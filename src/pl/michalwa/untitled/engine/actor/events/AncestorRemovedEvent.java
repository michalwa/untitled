package pl.michalwa.untitled.engine.actor.events;

import pl.michalwa.untitled.engine.actor.Actor;
import pl.michalwa.untitled.engine.events.Event;

/**
 * Dispatched by {@link Actor} when an ancestor is removed from the tree
 */
public class AncestorRemovedEvent extends Event
{
	/**
	 * The removed ancestor
	 */
	public final Actor actor;
	
	/**
	 * Constructs a new ancestor removal event
	 *
	 * @param actor the ancestor that was removed
	 */
	public AncestorRemovedEvent(Actor actor)
	{
		this.actor = actor;
	}
	
	@Override
	public String getMessage()
	{
		return "Ancestor " + actor + " removed";
	}
}

