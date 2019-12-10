package pl.michalwa.untitled.engine.graphics.render;

import pl.michalwa.untitled.engine.geom.Vector2;
import pl.michalwa.untitled.engine.geom.Vector2f;
import pl.michalwa.untitled.engine.graphics.Color;
import pl.michalwa.untitled.engine.graphics.font.Font;
import pl.michalwa.untitled.engine.utils.As;

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
	 * Sets the anchor mode for drawing shapes
	 *
	 * @param vertical the vertical alignment option
	 * @param horizontal the horizontal alignment option
	 */
	default void setAnchorMode(AnchorMode.Vertical vertical, AnchorMode.Horizontal horizontal)
	{
		setAnchorMode(new AnchorMode(vertical, horizontal));
	}
	
	/**
	 * Sets the anchor mode for drawing text
	 *
	 * @param mode the anchor mode to set
	 */
	void setTextAnchorMode(TextAnchorMode mode);
	
	/**
	 * Sets the anchor mode for drawing text
	 *
	 * @param vertical the vertical alignment option
	 * @param horizontal the horizontal alignment option
	 */
	default void setTextAnchorMode(TextAnchorMode.Vertical vertical, AnchorMode.Horizontal horizontal)
	{
		setTextAnchorMode(new TextAnchorMode(vertical, horizontal));
	}
	
	/**
	 * Sets the font to be used to render text
	 *
	 * @param font the font to use to render text
	 */
	void setFont(Font font);
	
	/**
	 * Sets the size of the font to be used to render text
	 *
	 * @param size the new font size
	 */
	void setFontSize(float size);
	
	/**
	 * Sets the style of the font to be used to render text
	 *
	 * @param style the new font style
	 */
	void setFontStyle(Font.Style style);
	
	/**
	 * Draws a line with the currently set stroke options from one of the specified points to the other.
	 * The given floating point values may get rounded down to integers by the implementation.
	 *
	 * @param x1 the X coordinate of the first point of the line
	 * @param y1 the Y coordinate of the first point of the line
	 * @param x2 the X coordinate of the second point of the line
	 * @param y2 the Y coordinate of the second point of the line
	 */
	void drawLine(float x1, float y1, float x2, float y2);
	
	/**
	 * Draws a line with the currently set stroke options from one of the specified points to the other.
	 * The given floating point values may get rounded down to integers by the implementation.
	 *
	 * @param a the first point of the line to draw
	 * @param b the second point of the line to draw
	 */
	default void drawLine(As<? extends Vector2> a, As<? extends Vector2> b)
	{
		Vector2f v1 = a.as().asVector2f(), v2 = b.as().asVector2f();
		drawLine(v1.x, v1.y, v2.x, v2.y);
	}
	
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
	 * Draws a rectangle with the currently set fill and stroke options.
	 * The given floating point values may get rounded down to integers by the implementation.
	 *
	 * @param anchor the anchor to draw the rectangle at (see: {@link #setAnchorMode(AnchorMode)})
	 * @param dimensions the dimensions of the rectangle to draw where the X coordinate
	 *                   represents the width and the Y coordinate represents the height
	 */
	default void drawRect(As<? extends Vector2> anchor, As<? extends Vector2> dimensions)
	{
		Vector2f anch = anchor.as().asVector2f(), dim = dimensions.as().asVector2f();
		drawRect(anch.x, anch.y, dim.x, dim.y);
	}
	
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
	 * Draws an ellipse with the currently set fill and stroke options.
	 * The given floating point values may get rounded down to integers by the implementation.
	 *
	 * @param anchor the anchor to draw the rectangle at (see: {@link #setAnchorMode(AnchorMode)})
	 * @param dimensions the dimensions of the bounding box of the ellipse to draw where
	 *                   the X coordinate represents the width andthe Y coordinate represents the height
	 */
	default void drawEllipse(As<? extends Vector2> anchor, As<? extends Vector2> dimensions)
	{
		Vector2f anch = anchor.as().asVector2f(), dim = dimensions.as().asVector2f();
		drawEllipse(anch.x, anch.y, dim.x, dim.y);
	}
	
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
	 * @param radius the radius of the circle
	 *
	 * @see #drawEllipse(float, float, float, float)
	 * @see #setAnchorMode(AnchorMode)
	 */
	default void drawCircle(float x, float y, float radius)
	{
		drawEllipse(x, y, radius * 2.0f, radius * 2.0f);
	}
	
	/**
	 * Draws a circle with the currently set fill and stroke options.
	 * The given floating point values may get rounded down to integers by the implementation.
	 *
	 * @param anchor the anchor to draw the rectangle at (see: {@link #setAnchorMode(AnchorMode)})
	 * @param radius the radius of the circle to draw
	 *
	 * @see #drawEllipse(float, float, float, float)
	 */
	default void drawCircle(As<? extends Vector2> anchor, float radius)
	{
		Vector2f anch = anchor.as().asVector2f();
		drawCircle(anch.x, anch.y, radius);
	}
	
	/**
	 * Renders text with the currently set fill color
	 *
	 * @param x the X coordinate of the anchor to render the text at
	 * @param y the Y coordinate of the anchor to render the text at
	 * @param text the text to render
	 */
	void drawText(float x, float y, String text);
	
	/**
	 * Renders text with the currently set fill color
	 *
	 * @param anchor the position of the anchor to draw the text at
	 * @param text the text to draw
	 */
	default void drawText(As<? extends Vector2> anchor, String text)
	{
		Vector2f anch = anchor.as().asVector2f();
		drawText(anch.x, anch.y, text);
	}
}
