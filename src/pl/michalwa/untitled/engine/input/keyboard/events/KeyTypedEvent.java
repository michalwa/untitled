package pl.michalwa.untitled.engine.input.keyboard.events;

import pl.michalwa.untitled.engine.input.keyboard.Key;

/**
 * When a key event should cause a character to be typed. The preferred way to handle typing
 */
public class KeyTypedEvent extends KeyEvent
{
	/**
	 * The typed character
	 */
	private final char typedCharacter;
	
	/**
	 * Constructs a key type event with the given key code
	 *
	 * @param key the key associated with the event
	 * @param typedCharacter the typed character
	 */
	public KeyTypedEvent(Key key, char typedCharacter)
	{
		super(key);
		this.typedCharacter = typedCharacter;
	}
	
	/**
	 * Returns the character which was typed in the event
	 *
	 * @return the typed character
	 */
	public char getTypedCharacter()
	{
		return typedCharacter;
	}
	
	@Override
	public String getMessage()
	{
		return super.getMessage() + " (typed character: '" + typedCharacter + "')";
	}
	
	@Override
	protected String getDescription()
	{
		return "typed";
	}
}
