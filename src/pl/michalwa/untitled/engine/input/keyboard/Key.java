package pl.michalwa.untitled.engine.input.keyboard;

import java.awt.event.KeyEvent;
import java.util.Optional;
import pl.michalwa.untitled.engine.component.Container;

/**
 * Constants represent keys on a keyboard
 */
public enum Key
{
	/* Location -1 means the key location is irrelevant */
	
	_0(KeyEvent.VK_0, -1),
	_1(KeyEvent.VK_1, -1),
	_2(KeyEvent.VK_2, -1),
	_3(KeyEvent.VK_3, -1),
	_4(KeyEvent.VK_4, -1),
	_5(KeyEvent.VK_5, -1),
	_6(KeyEvent.VK_6, -1),
	_7(KeyEvent.VK_7, -1),
	_8(KeyEvent.VK_8, -1),
	_9(KeyEvent.VK_9, -1),
	
	NUMPAD_0(KeyEvent.VK_NUMPAD0, -1),
	NUMPAD_1(KeyEvent.VK_NUMPAD1, -1),
	NUMPAD_2(KeyEvent.VK_NUMPAD2, -1),
	NUMPAD_3(KeyEvent.VK_NUMPAD3, -1),
	NUMPAD_4(KeyEvent.VK_NUMPAD4, -1),
	NUMPAD_5(KeyEvent.VK_NUMPAD5, -1),
	NUMPAD_6(KeyEvent.VK_NUMPAD6, -1),
	NUMPAD_7(KeyEvent.VK_NUMPAD7, -1),
	NUMPAD_8(KeyEvent.VK_NUMPAD8, -1),
	NUMPAD_9(KeyEvent.VK_NUMPAD9, -1),
	NUMPAD_ASTERISK(KeyEvent.VK_MULTIPLY, -1),
	NUMPAD_PLUS(KeyEvent.VK_ADD, -1),
	NUMPAD_MINUS(KeyEvent.VK_SUBTRACT, -1),
	NUMPAD_DOT(KeyEvent.VK_DECIMAL, -1),
	NUMPAD_SLASH(KeyEvent.VK_DIVIDE, -1),
	NUM_LOCK(KeyEvent.VK_NUM_LOCK, -1),
	
	A(KeyEvent.VK_A, -1),
	B(KeyEvent.VK_B, -1),
	C(KeyEvent.VK_C, -1),
	D(KeyEvent.VK_D, -1),
	E(KeyEvent.VK_E, -1),
	F(KeyEvent.VK_F, -1),
	G(KeyEvent.VK_G, -1),
	H(KeyEvent.VK_H, -1),
	I(KeyEvent.VK_I, -1),
	J(KeyEvent.VK_J, -1),
	K(KeyEvent.VK_K, -1),
	L(KeyEvent.VK_L, -1),
	M(KeyEvent.VK_M, -1),
	N(KeyEvent.VK_N, -1),
	O(KeyEvent.VK_O, -1),
	P(KeyEvent.VK_P, -1),
	Q(KeyEvent.VK_Q, -1),
	R(KeyEvent.VK_R, -1),
	S(KeyEvent.VK_S, -1),
	T(KeyEvent.VK_T, -1),
	U(KeyEvent.VK_U, -1),
	V(KeyEvent.VK_V, -1),
	W(KeyEvent.VK_W, -1),
	X(KeyEvent.VK_X, -1),
	Y(KeyEvent.VK_Y, -1),
	Z(KeyEvent.VK_Z, -1),
	
	LEFT  (KeyEvent.VK_LEFT,  -1),
	RIGHT (KeyEvent.VK_RIGHT, -1),
	UP    (KeyEvent.VK_UP,    -1),
	DOWN  (KeyEvent.VK_DOWN,  -1),
	
	ENTER       (KeyEvent.VK_ENTER,       -1),
	BACKSPACE   (KeyEvent.VK_BACK_SPACE,  -1),
	TAB         (KeyEvent.VK_TAB,         -1),
	LEFT_SHIFT  (KeyEvent.VK_SHIFT,   KeyEvent.KEY_LOCATION_LEFT),
	RIGHT_SHIFT (KeyEvent.VK_SHIFT,   KeyEvent.KEY_LOCATION_RIGHT),
	LEFT_CTRL   (KeyEvent.VK_CONTROL, KeyEvent.KEY_LOCATION_LEFT),
	RIGHT_CTRL  (KeyEvent.VK_CONTROL, KeyEvent.KEY_LOCATION_RIGHT),
	LEFT_ALT    (KeyEvent.VK_ALT,     KeyEvent.KEY_LOCATION_LEFT),
	RIGHT_ALT   (KeyEvent.VK_ALT,     KeyEvent.KEY_LOCATION_RIGHT),
	PAUSE       (KeyEvent.VK_PAUSE,       -1),
	CAPS_LOCK   (KeyEvent.VK_CAPS_LOCK,   -1),
	ESCAPE      (KeyEvent.VK_ESCAPE,      -1),
	SPACE       (KeyEvent.VK_SPACE,       -1),
	INSERT      (KeyEvent.VK_INSERT,      -1),
	DELETE      (KeyEvent.VK_DELETE,      -1),
	HOME        (KeyEvent.VK_HOME,        -1),
	END         (KeyEvent.VK_END,         -1),
	PAGE_UP     (KeyEvent.VK_PAGE_UP,     -1),
	PAGE_DOWN   (KeyEvent.VK_PAGE_DOWN,   -1),
	SCROLL_LOCK (KeyEvent.VK_SCROLL_LOCK, -1),
	
