package pl.michalwa.untitled.engine.utils.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A wrapper that allows dispatching custom actions when the data is changed
 */
public class Observable<T> extends Wrapper<T>
{
	/**
	 * Subscribed observers
	 */
	private final List<Observer<T>> observers = new ArrayList<>();
	
	/**
	 * List of active bindings
	 */
	private final List<Binding<T, ?>> bindings = new ArrayList<>();
	
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
		setFromBinding(value, null);
	}
	
	/**
	 * Stores the given value in this observable. If {@link Objects#equals(Object, Object)}
	 * returns {@code false} for the current/previous and the new value, all observers will be notified
	 * except the specified observable to mit. If {@code omit} is {@code null}, all observers will be notified
	 * with no exception
	 *
	 * @param value the new value
	 */
	private void setFromBinding(T value, Observable<?> omit)
	{
		T previous = this.value;
		this.value = value;
		
		if(!Objects.equals(previous, this.value)) {
			
			// Notify observers
			for(Observer<T> observer : observers) {
				observer.onValueChanged(previous, this.value);
			}
			
			// Update bindings
			for(Binding<T, ?> binding : bindings) {
				if(binding.target != omit) {
					binding.update(this);
				}
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
	 * Subscribes the given consumer for changes in this observable.
	 * This is the same as calling {@link #subscribe(Observer)} with an observer that ignores the previous value parameter.
	 *
	 * @param consumer the consumer to subscribe
	 */
	public void subscribe(Consumer<T> consumer)
	{
		observers.add((prev, curr) -> consumer.accept(curr));
	}
	
	/**
	 * Binds this observable to the given observable of the same type
	 *
	 * <p>
	 * Sets this observable to the value of the given observable whenever it changes.
	 * </p>
	 * <p>
	 * This observable is initially set to the value of the given observable after calling this method.
	 * </p>
	 *
	 * @param o the observable to bind this observable to
	 *
	 * @throws IllegalStateException if this {@link Observable} was already bound
	 */
	public void bindTo(Observable<T> o)
	{
		for(Binding<T, ?> binding : o.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		o.bindings.add(new Binding<>(this, Function.identity()));
		setFromBinding(o.value, o);
	}
	
	/**
	 * Creates a two-way binding between this and the given observable
	 *
	 * <p>
	 * Sets this observable to the value of the given observable whenever it changes.
	 * </p>
	 * <p>
	 * Sets the given observable to the value of this observable whenever it changes.
	 * </p>
	 * <p>
	 * This observable is initially set to the value of the given observable after calling this method.
	 * </p>
	 *
	 * @param o the observable to bind this observable to
	 *
	 * @throws IllegalStateException if this {@link Observable} was already bound
	 */
	public void bindTwoWay(Observable<T> o)
	{
		for(Binding<T, ?> binding : o.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		for(Binding<T, ?> binding : bindings) {
			if(binding.target == o) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		o.bindings.add(new Binding<>(this, Function.identity()));
		bindings.add(new Binding<>(o, Function.identity()));
		setFromBinding(o.value, o);
	}
	
	/**
	 * Binds this observable to the given observable applying the given mapping
	 *
	 * <p>
	 * Sets this observable to the result of calling the given mapping function with
	 * the value of the given observable whenever that changes.
	 * </p>
	 * <p>
	 * This observable is initially set to the result of calling the mapping function with
	 * the value of the given observable after calling this method.
	 * </p>
	 *
	 * @param o the observable to bind this observable to
	 * @param mapping the mapping function to map values from the given observable to this observable
	 */
	public <U> void bindTo(Observable<U> o, Function<U, T> mapping)
	{
		for(Binding<U, ?> binding : o.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		o.bindings.add(new Binding<>(this, mapping));
		setFromBinding(mapping.apply(o.value), o);
	}
	
	/**
	 * Creates a two-way binding between this and the given observable
	 *
	 * <p>
	 * Sets this observable to the result of calling the first mapping function with
	 * the value of the given observable whenever it changes.
	 * </p>
	 * <p>
	 * Sets the given observable to the result of calling the second mapping function
	 * with the value of this observable whenever it changes.
	 * </p>
	 * <p>
	 * This observable is initially set to the result of calling the first mapping function with
	 * the value of the given observable.
	 * </p>
	 *
	 * @param o the observable to bind this observable with
	 * @param back the mapping function from the given observable to this observable
	 * @param forth the mapping function from this observable to the given observable
	 * @param <U> the type of the given observable
	 */
	public <U> void bindTwoWay(Observable<U> o, Function<U, T> back, Function<T, U> forth)
	{
		for(Binding<U, ?> binding : o.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		for(Binding<T, ?> binding : bindings) {
			if(binding.target == o) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		o.bindings.add(new Binding<>(this, back));
		bindings.add(new Binding<>(o, forth));
		setFromBinding(back.apply(o.value), o);
	}
	
	/**
	 * Removes the binding of this observable to the given observable, if such exists
	 * (this observable will no longer read values from the given observable).
	 *
	 * @param o the observable to unbind from
	 */
	public void unbindFrom(Observable<?> o)
	{
		for(Binding<?, ?> binding : o.bindings) {
			if(binding.target == this) {
				o.bindings.remove(binding);
				break;
			}
		}
	}
	
	/**
	 * Removes the bindings of this observable to the given observable and
	 * from the given observable to this observable, if such exist.
	 *
	 * @param o the observable to unbind
	 */
	public void unbindTwoWay(Observable<?> o)
	{
		for(Binding<?, ?> binding : o.bindings) {
			if(binding.target == this) {
				o.bindings.remove(binding);
				break;
			}
		}
		for(Binding<?, ?> binding : bindings) {
			if(binding.target == o) {
				bindings.remove(binding);
				break;
			}
		}
	}
	
	/**
	 * Constructs a new observable and creates a one-way binding from this observable to it
	 *
	 * @param mapping the mapping function from this observable to the new observable
	 * @param <U> the type of the new observable
	 *
	 * @return the new bound observable
	 *
	 * @see #bindTo(Observable, Function)
	 */
	public <U> Observable<U> map(Function<T, U> mapping)
	{
		Observable<U> observable = new Observable<>(mapping.apply(value));
		observable.bindTo(this, mapping);
		return observable;
	}
	
	/**
	 * Constructs a new observable and creates a two-way binding between it and this observable
	 *
	 * @param forth the mapping function from this obserable to the new observable
	 * @param back the mapping function from the new observable to this observable
	 * @param <U> the type of the new observable
	 *
	 * @return the new bound observable
	 *
	 * @see #bindTwoWay(Observable, Function, Function)
	 */
	public <U> Observable<U> mapTwoWay(Function<T, U> forth, Function<U, T> back)
	{
		Observable<U> observable = new Observable<>(forth.apply(value));
		observable.bindTwoWay(this, forth, back);
		return observable;
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
	
	/**
	 * A one-way binding between two observables
	 *
	 * @param <T> the type of the first observable
	 * @param <U> the type of the second observable
	 */
	private static class Binding<T, U>
	{
		/**
		 * The target observable
		 */
		final Observable<U> target;
		
		/**
		 * The mapping function
		 */
		final Function<T, U> mapping;
		
		/**
		 * Constructs a new binding
		 *
		 * @param target the target observable
		 * @param mapping the mapping function
		 */
		Binding(Observable<U> target, Function<T, U> mapping)
		{
			this.target = target;
			this.mapping = mapping;
		}
		
		/**
		 * Updates this binding with the given value of the source observable
		 *
		 * @param source the source observable
		 */
		void update(Observable<T> source)
		{
			target.setFromBinding(mapping.apply(source.value), target);
		}
	}
}
