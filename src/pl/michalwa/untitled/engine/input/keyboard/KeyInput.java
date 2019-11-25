package pl.michalwa.untitled.engine.input.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	public void keyTyped(KeyEvent e)
	{
		dispatch(new KeyTypedEvent(Key.get(e.getKeyCode(), e.getKeyLocation()), e.getKeyChar()));
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		dispatch(new KeyPressedEvent(Key.get(e.getKeyCode(), e.getKeyLocation())));
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		dispatch(new KeyReleasedEvent(Key.get(e.getKeyCode(), e.getKeyLocation())));
	}
}
