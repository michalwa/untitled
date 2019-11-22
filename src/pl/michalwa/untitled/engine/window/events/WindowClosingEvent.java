package pl.michalwa.untitled.engine.window.events;

/**
 * Dispatched by {@link pl.michalwa.untitled.engine.window.Window} when the user requests the window to close
 */
public class WindowClosingEvent extends WindowEvent
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMessage()
	{
		return "The user requested the window to close";
	}
}
