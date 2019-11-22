package pl.michalwa.untitled.engine.loop.events;

import pl.michalwa.untitled.engine.events.Event;

/**
 * Dispatched every second by {@link pl.michalwa.untitled.engine.loop.GameLoop}
 */
public class EverySecond extends Event
{
	@Override
	public String getMessage()
	{
		return "SECOND";
	}
}
