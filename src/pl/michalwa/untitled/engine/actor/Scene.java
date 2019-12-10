package pl.michalwa.untitled.engine.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import pl.michalwa.untitled.engine.actor.trait.Renderer;
import pl.michalwa.untitled.engine.actor.trait.Trait;
import pl.michalwa.untitled.engine.graphics.render.RenderingContext;
import pl.michalwa.untitled.engine.graphics.render.layer.Layer;

/**
 * Holds top-most actors in a level and allows rendering
 */
public class Scene extends Layer
{
	/**
	 * Actors in the scene
	 */
	private final List<Actor> actors = new ArrayList<>();
	
	/**
	 * Constructs a new empty scene
	 */
	public Scene() {}
	
	/**
	 * Constructs a new empty scene with the given layer index
	 *
	 * @param index the layer index
	 */
	public Scene(int index)
	{
		super(index);
	}
	
	/**
	 * Adds the given actor to the scene.
	 * If the given actor has a parent, it will be removed from its children.
	 *
	 * @param actor the actor to add
	 */
	public void addActor(Actor actor)
	{
		if(!actors.contains(actor)) {
			// Unparent
			actor.getParent().ifPresent(parent -> parent.removeChild(actor));
			actors.add(actor);
		}
	}
	
	/**
	 * Removes the given actor from the scene
	 *
	 * @param actor the actor to remove
	 */
	public void removeActor(Actor actor)
	{
		actors.remove(actor);
	}
	
	/**
	 * Finds all actors with traits of the specified type in this scene
	 * and returns a list of all those traits.
	 *
	 * @param type the type of traits to find
	 * @param <T> the trait type
	 *
	 * @return a list of all traits of the specified type attached to actors in this scene
	 */
	public <T extends Trait> List<T> findAll(Class<T> type)
	{
		return Stream.concat(
			
			// On topmost actors
			actors.stream()
				.map(actor -> actor.getTrait(type))
				.filter(Optional::isPresent)
				.map(Optional::get),
			
			// In descendants
			actors.stream()
				.flatMap(actor -> actor.findAllInDescendants(type).stream()))
			
			.collect(Collectors.toList());
	}
	
	/**
	 * Finds any actor with a trait of the specified type in this scene
	 * and returns that trait.
	 *
	 * @param type the type of trait to find
	 * @param <T> the trait type
	 *
	 * @return the found trait or an empty {@link Optional}
	 */
	public <T extends Trait> Optional<T> find(Class<T> type)
	{
		return Stream.concat(
			
			// On topmost actors
			actors.stream()
				.map(actor -> actor.getTrait(type))
				.filter(Optional::isPresent)
				.map(Optional::get),
			
			// In descendants
			actors.stream()
				.flatMap(actor -> actor.findAllInDescendants(type).stream()))
			
			.findAny();
	}
	
	@Override
	public void render(RenderingContext ctx)
	{
		for(Renderer renderer : findAll(Renderer.class)) {
			renderer.render(ctx);
		}
	}
}
