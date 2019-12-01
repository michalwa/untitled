package pl.michalwa.untitled.engine.events;

/**
 * An event is used to allow flexibility in handling certain actions occuring during runtime
 */
public abstract class Event
{
	/**
	 * The dispatcher that produced the event
	 */
	protected EventDispatcher source;
	
	/**
	 * Whether this event has been consumed by a handler
	 */
	private boolean consumed = false;
	
	/**
	 * Returns a human-readable description of the event
	 *
	 * @return description of the event
	 */
	public abstract String getMessage();
	
	@Override
	public String toString()
	{
		return (source != null ? "[" + source + "] " : "")
			+ getClass().getSimpleName()
			+ ": " + getMessage();
	}
	
	/**
	 * Consumes this event preventing further subscribers from recieving it
	 *
	 * @return this event for method call chaining
	 */
	public Event consume()
	{
		consumed = true;
		return this;
	}
	
	/**
	 * Returns {@code true} if this event has been consumed by a handler
	 * or {@code false} otherwise
	 *
	 * @return whether this event has been consumed by a handler
	 */
	public boolean isConsumed()
	{
		return consumed;
	}
}
