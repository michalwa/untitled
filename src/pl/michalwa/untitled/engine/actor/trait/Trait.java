package pl.michalwa.untitled.engine.actor.trait;

import pl.michalwa.untitled.engine.actor.Actor;

/**
 * A stateful component attached to an {@link Actor} granting him the ability
 * to execute some actions or respond to various events in a certain way
 */
public abstract class Trait
{
	/**
	 * The actor this trait is attached to
	 */
	private Actor actor = null;
	
	/**
	 * Returns the actor this trait is attached to
	 *
	 * @return the actor this trait is attached to
	 */
	public Actor getActor()
	{
		return actor;
	}
	
	/**
	 * Attaches this trait to the given actor
	 *
	 * @param actor the actor to attach to
	 */
	public void attachTo(Actor actor)
	{
		if(this.actor != actor) {
			this.actor = actor;
			actor.attach(this);
			onAttached(actor);
		}
	}
	
	/**
	 * Called when this trait is attached to an actor
	 *
	 * @param actor the actor the trait was attached to
	 */
	protected abstract void onAttached(Actor actor);
}
