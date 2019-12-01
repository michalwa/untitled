package pl.michalwa.untitled.engine.component;

import java.util.*;

/**
 * A container encapusaltes various singleton components of the application
 * and allows soft dependencies between them
 */
public class Container
{
	/**
	 * The default container
	 */
	public static final Container main = new Container();
	
	/**
	 * The registered components
	 */
	private final Map<Class<? extends Component>, Component> components;
	
	/**
	 * Constructs a new empty container
	 */
	public Container()
	{
		components = new HashMap<>();
	}
	
	/**
	 * Registers the given components
	 *
	 * @param components components to register
	 */
	public void register(Component... components)
	{
		for(Component component : components) {
			if(!this.components.containsValue(component)) {
				this.components.put(component.getClass(), component);
			}
		}
	}
	
	/**
	 * Returns the component of the specified class. If there are multiple components
	 * of this type registered, the first one is returned.
	 *
	 * @param type the type of the component to return
	 *
	 * @return the component of the specified type
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> Optional<T> get(Class<T> type)
	{
		return components.entrySet().stream()
			.filter(entry -> type.isAssignableFrom(entry.getKey()))
			.map(entry -> (T) entry.getValue())  // "unchecked" cast
			.findFirst();
	}
	
	/**
	 * Same as {@link #get(Class)} but will throw an exception if the component
	 * of the specified class is not registered
	 *
	 * @param type the type of the component to return
	 *
	 * @return the component of the specified type
	 *
	 * @throws MissingComponentException if the requested component is not registered
	 */
	public <T extends Component> T require(Class<T> type)
	{
		Optional<T> opt = get(type);
		if(opt.isPresent()) return opt.get();
		throw new MissingComponentException("No component registered for class: " + type.getCanonicalName());
	}
	
	/**
	 * Initializes all components registered in this container.
	 * Components will be initialized in an order that ensures that each component's
	 * dependencies are initialized before it.
	 *
	 * @throws ComponentInitializationException if any of the components
	 */
	public void initialize() throws
		DependencyLoopException,
		MissingComponentException,
		ComponentInitializationException
	{
		// For detecting dependency loops
		DependencyGraph graph = new DependencyGraph();
		
		// Used later for building the initialization stack
		Set<Component> toTraverse = new HashSet<>(components.values());
		
		// Collect dependencies
		Map<Component, List<Component>> resolvedDeps = new HashMap<>();
		for(Component component : components.values()) {
			Set<Class<? extends Component>> deps = new HashSet<>();
			component.getDependencies(deps);
			
			// Resolve dependencies for component
			resolvedDeps.put(component, new ArrayList<>());
			for(Class<? extends Component> dep : deps) {
				Optional<? extends Component> opt = get(dep);
				
				if(opt.isPresent()) {
					Component resolved = opt.get();
					resolvedDeps.get(component).add(resolved);
					graph.addDependency(component, resolved);
					toTraverse.remove(resolved);
				} else {
					throw new MissingComponentException(
						"Missing dependency " + dep.getCanonicalName() + " required by " + component);
				}
			}
		}
		
		// Detect dependency loops
		DependencyGraph.Vertex cyclic = graph.findCyclic();
		if(cyclic != null) {
			throw new DependencyLoopException("Cyclic dependency detected: " + cyclic.getComponent());
		}
		
		// There must be at least one component that is not required by any other
		if(toTraverse.isEmpty()) {
			throw new DependencyLoopException("No topmost component found in the dependency tree");
		}
		
		// Build initialization stack by breadth-first traversal
		// (Last In First Out, deepest dependencies will be initialized first)
		Stack<Component> stack = new Stack<>();
		while(!toTraverse.isEmpty()) {
			for(Component component : new HashSet<>(toTraverse)) {
				stack.remove(component);
				stack.push(component);
				toTraverse.addAll(resolvedDeps.get(component));
				toTraverse.remove(component);
			}
		}
		
		// Run initialization
		while(!stack.empty()) {
			stack.pop().initialize(this);
		}
	}
}
