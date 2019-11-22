package pl.michalwa.untitled.engine.loop.events;

import pl.michalwa.untitled.engine.events.Event;

/**
 * Dispatche by {@link pl.michalwa.untitled.engine.loop.GameLoop} every tick
 */
public class Tick extends Event
{
	@Override
	public String getMessage()
	{
		return "TICK";
	}
}
