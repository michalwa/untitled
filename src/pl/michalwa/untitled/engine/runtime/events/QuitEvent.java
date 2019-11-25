package pl.michalwa.untitled.engine.runtime.events;

import pl.michalwa.untitled.engine.events.Event;

/**
 * Dispatched when the application is about to quit. If consumed, the quit is cancelled.
 */
public class QuitEvent extends Event
{
	@Override
	public String getMessage()
	{
		return "Application is closing";
	}
}
