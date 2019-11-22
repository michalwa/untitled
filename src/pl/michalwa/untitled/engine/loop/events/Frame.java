package pl.michalwa.untitled.engine.loop.events;

import pl.michalwa.untitled.engine.events.Event;

/**
 * Dispatched by {@link pl.michalwa.untitled.engine.loop.GameLoop} every frame
 */
public class Frame extends Event
{
	@Override
	public String getMessage()
	{
		return "FRAME";
	}
}
