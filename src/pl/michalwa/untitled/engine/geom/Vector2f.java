package pl.michalwa.untitled.engine.geom;

/**
 * A 2d vector consisting of two coordinates which are 32-bit floating point numbers
 */
public class Vector2f
{
	public static final Vector2f ZERO = new Vector2f(0.0f, 0.0f);
	
	/**
	 * The X coordinate
	 */
	public final float x;
	
	/**
	 * The Y coordinate
	 */
	public final float y;
	
	/**
	 * Constructs a new 2d vector with the given coordinates
	 *
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 */
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the magnitude (length) of this vector
	 *
	 * @return the magnitude of this vector
	 */
	public float mag()
	{
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	/**
	 * Returns the angle between this vector and the X-axis in radians
	 * (vector {@code [0, 1]} has a heading 0).
	 *
	 * @return the direction of this vector
	 */
	public float dir()
	{
		return (float) Math.atan2(this.y, this.x);
	}
	
	/**
	 * Returns the sum of the two vectors
	 *
	 * @param other the other vector to sum with this vector
	 *
	 * @return the sum of this vector and the given vector
	 */
	public Vector2f add(Vector2f other)
	{
		return new Vector2f(this.x + other.x, this.y + other.y);
	}
	
	/**
	 * Returns the difference of the two vectors
	 *
	 * @param other the other vector to subtract from this vector
	 *
	 * @return the result of subtracting the given vector from this vector
	 */
	public Vector2f sub(Vector2f other)
	{
		return new Vector2f(this.x - other.x, this.y - other.y);
	}
	
	/**
	 * Returns the component-wise product of this vector and the given scalar
	 *
	 * @param f the scalar to multiply this vector by
	 *
	 * @return the component-wise product of this vector and the given scalar
	 */
	public Vector2f mult(float f)
	{
		return new Vector2f(this.x * f, this.y * f);
	}
	
	/**
	 * Returns the component-wise quotient of this vector and the given scalar
	 *
	 * @param f the scalar to divide this vector by
	 *
	 * @return the component-wise quotient of this vector and the given scalar
	 */
	public Vector2f div(float f)
	{
		return new Vector2f(this.x / f, this.y / f);
	}
	
	/**
	 * Returns the dot product of the two vectors
	 *
	 * @param other the vector to dot multiply this vector by
	 *
	 * @return the dot product of this vector and the given vector
	 */
	public float dot(Vector2f other)
	{
		return this.x * other.x + this.y * other.y;
	}
	
	/**
	 * Returns the cross product of the two vectors
	 *
	 * @param other the vector to cross multiply this vector by
	 *
	 * @return the cross product of this vector and the given vector
	 */
	public float cross(Vector2f other)
	{
		return this.x * other.y - this.y * other.x;
	}
	
	/**
	 * Returns the distance between the terminal points of the two vectors
	 *
	 * @param other the other vector
	 *
	 * @return the distance between the terminal point of this vector and the given vector
	 */
	public float distanceTo(Vector2f other)
	{
		return other.sub(this).mag();
	}
	
	/**
	 * Returns a normalized copy of this vector, one that has the same direction
	 * but a magnitude of 1.
	 *
	 * @return a normalized copy of this vector
	 */
	public Vector2f normalized()
	{
		return this.div(this.mag());
	}
	
	/**
	 * Returns the projection of this vector onto the given vector
	 *
	 * @param other the vector to project this vector onto
	 *
	 * @return the projection of this vector onto the given vector
	 */
	public Vector2f projectOnto(Vector2f other)
	{
		// b * ((a * b) / |b|^2)
		return other.mult(this.dot(other) / (other.x * other.x + other.y * other.y));
	}
	
	/**
	 * Returns a new {@link Vector2f} that has the same coordinates as this vector,
	 * but cast to integers
	 *
	 * @return this vector as {@link Vector2f}
	 */
	public Vector2f toInt()
	{
		return new Vector2f((int) this.x, (int) this.y);
	}
	
	/**
	 * Returns {@code true} in any of the following cases:<ul>
	 *     <li>{@code this.equals(obj)} return {@code true}</li>
	 *     <li>the given object is a {@link Vector2f} and its coordinates
	 *     are equal to the cooridnates of this vector</li>
	 *     <li>the given object is a {@link Vector2i} and its coordinates
	 *     are equal to the coordinates of this vector</li>
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
			
			|| (obj instanceof Vector2f
			&& this.x == ((Vector2f) obj).x
			&& this.y == ((Vector2f) obj).y)
			
			|| (obj instanceof Vector2i
			&& this.x == ((Vector2i) obj).x
			&& this.y == ((Vector2i) obj).y);
	}
	
	@Override
	public String toString()
	{
		return "[" + this.x + ", " + this.y + "]";
	}
}
