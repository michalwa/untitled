package pl.michalwa.untitled.engine.actor.events;

import pl.michalwa.untitled.engine.actor.Actor;
import pl.michalwa.untitled.engine.events.Event;

/**
 * Dispatched by {@link Actor} when a new descendant appears in its tree
 */
public class DescendantAddedEvent extends Event
{
	/**
	 * The new descendant
	 */
	public final Actor actor;
	
	/**
	 * Constructs a new descendant addition event
	 *
	 * @param actor the descendant that was added
	 */
	public DescendantAddedEvent(Actor actor)
	{
		this.actor = actor;
	}
	
	@Override
	public String getMessage()
	{
		return "Descendant " + actor + " added";
	}
}
