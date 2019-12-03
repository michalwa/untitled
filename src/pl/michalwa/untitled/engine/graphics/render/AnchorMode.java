package pl.michalwa.untitled.engine.graphics.render;

import pl.michalwa.untitled.engine.geom.Vector2f;

/**
 * Defines the way to reference the location of a shape in the coordinate system
 */
public enum AnchorMode
{
	/**
	 * The anchor is located in the top-left corner of the shape's bounding box
	 */
	TOP_LEFT(0.0f, 0.0f),
	
	/**
	 * The anchor is located in the center of the top edge of the shape's bounding box
	 */
	TOP_CENTER(-0.5f, 0.0f),
	
	/**
	 * The anchor is located in the top-right corner of the shape's bounding box
	 */
	TOP_RIGHT(-1.0f, 0.0f),
	
	/**
	 * The anchor is located in the center of the left edge of the shape's bounding box
	 */
	CENTER_LEFT(0.0f, -0.5f),
	
	/**
	 * The anchor is located in the center of the shape's bounding box
	 */
	CENTER(-0.5f, -0.5f),
	
	/**
	 * The anchor is located in the center of the right edge of the shape's bounding box
	 */
	CENTER_RIGHT(-1.0f, -0.5f),
	
	/**
	 * The anchor is located in the bottom-left corner of the shape's bounding box
	 */
	BOTTOM_LEFT(0.0f, -1.0f),
	
	/**
	 * The anchor is located in the center of the bottom edge of the shape's bounding box
	 */
	BOTTOM_CENTER(-0.5f, 1.0f),
	
	/**
	 * The anchor is located in the bottom-right corner of the shape's bounding box
	 */
	BOTTOM_RIGHT(-1.0f, -1.0f);
	
	/**
	 * By how much the width will affect the location of the top-left corner of the bounding box
	 */
	private final float factorX;
	
	/**
	 * By how much the height will affect the location of the top-left corner of the bounding box
	 */
	private final float factorY;
	
	AnchorMode(float factorX, float factorY)
	{
		this.factorX = factorX;
		this.factorY = factorY;
	}
	
	/**
	 * Returns the coordinates of the top-left corner of the shape's bounding box
	 * for the given anchor and dimensions considering the anchor mode
	 *
	 * @param anchorX the X coordinate of the anchor
	 * @param anchorY the Y coordinate of the anchor
	 * @param width the width of the shape's bounding box
	 * @param height the height of the shape's bounding box
	 *
	 * @return the coordinates of the top-left corner of the shape's bounding box
	 */
	public Vector2f getTopLeft(float anchorX, float anchorY, float width, float height)
	{
		return new Vector2f(anchorX + width * factorX, anchorY + height * factorY);
	}
}
