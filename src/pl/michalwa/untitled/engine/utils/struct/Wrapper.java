package pl.michalwa.untitled.engine.utils.struct;

/**
 * Encapsulates a single value of type {@code <T>} and provides a getter and a setter.
 * Allows referencing fields and properties. Subclasses can execute custom actions upon getting/setting the value.
 *
 * @param <T> the encapsulated value type
 */
public class Wrapper<T>
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
}
