package pl.michalwa.untitled.engine.graphics.render;

/**
 * An object that can render itself to the game canvas using a {@link RenderingContext}
 */
public interface Renderable
{
	/**
	 * Renders the object to the game canvas using the given context
	 *
	 * @param ctx the context to use
	 */
	void render(RenderingContext ctx);
}
