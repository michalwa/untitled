package pl.michalwa.untitled.engine.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import pl.michalwa.untitled.engine.utils.collections.ReverseListIterator;

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
	private final List<SubscriberEntry<?>> subscribers;
	
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
	 * @param predicate a predicate to filter events that are supposed to be handled by the subscriber
	 * @param subscriber the subscriber to handle the events
	 * @param <T> the event type
	 */
	public <T extends Event> void subscribe(
		Class<? extends T> type,
		Predicate<T>       predicate,
		EventSubscriber<T> subscriber
	) {
		SubscriberEntry<T> entry = new SubscriberEntry<>(subscriber, type, predicate);
		if(!subscribers.contains(entry)) subscribers.add(entry);
	}
	
	/**
	 * Subscribes the given subscriber for events of the specified type.
	 * Same as calling {@link #subscribe(Class, Predicate, EventSubscriber)} but with a predicate
	 * that always returns {@code true}.
	 *
	 * @param type the event {@link Class} to subscribe to
	 * @param subscriber the subscriber to handle the events
	 * @param <T> the event type
	 */
	public <T extends Event> void subscribe(Class<? extends T> type, EventSubscriber<T> subscriber)
	{
		subscribe(type, e -> true, subscriber);
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
		Iterator<SubscriberEntry<?>> iterator = new ReverseListIterator<>(subscribers);
		while(iterator.hasNext() && event.isValid()) {
			SubscriberEntry<?> entry = iterator.next();
			
			if(entry.eventType.isInstance(event)) {
				SubscriberEntry<? super T> castEntry = (SubscriberEntry<? super T>) entry;  // "unchecked" cast
				if(castEntry.predicate.test(event)) {
					castEntry.subscriber.on(event);
				}
			}
		}
	}
	
	/**
	 * Encapsulates an entry in {@link #subscribers}
	 */
	private static class SubscriberEntry<T extends Event>
	{
		/**
		 * The subscriber
		 */
		private final EventSubscriber<T> subscriber;
		
		/**
		 * The event type the subscriber is subscribed to
		 */
		private final Class<? extends T> eventType;
		
		/**
		 * The predicate to filter incoming events
		 */
		private final Predicate<T> predicate;
		
		/**
		 * Constructs a new entry
		 *
		 * @param subscriber the subscriber (handler)
		 * @param eventType the event type the subscriber is subscribed to
		 * @param predicate the predicate to filter incoming events
		 */
		SubscriberEntry(
			EventSubscriber<T> subscriber,
			Class<? extends T> eventType,
			Predicate<T>       predicate
		) {
			this.subscriber = subscriber;
			this.eventType  = eventType;
			this.predicate  = predicate;
		}
		
		/**
		 * Returns {@code true} if {@code super.equals(obj)} returns {@code true}
		 * or if all of the values of this entry compared to the values of the given entry
		 * with {@link Objects#equals(Object, Object)} return {@code true}.
		 *
		 * @param obj the object to compare
		 *
		 * @return whether the objects are equal
		 */
		@Override
		public boolean equals(Object obj)
		{
			return super.equals(obj)
				
				|| (obj instanceof SubscriberEntry
				&& Objects.equals(((SubscriberEntry<?>) obj).subscriber, subscriber)
				&& Objects.equals(((SubscriberEntry<?>) obj).eventType,  eventType)
				&& Objects.equals(((SubscriberEntry<?>) obj).predicate,  predicate));
		}
	}
}
