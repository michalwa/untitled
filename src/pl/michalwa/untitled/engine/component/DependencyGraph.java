package pl.michalwa.untitled.engine.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Directed graph where vertices represent components and edges represent dependencies between them.
 * Used for detecting cyclic dependencies between components.
 */
class DependencyGraph
{
	/**
	 * Vertices of this graph
	 */
	private final List<Vertex> vertices = new ArrayList<>();
	
	/**
	 * Adds a vertex to the graph for the given component.
	 * Does nothing if a vertex with the given component already exists.
	 *
	 * @param component the component to add a vertex for
	 */
	public Vertex add(Component component)
	{
		Optional<Vertex> existing = vertices.stream()
			.filter(v -> v.component.equals(component))
			.findAny();
		
		if(existing.isPresent()) return existing.get();
		
		Vertex v = new Vertex(component);
		vertices.add(v);
		return v;
	}
	
	/**
	 * Adds an edge from one vertex to another for the two specified components
	 *
	 * @param dependent the dependent component that requires the dependency component
	 * @param dependency the dependency component required by the dependent component
	 */
	void addDependency(Component dependent, Component dependency)
	{
		Vertex from = findOrAddVertex(dependent);
		Vertex to = findOrAddVertex(dependency);
		
		if(!from.neighbors.contains(to)) from.neighbors.add(to);
	}
	
	/**
	 * Finds a vertex for the given component in this graph or creates one if it doesn't exist
	 * and returns it
	 *
	 * @param component the component to find a vertex for
	 *
	 * @return the found or created vertex
	 */
	private Vertex findOrAddVertex(Component component)
	{
		return vertices.stream()
			.filter(v -> v.component.equals(component))
			.findAny()
			.orElseGet(() -> {
				Vertex v = new Vertex(component);
				vertices.add(v);
				return v;
			});
	}
	
	/**
	 * Recursive method for finding cyclic dependencies, should only be called by {@link #findCyclic()}.
	 * This graph is no longer valid after this method is called.
	 *
	 * @param source the vertex to start searching from
	 *
	 * @return a vertex causing a cycle or {@code null} if not found
	 */
	private Vertex findCyclic(Vertex source)
	{
		source.beingVisited = true;
		
		for(Vertex neighbor : source.neighbors) {
			if(neighbor.beingVisited) {
				return neighbor;
			} else if(!neighbor.visited && findCyclic(neighbor) != null) {
				return neighbor;
			}
		}
		
		source.beingVisited = false;
		source.visited = true;
		return null;
	}
	
	/**
	 * Finds a cyclic dependency in the graph and returns the vertex that causes the cycle
	 * or {@code null} if not cycle is found. This graph is no longer valid after this method is called.
	 *
	 * @return the vertex causing a cycle or {@code null} if not found
	 */
	Vertex findCyclic()
	{
		for(Vertex vertex : vertices) {
			if(!vertex.visited && findCyclic(vertex) != null) {
				return vertex;
			}
		}
		return null;
	}
	
	/**
	 * Dependency graph vertex
	 */
	static class Vertex
	{
		/**
		 * The component associated with this vertex
		 */
		private final Component component;
		
		/**
		 * Adjacent vertices (dependencies of the component)
		 */
		private final List<Vertex> neighbors = new ArrayList<>();
		
		/**
		 * Whether this vertex is currently being visited in a traversal
		 */
		private boolean beingVisited = false;
		
		/**
		 * Whether this vertex has been visited in a traversal
		 */
		private boolean visited = false;
		
		/**
		 * Constructs a new vertex for the given component
		 */
		private Vertex(Component component)
		{
			this.component = component;
		}
		
		/**
		 * Returns the component represented by this vertex
		 *
		 * @return the component represented by this vertex
		 */
		public Component getComponent()
		{
			return component;
		}
	}
}
