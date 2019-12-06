package pl.michalwa.untitled.engine.utils.struct;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * A wrapper that allows dispatching custom actions when the data is changed
 */
public class Observable<T> extends Wrapper<T>
{
	/**
	 * Subscribed observers
	 */
	private final List<Observer<T>> observers = new CopyOnWriteArrayList<>();
	
	/**
	 * Whether this observable was bound to another
	 */
	private boolean bound = false;
	
	/**
	 * Constructs a new observable wrapper with a {@code null} value
	 */
	public Observable() {}
	
	/**
	 * Constructs a new observable wrapper with the given initial value
	 *
	 * @param value the initial value for the wrapper
	 */
	public Observable(T value)
	{
		super(value);
	}
	
	/**
	 * Stores the given value in this observable. If {@link Objects#equals(Object, Object)}
	 * returns {@code false} for the current/previous and the new value, all observers will be notified.
	 *
	 * @param value the new value
	 */
	@Override
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
	 * Applies a mapping to this observable
	 *
	 * <p>
	 * Constructs a new observable that will be automatically set to the result of calling
	 * the given mapping function with the value of this observable whenever it changes.
	 * </p>
	 * <p>
	 * This observable, on the other hand, will be set to the result of calling the other
	 * mapping function with the value of the new observable whenever that changes.
	 * </p>
	 * <p>
	 * Calling {@code function.apply(reverse.apply(x))} as well as {@code reverse.apply(function.apply(x))}
	 * should always return {@code x} unchanged.
	 * </p>
	 * <p>
	 * The resulting observable is initially set to the result of calling the mapping function
	 * with the current value of this observable.
	 * </p>
	 *
	 * @param function the function to map values from this observable to the new observable
	 * @param reverse the function to map values from the new observable to this observable
	 * @param <R> the type of the resulting observable
	 *
	 * @return the mapped observable
	 */
	public <R> Observable<R> map(Function<T, R> function, Function<R, T> reverse)
	{
		Observable<R> result = new Observable<>(function.apply(value));
		subscribe((prev, value) -> result.set(function.apply(value)));
		result.subscribe((prev, value) -> set(reverse.apply(value)));
		return result;
	}
	
	/**
	 * Binds this observable to the given observable
	 *
	 * <p>
	 * Sets this observable to store the value of the given observable whenever that changes.
	 * </p>
	 *
	 * @param o the observable to bind this observable to
	 *
	 * @throws IllegalStateException if this {@link Observable} was already bound
	 */
	public void bind(Observable<T> o)
	{
		if(bound) {
			throw new IllegalStateException("Observable already bound");
		}
		o.subscribe((prev, curr) -> set(curr));
		bound = true;
	}
	
	/**
	 * Binds this observable to the given observable applying the given mapping
	 *
	 * <p>
	 * Sets this observable to store the result of calling the given mapping function with
	 * the value of the given observable whenever that changes.
	 * </p>
	 *
	 * @param o the observable to bind this observable to
	 * @param function the mapping function to map values from the given observable to this observable
	 *
	 * @throws IllegalStateException if this {@link Observable} was already bound
	 */
	public <U> void bind(Observable<U> o, Function<U, T> function)
	{
		if(bound) {
			throw new IllegalStateException("Observable already bound");
		}
		o.subscribe((prev, curr) -> set(function.apply(curr)));
		bound = true;
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
		 * Called when the value of an observable wrapper changes ({@link #set(Object)} is called)
		 *
		 * @param previousValue the previous value stored in the observable wrapper
		 * @param newValue the new value
		 */
		void onValueChanged(T previousValue, T newValue);
	}
}
