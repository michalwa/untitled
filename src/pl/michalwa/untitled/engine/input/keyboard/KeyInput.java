package pl.michalwa.untitled.engine.input.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.events.EventDispatcher;
import pl.michalwa.untitled.engine.input.keyboard.events.KeyPressedEvent;
import pl.michalwa.untitled.engine.input.keyboard.events.KeyReleasedEvent;
import pl.michalwa.untitled.engine.input.keyboard.events.KeyTypedEvent;
import pl.michalwa.untitled.engine.window.Window;

/**
 * Attaches a key input listener to the game canvas and dispatches key events
 */
public class KeyInput extends EventDispatcher implements Component, KeyListener
{
	/**
	 * Contains keys that are currently being pressed down
	 */
	private final Set<Key> keysDown = new HashSet<>();
	
	@Override
	public void getDependencies(Set<Class<? extends Component>> dependencies)
	{
		dependencies.add(Window.class);
	}
	
	@Override
	public void initialize(Container container)
	{
		container.require(Window.class).getCanvas().addKeyListener(this);
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName();
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		dispatch(new KeyTypedEvent(Key.get(e.getKeyCode(), e.getKeyLocation()), e.getKeyChar()));
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		Key key = Key.get(e.getKeyCode(), e.getKeyLocation());
		if(key == null) return;
		if(keysDown.add(key)) {
			dispatch(new KeyPressedEvent(key));
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		Key key = Key.get(e.getKeyCode(), e.getKeyLocation());
		if(key == null) return;
		keysDown.remove(key);
		dispatch(new KeyReleasedEvent(key));
	}
	
	/**
	 * Returns {@code true} if the specified key is currently being pressed down
	 * or {@code false} otherwise
	 *
	 * @param key the key to tell the state of
	 *
	 * @return whether the specified key is currently being pressed down
	 */
	public boolean isDown(Key key)
	{
		return keysDown.contains(key);
	}
}
