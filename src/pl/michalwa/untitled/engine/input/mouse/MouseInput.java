package pl.michalwa.untitled.engine.input.mouse;

import java.awt.Canvas;
import java.awt.event.*;
import java.util.Set;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.events.EventDispatcher;
import pl.michalwa.untitled.engine.events.EventSubscriber;
import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.input.mouse.events.MouseClickedEvent;
import pl.michalwa.untitled.engine.input.mouse.events.MousePressedEvent;
import pl.michalwa.untitled.engine.input.mouse.events.MouseReleasedEvent;
import pl.michalwa.untitled.engine.utils.struct.Observable;
import pl.michalwa.untitled.engine.window.Window;

/**
 * Attaches a mouse input listener to the game window and dispatches mouse events.
 *
 * <p>
 * Mouse button events can be subscribed to with {@link MouseInput#subscribe(Class, EventSubscriber)}
 * passing {@link pl.michalwa.untitled.engine.input.mouse.events.MouseButtonEvent MouseButtonEvent.class}
 * or one of its subclasses as the event type parameter.
 * </p>
 *
 * <p>
 * Mouse movement and mouse wheel rotation can be detected by subscribing to one of
 * {@link MouseInput#position} or {@link MouseInput#wheel} observables and listening for changes.
 * </p>
 */
public class MouseInput extends EventDispatcher implements
	Component,
	MouseListener,
	MouseMotionListener,
	MouseWheelListener
{
	/**
	 * The position of the mouse cursor on the game canvas
	 */
	public final Observable<Vector2i> position;
	
	/**
	 * The position of the mouse wheel relative to its position
	 * upon initialization of this component (in unit turns or "clicks")
	 */
	public final Observable<Integer> wheel;
	
	/**
	 * Constructs a new mouse input component
	 */
	public MouseInput()
	{
		position = new Observable<>(Vector2i.ZERO);
		wheel = new Observable<>(0);
	}
	
	@Override
	public void getDependencies(Set<Class<? extends Component>> dependencies)
	{
		dependencies.add(Window.class);
	}
	
	@Override
	public void initialize(Container container)
	{
		Canvas canvas = container.require(Window.class).getCanvas();
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		dispatch(new MouseClickedEvent(
			new Vector2i(e.getX(), e.getY()),
			Button.get(e.getButton())));
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		dispatch(new MousePressedEvent(
			new Vector2i(e.getX(), e.getY()),
			Button.get(e.getButton())));
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		dispatch(new MouseReleasedEvent(
			new Vector2i(e.getX(), e.getY()),
			Button.get(e.getButton())));
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		position.set(new Vector2i(e.getX(), e.getY()));
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		position.set(new Vector2i(e.getX(), e.getY()));
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		wheel.set(wheel.get() + e.getWheelRotation());
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	/**
	 * Mouse buttons
	 */
	public enum Button
	{
		/**
		 * Left (main) mouse button
		 */
		LEFT(MouseEvent.BUTTON1, "Left mouse button"),
		
		/**
		 * Middle (wheel) mouse button
		 */
		MIDDLE(MouseEvent.BUTTON2, "Middle mouse button"),
		
		/**
		 * Right (option) mouse button
		 */
		RIGHT(MouseEvent.BUTTON3, "Right mouse button");
		
		/**
		 * {@link MouseEvent} button constant value
		 */
		public final int index;
		
		/**
		 * A textual description of the button
		 */
		private final String description;
		
		Button(int index, String description)
		{
			this.index = index;
			this.description = description;
		}
		
		@Override
		public String toString()
		{
			return description;
		}
		
		/**
		 * Returns the enum constant with the specified button index
		 *
		 * @param index the index of the button enum constant to return
		 *
		 * @return the button enum constant with the specified index or {@code null} if not found
		 */
		static Button get(int index)
		{
			for(Button button : Button.values()) {
				if(button.index == index) return button;
			}
			return null;
		}
	}
}
