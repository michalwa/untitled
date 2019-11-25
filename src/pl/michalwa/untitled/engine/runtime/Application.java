package pl.michalwa.untitled.engine.runtime;

import java.util.Set;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.events.Event;
import pl.michalwa.untitled.engine.events.EventDispatcher;
import pl.michalwa.untitled.engine.runtime.events.QuitEvent;
import pl.michalwa.untitled.engine.runtime.events.StartEvent;

/**
 * Dispatches events regarding changes in the application runtime like startup, initialization or exit
 */
public class Application extends EventDispatcher implements Component
{
	/**
	 * Dispatches a start event. Returns {@code true} if the event is not consumed after dispatching
	 * or {@code false} otherwise.
	 */
	public boolean start()
	{
		Event event = new StartEvent();
		dispatch(event);
		return !event.isConsumed();
	}
	
	/**
	 * Dispatches a quit event and closes the application.
	 * If the event gets consumed by a subscriber, the quit is cancelled.
	 */
	public void quit()
	{
		Event event = new QuitEvent();
		dispatch(event);
		if(!event.isConsumed()) System.exit(0);
	}
	
	@Override
	public void getDependencies(Set<Class<? extends Component>> dependencies) {}
	
	@Override
	public void initialize(Container container) {}
}
