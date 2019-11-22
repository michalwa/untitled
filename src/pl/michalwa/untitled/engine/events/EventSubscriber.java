package pl.michalwa.untitled.engine.events;

/**
 * Recieves events of a certain type from dispatchers and handles them
 */
@FunctionalInterface
public interface EventSubscriber<T>
{
	/**
	 * Handles the given event
	 *
	 * @param event the event to handle
	 */
	void on(T event);
}
