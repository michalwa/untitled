package pl.michalwa.untitled.engine.utils.struct;

/**
 * An observable wrapper for a {@code float} value.
 * Contains convenience methods for working with floats.
 */
public class ObservableFloat extends Observable<Float>
{
	/**
	 * Constructs an empty observable wrapper for a float value
	 */
	public ObservableFloat() {}
	
	/**
	 * Constructs a new observable wrapper with the given initial value
	 *
	 * @param f the initial value for this observable
	 */
	public ObservableFloat(float f)
	{
		super(f);
	}
	
	/**
	 * Adds the given value to the value of this wrapper.
	 * Equivalent of calling {@code set(get() + f)}
	 *
	 * @param f the value to add
	 */
	public void add(float f)
	{
		set(get() + f);
	}
	
	/**
	 * Subtracts the given value from the value of this wrapper.
	 * Equivalent of calling {@code set(get() - f)}
	 *
	 * @param f the value to subtract
	 */
	public void sub(float f)
	{
		set(get() - f);
	}
	
	/**
	 * Multiplies the value of this wrapper by the given factor.
	 * Equivalent of calling {@code set(get() * f)}
	 *
	 * @param f the value to multiply by
	 */
	public void mult(float f)
	{
		set(get() * f);
	}
	
	/**
	 * Divides the value of this wrapper by the given factor.
	 * Equivalent of calling {@code set(get() / f)}
	 *
	 * @param f the value to divide by
	 */
	public void div(float f)
	{
		set(get() / f);
	}
}
