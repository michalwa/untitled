package pl.michalwa.untitled.engine.geom;

/**
 * A 2d vector consisting of two coordinates which are 32-bit signed integers
 */
public class Vector2i
{
	/**
	 * The X coordinate
	 */
	final int x;
	
	/**
	 * The y coordinate
	 */
	final int y;
	
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
		return new Vector2i(this.x + other.x, this.y + other.y);
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
		return new Vector2i(this.x - other.x, this.y - other.y);
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
}
