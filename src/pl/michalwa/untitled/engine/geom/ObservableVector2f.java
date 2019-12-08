package pl.michalwa.untitled.engine.geom;

import pl.michalwa.untitled.engine.utils.struct.Observable;

/**
 * An observable wrapper for a {@link Vector2f}.
 * Contains convenience methods for working with integers.
 */
public class ObservableVector2f extends Observable<Vector2f>
{
	/**
	 * Constructs an empty observable wrapper for a {@link Vector2f}
	 */
	public ObservableVector2f() {}
	
	/**
	 * Constructs a new observable wrapper with the given initial value
	 *
	 * @param v the initial value for this observable
	 */
	public ObservableVector2f(Vector2f v)
	{
		super(v);
	}
	
	/**
	 * Adds the given vector to the vector stored in this wrapper.
	 * Equivalent of calling {@code set(get().add(v))}
	 *
	 * @param v the vector to add
	 */
	public void add(Vector2f v)
	{
		set(get().add(v));
	}
	
	/**
	 * Adds the specified vector to the vector stored in this wrapper.
	 * Equivalent of calling {@code set(get().add(v))}
	 *
	 * @param x the X coordinate of the vector to add
	 * @param y the Y coordinate of the vector to add
	 */
	public void add(float x, float y)
	{
		set(get().add(x, y));
	}
	
	/**
	 * Subtracts the given vector from the vector stored in this wrapper.
	 * Equivalent of calling {@code set(get().sub(v))}
	 *
	 * @param v the vector to subtract
	 */
	public void sub(Vector2f v)
	{
		set(get().sub(v));
	}
	
	/**
	 * Subtracts the specified vector from the vector stored in this wrapper.
	 * Equivalent of calling {@code set(get().sub(v))}
	 *
	 * @param x the X coordinate of the vector to subtract
	 * @param y the Y coordinate of the vector to subtract
	 */
	public void sub(float x, float y)
	{
		set(get().sub(x, y));
	}
	
	/**
	 * Multiplies the vector of this wrapper by the given scalar.
	 * Equivalent of calling {@code set(get().mult(k))}
	 *
	 * @param k the scalar to multiply by
	 */
	public void mult(float k)
	{
		set(get().mult(k));
	}
	
	/**
	 * Performs component-wise division of the vector stored in this wrapper by the given scalar.
	 * Equivalent of calling {@code set(get().div(k))}
	 *
	 * @param k the scalar to divide by
	 */
	public void div(float k)
	{
		set(get().div(k));
	}
}

