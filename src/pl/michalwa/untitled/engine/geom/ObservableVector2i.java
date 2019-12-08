package pl.michalwa.untitled.engine.geom;

import pl.michalwa.untitled.engine.utils.struct.Observable;

/**
 * An observable wrapper for a {@link Vector2i}.
 * Contains convenience methods for working with {@link Vector2i}s.
 */
public class ObservableVector2i extends Observable<Vector2i>
{
	/**
	 * Constructs an empty observable wrapper for a {@link Vector2i}
	 */
	public ObservableVector2i() {}
	
	/**
	 * Constructs a new observable wrapper with the given initial value
	 *
	 * @param v the initial value for this observable
	 */
	public ObservableVector2i(Vector2i v)
	{
		super(v);
	}
	
	/**
	 * Adds the given vector to the vector stored in this wrapper.
	 * Equivalent of calling {@code set(get().add(v))}
	 *
	 * @param v the vector to add
	 */
	public void add(Vector2i v)
	{
		set(get().add(v));
	}
	
	/**
	 * Subtracts the given vector from the vector stored in this wrapper.
	 * Equivalent of calling {@code set(get().sub(v))}
	 *
	 * @param v the vector to subtract
	 */
	public void sub(Vector2i v)
	{
		set(get().sub(v));
	}
	
	/**
	 * Multiplies the vector of this wrapper by the given scalar.
	 * Equivalent of calling {@code set(get().mult(k))}
	 *
	 * @param k the scalar to multiply by
	 */
	public void mult(int k)
	{
		set(get().mult(k));
	}
	
	/**
	 * Performs component-wise integer division of the vector stored in this wrapper by the given integer scalar.
	 * Equivalent of calling {@code set(get().div(k))}
	 *
	 * @param k the scalar to divide by
	 */
	public void div(int k)
	{
		set(get().div(k));
	}
}
