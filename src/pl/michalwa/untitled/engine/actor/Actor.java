package pl.michalwa.untitled.engine.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import pl.michalwa.untitled.engine.actor.events.AncestorAddedEvent;
import pl.michalwa.untitled.engine.actor.events.AncestorRemovedEvent;
import pl.michalwa.untitled.engine.actor.events.DescendantAddedEvent;
import pl.michalwa.untitled.engine.actor.events.DescendantRemovedEvent;
import pl.michalwa.untitled.engine.actor.trait.Trait;
import pl.michalwa.untitled.engine.events.Event;
import pl.michalwa.untitled.engine.events.EventDispatcher;

/**
 * An actor is anything "physical" in the game
 */
public class Actor extends EventDispatcher
{
	/**
	 * The parent actor (can be {@code null})
	 */
	private Actor parent = null;
	
	/**
	 * Children of this actor. All behavior of children actors is in some sense
	 * relative to the state of the parent actor, e.g. transforms are relative.
	 */
	private final List<Actor> children = new ArrayList<>();
	
	/**
	 * Traits attached to this actor
	 */
	private final List<Trait> traits = new ArrayList<>();
	
	@Override
	public <T extends Event> void dispatch(T event)
	{
		super.dispatch(event);
		
		// Propagate tree events
		if(event instanceof AncestorAddedEvent || event instanceof AncestorRemovedEvent) {
			getChildren().forEach(c -> c.dispatch(event));
		} else if(event instanceof DescendantAddedEvent || event instanceof DescendantRemovedEvent) {
			getParent().ifPresent(p -> p.dispatch(event));
		}
	}
	
	/**
	 * Adds the given actor as a child of this actor. Removes the actor
	 * from the children of its current parent.
	 *
	 * @param actor the child to add
	 */
	public void addChild(Actor actor)
	{
		if(actor.parent != null) {
			actor.parent.removeChild(actor);
		}
		if(!children.contains(actor)) {
			children.add(actor);
		}
		
		actor.parent = this;
		
		actor.dispatch(new AncestorAddedEvent(this));
		dispatch(new DescendantAddedEvent(actor));
	}
	
	/**
	 * Removes the given actor from the children of this actor.
	 *
	 * @param actor the child to remove
	 */
	public void removeChild(Actor actor)
	{
		if(actor.parent == this) {
			actor.parent = null;
			actor.dispatch(new AncestorRemovedEvent(this));
		}
		children.remove(actor);
		dispatch(new DescendantRemovedEvent(actor));
	}
	
	/**
	 * Returns a list of this actor's children
	 *
	 * @return a list of this actor's children
	 */
	public List<Actor> getChildren()
	{
		return new ArrayList<>(children);
	}
	
	/**
	 * Returns the parent of this actor or an empty {@link Optional} if it has no parent.
	 *
	 * @return the parent of this actor or an empty {@link Optional}
	 */
	public Optional<Actor> getParent()
	{
		return Optional.ofNullable(parent);
	}
	
	/**
	 * Attaches the given trait to this actor. A trait cannot be removed from an actor.
	 * Does nothing if a trait of the same type (or a subtype) is already attached to this actor.
	 *
	 * @param trait the trait to attach
	 */
	public void attach(Trait trait)
	{
		if(!getTrait(trait.getClass()).isPresent()) {
			traits.add(trait);
		}
		trait.attachTo(this);
	}
	
	/**
	 * Returns a trait of the specified type attached to this actor, if there is one.
	 * Otherwise, returns an empty {@link Optional}.
	 *
	 * @param type the type of trait to return
	 * @param <T> the type of trait to return
	 *
	 * @return a trait of the specified type or an empty {@link Optional}
	 */
	@SuppressWarnings("unchecked")
	public <T extends Trait> Optional<T> getTrait(Class<T> type)
	{
		return traits.stream()
			.filter(type::isInstance)
			.map(t -> (T) t)
			.findAny();
	}
	
	/**
	 * Finds all actors among the descendants of this actor (not including this actor) that have
	 * a trait of the specified type attached to them and returns all of those trait instances
	 * attached to those actors as a list.
	 *
	 * @param type the type of traits to find
	 * @param <T> the type of traits
	 *
	 * @return a list of traits of the specified type
	 */
	public <T extends Trait> List<T> findAllInDescendants(Class<T> type)
	{
		return Stream.of(
			
			children.stream()
				.map(c -> c.getTrait(type))
				.filter(Optional::isPresent)
				.map(Optional::get),
		
			children.stream()
				.flatMap(c -> c.findAllInDescendants(type).stream()))
			
			.flatMap(Function.identity())
			.collect(Collectors.toList());
	}
	
	/**
	 * Finds an ancestor of this actor that has a trait of the specified type attached to it
	 * and returns that trait. If not found, returns an empty {@link Optional}.
	 *
	 * @param type the type of trait to find
	 * @param <T> the type of trait to find
	 *
	 * @return
	 */
	public <T extends Trait> Optional<T> findInAncestors(Class<T> type)
	{
		return getParent()
			.flatMap(parent -> Optional.ofNullable(parent.getTrait(type)
			.orElseGet(() -> parent.findInAncestors(type).orElse(null))));
	}
}
