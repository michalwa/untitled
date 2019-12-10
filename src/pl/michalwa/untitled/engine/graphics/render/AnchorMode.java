package pl.michalwa.untitled.engine.graphics.render;

import java.util.Objects;
import pl.michalwa.untitled.engine.geom.Vector2f;

/**
 * Defines the way to reference the location of a shape in the coordinate system
 */
public class AnchorMode
{
	/**
	 * Vertical alignment options
	 */
	public enum Vertical
	{
		/**
		 * The anchor is located on the top edge of the bounding box
		 */
		TOP(0.0f),
		
		/**
		 * The anchor is located on the horizontal line in the center of the bounding box
		 */
		CENTER(-0.5f),
		
		/**
		 * The anchor is located on the bottom edge of the bounding box
		 */
		RIGHT(-1.0f);
		
		/**
		 * How much the height of the bounding box affects the location of the top edge
		 */
		private final float factor;
		
		Vertical(float factor)
		{
			this.factor = factor;
		}
		
		/**
		 * Calculates the Y coordinate of the top edge of the bounding box of the object to draw
		 *
		 * @param y the Y coordinate of the anchor
		 * @param height the height of the object
		 *
		 * @return the Y coordinate of the top edge of the bounding box of the object to draw
		 */
		public float getTop(float y, float height)
		{
			return y + height * factor;
		}
	}
	
	/**
	 * Horizontal alignment options
	 */
	public enum Horizontal
	{
		/**
		 * The anchor is located on the left edge of the bounding box
		 */
		LEFT(0.0f),
		
		/**
		 * The anchor is located on the vertical line in the center of the bounding box
		 */
		CENTER(-0.5f),
		
		/**
		 * The anchor is located on the right edge of the bounding box
		 */
		RIGHT(-1.0f);
		
		/**
		 * How much the width of the bounding box affects the location of the left edge
		 */
		private final float factor;
		
		Horizontal(float factor)
		{
			this.factor = factor;
		}
		
		/**
		 * Calculates the X coordinate of the left edge of the bounding box of the object to draw
		 *
		 * @param x the X coordinate of the anchor
		 * @param width the width of the object
		 *
		 * @return the X coordinate of the left edge of the bounding box of the object to draw
		 */
		public float getLeft(float x, float width)
		{
			return x + width * factor;
		}
	}
	
	/**
	 * The vertical alignment
	 */
	private final Vertical vertical;
	
	/**
	 * The horizontal alignment
	 */
	private final Horizontal horizontal;
	
	/**
	 * Constructs a new anchor mode with the given alignment options
	 *
	 * @param vertical the vertical alignment option
	 * @param horizontal the horizontal alignment option
	 */
	public AnchorMode(Vertical vertical, Horizontal horizontal)
	{
		this.vertical = Objects.requireNonNull(vertical);
		this.horizontal = Objects.requireNonNull(horizontal);
	}
	
	/**
	 * Calculates the coordinates of the top-left corner of the shape's bounding box
	 * for the given anchor and dimensions considering the anchor mode.
	 *
	 * <p>
	 * Equivalent of constructing a {@link Vector2f} of the results of calling
	 * {@link Horizontal#getLeft(float, float)} with {@code anchorX}, {@code width}
	 * and {@link Vertical#getTop(float, float)} with {@code anchorY}, {@code height}
	 * </p>
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
		return new Vector2f(horizontal.getLeft(anchorX, width), vertical.getTop(anchorY, height));
	}
	
	/**
	 * Returns the vertical alignment of this anchor mode
	 *
	 * @return the vertical alignment
	 */
	public Vertical getVertical()
	{
		return vertical;
	}
	
	/**
	 * Returns the horizontal alignment of this anchor mode
	 *
	 * @return the horizontal alignment
	 */
	public Horizontal getHorizontal()
	{
		return horizontal;
	}
	
	@Override
	public String toString()
	{
		return "AnchorMode(" + horizontal + ", " + vertical + ")";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj)
			
			|| (obj instanceof AnchorMode
			&& ((AnchorMode) obj).getVertical() == vertical
			&& ((AnchorMode) obj).getHorizontal() == horizontal);
	}
}
