package pl.michalwa.untitled.engine.graphics.render.layer;

import pl.michalwa.untitled.engine.graphics.render.RenderingContext;

/**
 * Renderer layer - used to group and sort drawable content
 */
public abstract class Layer implements Comparable<Layer>
{
	/**
	 * Sorting index
	 */
	private final int index;
	
	/**
	 * Constructs a renderer layer with a default sorting index of 0
	 */
	protected Layer()
	{
		this.index = 0;
	}
	
	/**
	 * Constructs a renderer layer with the given sorting index
	 *
	 * @param index the sorting index for this layer
	 */
	protected Layer(int index)
	{
		this.index = index;
	}
	
	@Override
	public int compareTo(Layer other)
	{
		return this.index - other.index;
	}
	
	/**
	 * Renders the layer in the given context.
	 * Before each layer is rendered, the renderer must ensure no information is left
	 * in the rendering context by the previous layer.
	 *
	 * @param ctx the rendering context
	 */
	public abstract void render(RenderingContext ctx);
}
