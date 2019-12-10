package pl.michalwa.untitled.engine.geom;

/**
 * Defines generic functionality for 2-coordinate vector data types
 */
public interface Vector2
{
	/**
	 * Returns a copy of this vector as a {@link Vector2f}
	 *
	 * @return a copy of this vector as a {@link Vector2f}
	 */
	Vector2f asVector2f();
}
