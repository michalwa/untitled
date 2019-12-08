package pl.michalwa.untitled.engine.utils.struct;

/**
 * An observable wrapper for an {@code int} value.
 * Contains convenience methods for working with integers.
 */
public class ObservableInt extends Observable<Integer>
{
	/**
	 * Constructs an empty observable wrapper for an int value
	 */
	public ObservableInt() {}
	
	/**
	 * Constructs a new observable wrapper with the given initial value
	 *
	 * @param i the initial value for this observable
	 */
	public ObservableInt(int i)
	{
		super(i);
	}
	
	/**
	 * Adds the given value to the value of this wrapper.
	 * Equivalent of calling {@code set(get() + i)}
	 *
	 * @param i the value to add
	 */
	public void add(int i)
	{
		set(get() + i);
	}
	
	/**
	 * Subtracts the given value from the value of this wrapper.
	 * Equivalent of calling {@code set(get() - i)}
	 *
	 * @param i the value to subtract
	 */
	public void sub(int i)
	{
		set(get() - i);
	}
	
	/**
	 * Multiplies the value of this wrapper by the given factor.
	 * Equivalent of calling {@code set(get() * i)}
	 *
	 * @param i the value to multiply by
	 */
	public void mult(int i)
	{
		set(get() * i);
	}
	
	/**
	 * Performs integer division of the value stored in this wrapper by the given integer.
	 * Equivalent of calling {@code set(get() / i)}
	 *
	 * @param i the value to divide by
	 */
	public void div(int i)
	{
		set(get() / i);
	}
}
