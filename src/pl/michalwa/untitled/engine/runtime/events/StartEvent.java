package pl.michalwa.untitled.engine.runtime.events;

import pl.michalwa.untitled.engine.events.Event;

/**
 * Dispatched when the application is starting. If consumed, the start is cancelled.
 */
public class StartEvent extends Event
{
	@Override
	public String getMessage()
	{
		return "Application is starting";
	}
}
