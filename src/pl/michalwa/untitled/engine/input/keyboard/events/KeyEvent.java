package pl.michalwa.untitled.engine.input.keyboard.events;

import pl.michalwa.untitled.engine.events.Event;
import pl.michalwa.untitled.engine.input.keyboard.Key;

/**
 * Dispatched when key input is recieved
 */
public abstract class KeyEvent extends Event
{
	/**
	 * The code of the key associated with the event
	 */
	private final Key key;
	
	/**
	 * Constructs a key event with the given key
	 *
	 * @param key the key associated with the event
	 */
	KeyEvent(Key key)
	{
		this.key = key;
	}
	
	/**
	 * Returns the key associated with this event
	 *
	 * @return the key associated with this event
	 */
	public Key getKey()
	{
		return key;
	}
	
	@Override
	public String getMessage()
	{
		return key + " key " + getDescription();
	}
	
	/**
	 * Returns a human-readable description of what happened with the key
	 *
	 * @return a description of the type of the key event
	 */
	protected abstract String getDescription();
}
