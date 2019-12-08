package pl.michalwa.untitled.engine.geom;

/**
 * A 2d vector consisting of two coordinates which are 32-bit signed integers
 */
public class Vector2i
{
	public static final Vector2i ZERO = new Vector2i(0, 0);
	
	/**
	 * The X coordinate
	 */
	public final int x;
	
	/**
	 * The y coordinate
	 */
	public final int y;
	
	/**
	 * Constructs a new 2d vector with the given coordinates
	 *
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 */
	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the sum of the two vectors
	 *
	 * @param other the other vector to sum with this vector
	 *
	 * @return the sum of this vector and the given vector
	 */
	public Vector2i add(Vector2i other)
	{
		return add(other.x, other.y);
	}
	
	/**
	 * Returns the sum of this vector and the vector with the specified coordinates
	 *
	 * @param x the X cooridnate of the vector to sum with this vector
	 * @param y the Y cooridnate of the vector to sum with this vector
	 *
	 * @return the sum of this vector and the vector with the specified coordinates
	 */
	public Vector2i add(int x, int y)
	{
		return new Vector2i(this.x + x, this.y + y);
	}
	
	/**
	 * Returns the difference of the two vectors
	 *
	 * @param other the other vector to subtract from this vector
	 *
	 * @return the result of subtracting the given vector from this vector
	 */
	public Vector2i sub(Vector2i other)
	{
		return sub(other.x, other.y);
	}
	
	/**
	 * Returns the difference of this vector and the vector with the specified coordinates
	 *
	 * @param x the X cooridnate of the vector to subtract from this vector
	 * @param y the Y cooridnate of the vector to subtract from this vector
	 *
	 * @return the difference of this vector and the vector with the specified coordinates
	 */
	public Vector2i sub(int x, int y)
	{
		return new Vector2i(this.x - x, this.y - y);
	}
	
	/**
	 * Returns the component-wise product of this vector and the given scalar
	 *
	 * @param k the scalar to multiply this vector by
	 *
	 * @return the component-wise product of this vector and the given scalar
	 */
	public Vector2i mult(int k)
	{
		return new Vector2i(this.x * k, this.y * k);
	}
	
	/**
	 * Returns the component-wise quotient of this vector and the given scalar
	 * (performs integer division of both coordinates)
	 *
	 * @param k the scalar to divide this vector by
	 *
	 * @return the result of integer division of this vector by the given scalar
	 */
	public Vector2i div(int k)
	{
		return new Vector2i(this.x / k, this.y / k);
	}
	
	/**
	 * Returns {@code true} in any of the following cases:<ul>
	 *     <li>{@code this.equals(obj)} return {@code true}</li>
	 *     <li>the given object is a {@link Vector2i} and its coordinates are equal to the cooridnates of this vector</li>
	 *     <li>the given object is a {@link Vector2f} and its coordinates are equal to the coordinates of this vector</li>
	 * </ul>
	 * Returns {@code false} otherwise.
	 *
	 * @param obj the object to compare
	 *
	 * @return whether the two objects are equal
	 */
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj)
			
			|| (obj instanceof Vector2i
			&& this.x == ((Vector2i) obj).x
			&& this.y == ((Vector2i) obj).y)
			
			|| (obj instanceof Vector2f
			&& this.x == ((Vector2f) obj).x
			&& this.y == ((Vector2f) obj).y);
	}
	
	/**
	 * Returns a new {@link Vector2f} that has the same coordinates as this vector
	 *
	 * @return this vector as {@link Vector2f}
	 */
	public Vector2f toFloat()
	{
		return new Vector2f(this.x, this.y);
	}
	
	@Override
	public String toString()
	{
		return "[" + this.x + ", " + this.y + "]";
	}
}
