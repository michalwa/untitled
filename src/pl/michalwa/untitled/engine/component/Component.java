package pl.michalwa.untitled.engine.component;

import java.util.Set;

/**
 * A component is an element of the engine with a single responsibility
 */
public interface Component
{
	/**
	 * Populates the given set with the types of components that need to be registered
	 * and initialized before this component can be initialized
	 *
	 * @param dependencies the set of dependencies to populate
	 */
	void getDependencies(Set<Class<? extends Component>> dependencies);
	
	/**
	 * Initializes this component in the context of the given container.
	 * This method is meant to perform initialization that involves other components
	 *
	 * @param container the container where this component is being initialized
	 */
	void initialize(Container container) throws ComponentInitializationException;
}
