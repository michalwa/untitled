package pl.michalwa.untitled.engine.window.events;

import pl.michalwa.untitled.engine.events.Event;

/**
 * Occurs when the game window gains or loses focus
 */
public class WindowFocusEvent extends Event
{
	/**
	 * Whether the window gained or lost focus
	 */
	public final boolean focused;
	
	/**
	 * Constructs a new {@link WindowFocusEvent}
	 *
	 * @param focused whether the window gained or lost focus
	 */
	public WindowFocusEvent(boolean focused)
	{
		this.focused = focused;
	}
	
	@Override
	public String getMessage()
	{
		return focused ? "Window gained focus" : "Window lost focus";
	}
}
