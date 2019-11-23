package pl.michalwa.untitled.engine.input.mouse.events;

import pl.michalwa.untitled.engine.events.Event;
import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.input.mouse.MouseInput;

public abstract class MouseButtonEvent extends Event
{
	/**
	 * The point on the game canvas where the mouse click occured
	 */
	public final Vector2i point;
	
	/**
	 * The button that has been clicked
	 */
	public final MouseInput.Button button;
	
	/**
	 * Constructs a mouse button event for the given button
	 *
	 * @param point the point on the game canvas where the event occured
	 * @param button the button which was involved in the event
	 */
	public MouseButtonEvent(Vector2i point, MouseInput.Button button)
	{
		this.point = point;
		this.button = button;
	}
	
	@Override
	public String getMessage()
	{
		return button + " " + getDescription() + " at " + point;
	}
	
	/**
	 * Returns a textual description of what happened with the mouse button
	 * (clicked/pressed/released)
	 *
	 * @return a description of the event
	 */
	protected abstract String getDescription();
}
