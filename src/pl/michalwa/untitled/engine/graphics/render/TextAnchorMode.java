package pl.michalwa.untitled.engine.graphics.render;

import java.awt.FontMetrics;
import java.util.Objects;
import java.util.function.BiFunction;
import pl.michalwa.untitled.engine.geom.Vector2f;

/**
 * Defines the way to reference the location of a rendered string in the coordinate system.
 * Same as {@link AnchorMode} but for text.
 */
public class TextAnchorMode
{
	/**
	 * Vertical text alignment options
	 */
	public enum Vertical
	{
		/**
		 * The ascent line
		 */
		ASCENT((y, f) -> y + f.getAscent()),
		
		/**
		 * The baseline
		 */
		BASE((y, f) -> y),
		
		/**
		 * The descent line
		 */
		DESCENT((y, f) -> y - f.getDescent()),
		
		/**
		 * The ascent line of the next line of text
		 */
		BOTTOM((y, f) -> y - f.getDescent() - f.getLeading());
		
		/**
		 * A function to calculate the Y coordinate of the baseline based on the
		 * Y coordinate of the anchor and the font metrics for the font used
		 */
		private final BiFunction<Float, FontMetrics, Float> baselineCalculator;
		
		Vertical(BiFunction<Float, FontMetrics, Float> baselineCalculator)
		{
			this.baselineCalculator = baselineCalculator;
		}
		
		/**
		 * Calculates the Y coordinate of the baseline of the text to be rendered
		 * based on the given Y coordinate of the anchor and the font metrics
		 * for the font used to render the text
		 *
		 * @param y the Y coordinate of the anchor
		 * @param metrics the font metrics for the font used
		 *
		 * @return the Y coordinate of the baseline of the text to be rendered
		 */
		public float getTop(float y, FontMetrics metrics)
		{
			return baselineCalculator.apply(y, metrics);
		}
	}
	
	/**
	 * Vertical alignment
	 */
	private final Vertical vertical;
	
	/**
	 * Horizontal alignment
	 */
	private final AnchorMode.Horizontal horizontal;
	
	/**
	 * Constructs a new text anchor mode with the given alignment options
	 *
	 * @param vertical the vertical alignment option
	 * @param horizontal the horizontal alignment option
	 */
	public TextAnchorMode(Vertical vertical, AnchorMode.Horizontal horizontal)
	{
		this.vertical = Objects.requireNonNull(vertical);
		this.horizontal = Objects.requireNonNull(horizontal);
	}
	
	/**
	 * Calculates the coordinates of the leftmost point of the text bounding box
	 * located on the baseline based on the given parameters
	 *
	 * @param x the X coordinate of the anchor
	 * @param y the Y coordinate of the anchor
	 * @param metrics the font metrics for the font used to render the text
	 * @param text the text to be rendered
	 *
	 * @return the coordinates of the leftmost point on the baseline
	 */
	public Vector2f getBaseLeft(float x, float y, FontMetrics metrics, String text)
	{
		return new Vector2f(horizontal.getLeft(x, metrics.stringWidth(text)), vertical.getTop(y, metrics));
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
	public AnchorMode.Horizontal getHorizontal()
	{
		return horizontal;
	}
	
	@Override
	public String toString()
	{
		return "TextAnchorMode(" + horizontal + ", " + vertical + ")";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj)
			
			|| (obj instanceof TextAnchorMode
			&& ((TextAnchorMode) obj).getVertical() == vertical
			&& ((TextAnchorMode) obj).getHorizontal() == horizontal);
	}
}
