package pl.michalwa.untitled.engine.graphics.render;

import pl.michalwa.untitled.engine.graphics.Color;

/**
 * Provides rendering functionality
 */
public interface RenderingContext
{
	/**
	 * Sets the color that will be used to fill shapes.
	 * If {@code color} is {@code null}, subsequent calls to shape-drawing methods will not use fill.
	 *
	 * @param color the new fill color
	 */
	void setFillColor(Color color);
	
	/**
	 * Sets the color that will be used to draw the outlines of shapes.
	 * If {@code color} is {@code null}, subsequent calls to shape-drawing methods will not draw the outline.
	 *
	 * @param color the new stroke color
	 */
	void setStrokeColor(Color color);
	
	/**
	 * Sets the width of strokes to be drawn
	 *
	 * @param width the new stroke width
	 */
	void setStrokeWidth(float width);
	
	/**
	 * Fills the entire drawing canvas with the given color. This method is independent
	 * from {@link #setFillColor(Color)} and will not read or modify the currently set fill color.
	 *
	 * @param color the color to fill the canvas with
	 */
	void fill(Color color);
	
	/**
	 * Sets the anchor mode for drawing shapes
	 *
	 * @param mode the anchor mode to set
	 */
	void setAnchorMode(AnchorMode mode);
	
	/**
	 * Draws a rectangle with the currently set fill and stroke options.
	 * The given floating point values may get rounded down to integers by the implementation.
	 *
	 * @param x the X coordinate of the anchor to draw the rectangle at
	 * @param y the Y coordinate of the anchor to draw the rectangle at
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 *               
	 * @see #setAnchorMode(AnchorMode) 
	 */
	void drawRect(float x, float y, float width, float height);
	
	/**
	 * Draws an ellipse with the currently set fill and stroke options.
	 * The given floating point values may get rounded down to integers by the implementation.
	 *
	 * @param x the X coordinate of the anchor to draw the ellipse at
	 * @param y the Y coordinate of the anchor to draw the ellipse at
	 * @param width the width of the ellipse's bounding box
	 * @param height the height of the ellipse's bounding box
	 *
	 * @see #setAnchorMode(AnchorMode)
	 */
	void drawEllipse(float x, float y, float width, float height);
	
	/**
	 * Draws a circle with the currently set fill and stroke options.
	 * The given floating point values may get rounded down to integers by the implementation.
	 *
	 * <p>
	 * The default implementation of this method calls {@link #drawEllipse(float, float, float, float)}
	 * with {@code diameter} passed as both {@code width} and {@code height}
	 * </p>
	 *
	 * @param x the X coordinate of the anchor to draw the circle at
	 * @param y the Y coordinate of the anchor to draw the circle at
	 * @param diameter the diameter of the circle
	 *
	 * @see #drawEllipse(float, float, float, float)
	 * @see #setAnchorMode(AnchorMode)
	 */
	default void drawCircle(float x, float y, float diameter)
	{
		drawEllipse(x, y, diameter, diameter);
	}
}
