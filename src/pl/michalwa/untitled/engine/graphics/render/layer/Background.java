package pl.michalwa.untitled.engine.graphics.render.layer;

import pl.michalwa.untitled.engine.graphics.Color;
import pl.michalwa.untitled.engine.graphics.render.RenderingContext;

/**
 * Background layer. Fills the canvas with a solid color. Has a default index of {@code Integer.MIN_VALUE}
 */
public class Background extends Layer
{
	/**
	 * The background color
	 */
	private final Color color;
	
	/**
	 * Constructs a new background layer
	 *
	 * @param color the background color
	 */
	public Background(Color color)
	{
		this.color = color;
	}
	
	/**
	 * Constructs a new background layer
	 *
	 * @param index the sorting index for this layer
	 * @param color the background color
	 */
	public Background(int index, Color color)
	{
		super(index);
		this.color = color;
	}
	
	@Override
	public void render(RenderingContext ctx)
	{
		ctx.fill(color);
	}
}