	DEAD_TILDE    (KeyEvent.VK_DEAD_TILDE,    -1),
	BACKTICK      (KeyEvent.VK_BACK_QUOTE,    -1),
	COMMA         (KeyEvent.VK_COMMA,         -1),
	MINUS         (KeyEvent.VK_MINUS,         -1),
	PERIOD        (KeyEvent.VK_PERIOD,        -1),
	SLASH         (KeyEvent.VK_SLASH,         -1),
	SEMICOLON     (KeyEvent.VK_SEMICOLON,     -1),
	QUOTE         (KeyEvent.VK_QUOTE,         -1),
	EQUALS        (KeyEvent.VK_EQUALS,        -1),
	OPEN_BRACKET  (KeyEvent.VK_OPEN_BRACKET,  -1),
	CLOSE_BRACKET (KeyEvent.VK_CLOSE_BRACKET, -1),
	BACKSLASH     (KeyEvent.VK_BACK_SLASH,    -1),
	
	F1  (KeyEvent.VK_F1,  -1),
	F2  (KeyEvent.VK_F2,  -1),
	F3  (KeyEvent.VK_F3,  -1),
	F4  (KeyEvent.VK_F4,  -1),
	F5  (KeyEvent.VK_F5,  -1),
	F6  (KeyEvent.VK_F6,  -1),
	F7  (KeyEvent.VK_F7,  -1),
	F8  (KeyEvent.VK_F8,  -1),
	F9  (KeyEvent.VK_F9,  -1),
	F10 (KeyEvent.VK_F10, -1),
	F11 (KeyEvent.VK_F11, -1),
	F12 (KeyEvent.VK_F12, -1);
	
	/**
	 * {@link java.awt.event.KeyEvent} key code
	 */
	private final int code;
	
	/**
	 * The location of the key (left or right for keys like Shift, Alt, Ctrl);
	 * {@code -1} if the location is irrelevant
	 */
	private final int location;
	
	Key(int code, int location)
	{
		this.code = code;
		this.location = location;
	}
	
	/**
	 * Returns the code of the key ({@link java.awt.event.KeyEvent} key code)
	 *
	 * @return the key code
	 */
	public int getCode()
	{
		return code;
	}
	
	/**
	 * Returns a human-readable name of the key
	 *
	 * @return the name of this key
	 */
	public String getName()
	{
		String locationText = "";
		switch(location) {
		case KeyEvent.KEY_LOCATION_LEFT:
			locationText = "LEFT ";
			break;
		case KeyEvent.KEY_LOCATION_RIGHT:
			locationText = "RIGHT ";
			break;
		default:
			break;
		}
		
		return locationText + KeyEvent.getKeyText(code);
	}
	
	/**
	 * Returns {@code true} if the key is currently being pressed down or {@code false} otherwise.
	 * If there is no {@link KeyInput} component registered in the {@link Container#main default container}
	 * throws an {@link IllegalStateException}.
	 *
	 * @return whether the specified key is currently being pressed down
	 *
	 * @throws IllegalStateException if the key input component is not registered in the default container
	 */
	public boolean isDown()
	{
		Optional<KeyInput> opt = Container.main.get(KeyInput.class);
		if(!opt.isPresent()) {
			throw new IllegalStateException("KeyInput component not registered");
		}
		return opt.get().isDown(this);
	}
	
	/**
	 * Finds and returns the enum constant associated with the given values.
	 * Returns {@code null} if such a constant is not found.
	 *
	 * @param code the key code (one of {@link KeyEvent} {@code VK_*} constants)
	 * @param location the key location constant value (one of {@link KeyEvent} {@code KEY_LOCATION_*} constants)
	 *
	 * @return the found {@link Key} enum constant or {@code null}
	 */
	public static Key get(int code, int location)
	{
		for(Key key : values()) {
			if(key.code == code && (key.location == -1 || key.location == location)) {
				return key;
			}
		}
		
		return null;
	}
}
