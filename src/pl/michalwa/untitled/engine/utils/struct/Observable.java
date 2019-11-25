package pl.michalwa.untitled.engine.utils.struct;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A generic data wrapper that allows dispatching custom actions when the data is changed
 */
public class Observable<T>
{
	/**
	 * The stored value
	 */
	private T value = null;
	
	/**
	 * Subscribed observers
	 */
	private final List<Observer<T>> observers = new CopyOnWriteArrayList<>();
	
	/**
	 * Constructs a new observable wrapper with the given initial value
	 *
	 * @param value the initial value for the wrapper
	 */
	public Observable(T value)
	{
		this.value = value;
	}
	
	/**
	 * Constructs a new observable wrapper with no initial value ({@code null})
	 */
	public Observable() {}
	
	/**
	 * Returns the value stored in this observable
	 *
	 * @return the value
	 */
	public T get()
	{
		return value;
	}
	
	/**
	 * Stores the given value in this observable. If {@link Objects#equals(Object, Object)}
	 * return {@code false} for the current/previous and the new value of the observable,
	 * all observers will be notified.
	 *
	 * @param value the new value
	 */
	public void set(T value)
	{
		T previous = this.value;
		this.value = value;
		
		if(!Objects.equals(previous, this.value)) {
			for(Observer<T> observer : observers) {
				observer.onValueChanged(previous, this.value);
			}
		}
	}
	
	/**
	 * Subscribes the given observer for changes in this observable
	 *
	 * @param observer the observer to subscribe
	 */
	public void subscribe(Observer<T> observer)
	{
		if(!observers.contains(observer)) observers.add(observer);
	}
	
	/**
	 * Listens for changes of an observable value
	 *
	 * @param <T> the type of the observable value
	 */
	@FunctionalInterface
	public interface Observer<T>
	{
		/**
		 * Called when the value of an observable wrapper changes
		 *
		 * @param previousValue the previous value stored in the observable wrapper
		 * @param newValue the new value
		 */
		void onValueChanged(T previousValue, T newValue);
	}
}
