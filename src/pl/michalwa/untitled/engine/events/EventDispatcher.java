package pl.michalwa.untitled.engine.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pl.michalwa.untitled.engine.utils.collections.ReverseListIterator;
import pl.michalwa.untitled.engine.utils.struct.Pair;

/**
 * Dispatches events allowing other classes to subscribe and handle them
 */
public class EventDispatcher
{
	/**
	 * Event types associated with subscribers
	 *
	 * For each pair the type argument of the {@link Class} type must be equal to or a subclass of
	 * the type argument of the {@link EventSubscriber} type.
	 */
	private final List<Pair<Class<? extends Event>, EventSubscriber<? extends Event>>> subscribers;
	
	/**
	 * Constructs a new event dispatcher
	 */
	public EventDispatcher()
	{
		subscribers = new ArrayList<>();
	}
	
	/**
	 * Subscribes the given subscriber for events of the specified type
	 *
	 * @param type the event {@link Class} to subscribe to
	 * @param subscriber the subscriber to handle the events
	 * @param <T> the event type
	 */
	public <T extends Event> void subscribe(Class<T> type, EventSubscriber<T> subscriber)
	{
		subscribers.add(new Pair<>(type, subscriber));
	}
	
	/**
	 * Dispatches the given event to subscribers
	 *
	 * @param event the event to dispatch
	 * @param <T> the event type
	 */
	@SuppressWarnings("unchecked")
	public <T extends Event> void dispatch(T event)
	{
		event.source = this;
		
		// Iterate in reverse so that subscribers added earlier have less priority
		// In case an event gets consumed by a subscriber we want the subscribers
		// registered before it not to recieve the event
		Iterator<Pair<Class<? extends Event>, EventSubscriber<? extends Event>>> iterator = new ReverseListIterator<>(subscribers);
		while(iterator.hasNext() && !event.consumed) {
			final Pair<Class<? extends Event>, EventSubscriber<? extends Event>> pair = iterator.next();
			
			if(pair.getFirst().isInstance(event)) {
				((EventSubscriber<? super T>) pair.getSecond()).on(event);  // "unchecked" cast
			}
		}
	}
}
