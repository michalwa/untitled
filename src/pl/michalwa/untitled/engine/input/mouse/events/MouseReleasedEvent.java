package pl.michalwa.untitled.engine.input.mouse.events;

import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.input.mouse.MouseInput;

/**
 * When a mouse button has been released
 */
public class MouseReleasedEvent extends MouseButtonEvent
{
	/**
	 * Constructs a mouse release event
	 *
	 * @param point the point on the game canvas where the event occured
	 * @param button the button which was involved in the event
	 */
	public MouseReleasedEvent(Vector2i point, MouseInput.Button button)
	{
		super(point, button);
	}
	
	@Override
	protected String getDescription()
	{
		return "released";
	}
}
