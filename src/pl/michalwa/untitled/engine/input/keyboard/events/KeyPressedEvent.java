package pl.michalwa.untitled.engine.input.keyboard.events;

import pl.michalwa.untitled.engine.input.keyboard.Key;

/**
 * When a key on the keyboard is pressed
 */
public class KeyPressedEvent extends KeyEvent
{
	/**
	 * Constructs a key press event with the given key
	 *
	 * @param key the key associated with the event
	 */
	public KeyPressedEvent(Key key)
	{
		super(key);
	}
	
	@Override
	protected String getDescription()
	{
		return "pressed";
	}
}
