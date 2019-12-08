package pl.michalwa.untitled.engine.actor.events;

import pl.michalwa.untitled.engine.actor.Actor;
import pl.michalwa.untitled.engine.events.Event;

/**
 * Dispatched by {@link Actor} when a descendant is removed from the tree
 */
public class DescendantRemovedEvent extends Event
{
	/**
	 * The removed descendant
	 */
	public final Actor actor;
	
	/**
	 * Constructs a new descendant removal event
	 *
	 * @param actor the descendant that was removed
	 */
	public DescendantRemovedEvent(Actor actor)
	{
		this.actor = actor;
	}
	
	@Override
	public String getMessage()
	{
		return "Descendant " + actor + " removed";
	}
}

