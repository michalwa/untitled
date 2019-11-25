package pl.michalwa.untitled.engine.input.keyboard.events;

import pl.michalwa.untitled.engine.input.keyboard.Key;

/**
 * When a key on the keyboard is released
 */
public class KeyReleasedEvent extends KeyEvent
{
	/**
	 * Constructs a key release event with the given key
	 *
	 * @param key the key associated with the event
	 */
	public KeyReleasedEvent(Key key)
	{
		super(key);
	}
	
	@Override
	protected String getDescription()
	{
		return "released";
	}
}
