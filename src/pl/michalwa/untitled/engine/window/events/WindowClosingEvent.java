package pl.michalwa.untitled.engine.window.events;

import pl.michalwa.untitled.engine.runtime.Application;
import pl.michalwa.untitled.engine.window.Window;

/**
 * Dispatched by {@link Window} when the user requests the window to close.
 * If consumed, the window will not close and the application will keep running.
 * If not consumed, {@link Application#quit()} will be called by the {@link Window} component,
 * or if the {@link Application} component was not present in the container
 * upon initialization, {@code System.exit(0)} will be called by default.
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
