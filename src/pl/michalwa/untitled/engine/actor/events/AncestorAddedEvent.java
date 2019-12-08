package pl.michalwa.untitled.engine.actor.events;

import pl.michalwa.untitled.engine.actor.Actor;
import pl.michalwa.untitled.engine.events.Event;

/**
 * Dispatched by {@link Actor} when a new ancestor appears in its tree
 */
public class AncestorAddedEvent extends Event
{
	/**
	 * The new ancestor
	 */
	public final Actor actor;
	
	/**
	 * Constructs a new ancestor addition event
	 *
	 * @param actor the ancestor that was added
	 */
	public AncestorAddedEvent(Actor actor)
	{
		this.actor = actor;
	}
	
	@Override
	public String getMessage()
	{
		return "Ancestor " + actor + " added";
	}
}
