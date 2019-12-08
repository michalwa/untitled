package pl.michalwa.untitled.engine.utils.struct;

import java.util.*;
import java.util.function.BiFunction;
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
	private final List<Binding<?>> bindings = new ArrayList<>();
	
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
	private void setFromBinding(T value, Collection<Observable<?>> omit)
	{
		T previous = this.value;
		this.value = value;
		
		if(!Objects.equals(previous, this.value)) {
			
			// Notify observers
			for(Observer<T> observer : observers) {
				observer.onValueChanged(previous, this.value);
			}
			
			// Update bindings
			for(Binding<?> binding : bindings) {
				if(omit == null || !omit.contains(binding.target)) {
					binding.update();
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
		for(Binding<?> binding : o.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		Binding<T> binding = new SingleBinding<>(o, this, Function.identity());
		o.bindings.add(binding);
		binding.update();
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
		for(Binding<?> binding : o.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		for(Binding<?> binding : bindings) {
			if(binding.target == o) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		Binding<T> firstBinding = new SingleBinding<>(o, this, Function.identity());
		o.bindings.add(firstBinding);
		bindings.add(new SingleBinding<>(this, o, Function.identity()));
		firstBinding.update();
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
		for(Binding<?> binding : o.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		Binding<T> binding = new SingleBinding<>(o, this, mapping);
		o.bindings.add(binding);
		binding.update();
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
		for(Binding<?> binding : o.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		for(Binding<?> binding : bindings) {
			if(binding.target == o) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		Binding<T> firstBinding = new SingleBinding<>(o, this, back);
		o.bindings.add(firstBinding);
		bindings.add(new SingleBinding<>(this, o, forth));
		firstBinding.update();
	}
	
	/**
	 * Binds this observable to both of the given observables.
	 *
	 * <p>
	 * Whenever the value of any of the given observables changes, this observable is set
	 * to the result of calling the given mapping function with the values of the observables.
	 * </p>
	 * <p>
	 * This observable is initially set to the result of calling the mapping function
	 * with the values of the two observables.
	 * </p>
	 *
	 * @param u the first observable to bind to
	 * @param v the second observable to bind to
	 * @param mapping the mapping function from the two observables to this observable
	 * @param <U> the type of the first observable
	 * @param <V> the type of the second observable
	 */
	public <U, V> void bindTo(Observable<U> u, Observable<V> v, BiFunction<U, V, T> mapping)
	{
		for(Binding<?> binding : u.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		for(Binding<?> binding : v.bindings) {
			if(binding.target == this) {
				throw new IllegalStateException("Binding already exists");
			}
		}
		Binding<T> binding = new DoubleBinding<>(u, v, this, mapping);
		u.bindings.add(binding);
		v.bindings.add(binding);
		binding.update();
	}
	
	/**
	 * Removes the binding of this observable to the given observable, if such exists
	 * (this observable will no longer read values from the given observable).
	 *
	 * @param o the observable to unbind from
	 */
	public void unbindFrom(Observable<?> o)
	{
		for(Binding<?> binding : o.bindings) {
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
		for(Binding<?> binding : o.bindings) {
			if(binding.target == this) {
				o.bindings.remove(binding);
				break;
			}
		}
		for(Binding<?> binding : bindings) {
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
		Observable<U> observable = new Observable<>(mapping.apply(get()));
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
		Observable<U> observable = new Observable<>(forth.apply(get()));
		observable.bindTwoWay(this, forth, back);
		return observable;
	}
	
	/**
	 * Constructs a new observable and creates a double two-way binding between the two
	 * given source observables and the new observable.
	 *
	 * @param s1 the first source observable
	 * @param s2 the second source observable
	 * @param mapping the mapping from the two source observables to the new observable
	 * @param <S1> the type of the first source observable
	 * @param <S2> the type of the second source observable
	 * @param <T> the type of the new observable
	 *
	 * @return the new observable
	 */
	public static <S1, S2, T> Observable<T> map(
		Observable<S1> s1,
		Observable<S2> s2,
		BiFunction<S1, S2, T> mapping
	) {
		Observable<T> observable = new Observable<>(mapping.apply(s1.get(), s2.get()));
		observable.bindTo(s1, s2, mapping);
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
	 * A one-way binding between observables
	 *
	 * @param <T> the type of the target observable
	 */
	private static abstract class Binding<T>
	{
		/**
		 * The target observable
		 */
		final Observable<T> target;
		
		Binding(Observable<T> target)
		{
			this.target = target;
		}
		
		/**
		 * Updates this binding by mapping and setting the value of the target observable
		 */
		abstract void update();
	}
	
	/**
	 * A simple one-way binding between two observables
	 *
	 * @param <S> the type of the source observable
	 * @param <T> the type of the target observable
	 */
	private static class SingleBinding<S, T> extends Binding<T>
	{
		/**
		 * The source observable
		 */
		final Observable<S> source;
		
		/**
		 * The mapping function
		 */
		final Function<S, T> mapping;
		
		/**
		 * Constructs a new binding
		 *
		 * @param source the source observable
		 * @param target the target observable
		 * @param mapping the mapping function
		 */
		SingleBinding(
			Observable<S> source,
			Observable<T> target,
			Function<S, T> mapping
		) {
			super(target);
			this.source = source;
			this.mapping = mapping;
		}
		
		@Override
		void update()
		{
			T value = mapping.apply(source.get());
			target.setFromBinding(value, Collections.singletonList(target));
		}
	}
	
	/**
	 * A double one-way binding between three observables
	 *
	 * @param <S1> the type of the first source observable
	 * @param <S2> the type of the second source observable
	 * @param <T> the type of the target observable
	 */
	private static class DoubleBinding<S1, S2, T> extends Binding<T>
	{
		/**
		 * The first source observable
		 */
		final Observable<S1> firstSource;
		
		/**
		 * The second source observable
		 */
		final Observable<S2> secondSource;
		
		/**
		 * The mapping function
		 */
		final BiFunction<S1, S2, T> mapping;
		
		/**
		 * Constructs a new double one-way binding
		 *
		 * @param firstSource the first source observable
		 * @param secondSource the second source observable
		 * @param target the target observable
		 * @param mapping the mapping function
		 */
		DoubleBinding(
			Observable<S1> firstSource,
			Observable<S2> secondSource,
			Observable<T> target,
			BiFunction<S1, S2, T> mapping
		) {
			super(target);
			this.firstSource = firstSource;
			this.secondSource = secondSource;
			this.mapping = mapping;
		}
		
		@Override
		void update()
		{
			T value = mapping.apply(firstSource.get(), secondSource.get());
			target.setFromBinding(value, Arrays.asList(firstSource, secondSource));
		}
	}
}
