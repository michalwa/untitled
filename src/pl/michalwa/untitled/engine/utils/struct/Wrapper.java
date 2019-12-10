package pl.michalwa.untitled.engine.utils.struct;

import java.util.Objects;
import pl.michalwa.untitled.engine.utils.As;

/**
 * Encapsulates a single value of type {@code <T>} and provides a getter and a setter.
 * Allows referencing fields and properties. Subclasses can execute custom actions upon getting/setting the value.
 *
 * @param <T> the encapsulated value type
 */
public class Wrapper<T> implements As<T>
{
	/**
	 * The encapsulated value
	 */
	protected T value = null;
	
	/**
	 * Constructs an empty wrapper with a {@code null} value
	 */
	public Wrapper() {}
	
	/**
	 * Constructs a wrapper with the given value
	 *
	 * @param value the initial value for the wrapper
	 */
	public Wrapper(T value)
	{
		this.value = value;
	}
	
	/**
	 * Returns the value stored in this wrapper
	 *
	 * @return the value stored in this wrapper
	 */
	public T get()
	{
		return value;
	}
	
	/**
	 * Sets this wrapper to the given value
	 *
	 * @param value the new value to store
	 */
	public void set(T value)
	{
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		return "(" + value.toString() + ")";
	}
	
	/**
	 * Returns {@code true} in any of the following cases:
	 * <ul>
	 * <li>
	 *     {@code super.equals(obj)} returns {@code true}
	 * </li>
	 * <li>
	 *     the given object is a {@link Wrapper} and {@link Objects#equals(Object, Object)} returns {@code true}
	 *     for its value and the value of this wrapper
	 * </li>
	 * <li>
	 *     {@link Objects#equals(Object, Object)} returns {@code true} for the given object
	 *     and the value of this wrapper
	 * </li>
	 * </ul>
	 * Returns {@code false} otherwise.
	 *
	 * @param obj the object to compare
	 * @return whether the two objects are equal
	 */
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj)
			|| (obj instanceof Wrapper && Objects.equals(value, ((Wrapper<?>) obj).get()))
			|| Objects.equals(value, obj);
	}
	
	@Override
	public T as()
	{
		return get();
	}
}
