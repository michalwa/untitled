package pl.michalwa.untitled.engine.graphics.render;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.*;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.geom.Vector2f;
import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.graphics.Color;
import pl.michalwa.untitled.engine.graphics.GraphicsDriver;
import pl.michalwa.untitled.engine.graphics.font.Font;
import pl.michalwa.untitled.engine.graphics.render.layer.Layer;
import pl.michalwa.untitled.engine.loop.GameLoop;
import pl.michalwa.untitled.engine.loop.events.Frame;

/**
 * Handles rendering on a high level
 */
public class Renderer implements Component, RenderingContext
{
	/**
	 * Graphics driver
	 */
	private GraphicsDriver driver = null;
	
	/**
	 * Rendering hints for {@link Graphics2D}
	 */
	private final Map<RenderingHints.Key, Object> renderingHints = new HashMap<>();
	
	/**
	 * Render layers
	 */
	private final List<Layer> layers = new ArrayList<>();
	
	/**
	 * The currently active rendering graphics
	 */
	private Graphics2D graphics = null;
	
	/**
	 * The currently set fill color ({@code null} for no fill)
	 */
	private Color fillColor = null;
	
	/**
	 * The currently set stroke/outline color ({@code null} for no outline)
	 */
	private Color strokeColor = null;
	
	/**
	 * The currently set stroke style
	 */
	private float strokeWidth = 1.0f;
	
	/**
	 * The currently set anchor mode
	 */
	private AnchorMode anchorMode = new AnchorMode(AnchorMode.Vertical.TOP, AnchorMode.Horizontal.LEFT);
	
	/**
	 * The currently set text anchor mode
	 */
	private TextAnchorMode textAnchorMode = new TextAnchorMode(TextAnchorMode.Vertical.BASE, AnchorMode.Horizontal.LEFT);
	
	/**
	 * The currently set font
	 */
	private Font font = Font.defaultFont;
	
	/**
	 * The currently set font size
	 */
	private float fontSize = 18.0f;
	
	/**
	 * The currently set font style
	 */
	private Font.Style fontStyle = Font.Style.PLAIN;
	
	/**
	 * Renders contents to the given graphics instance
	 */
	private void render()
	{
		if(driver == null) {
			throw new IllegalStateException("Renderer component not initialized");
		}
		
		if(layers.size() == 0) {
			throw new IllegalStateException("There must be at least one render layer added to the render stack");
		}
		
		graphics = driver.getGraphics();
		graphics.setRenderingHints(renderingHints);
		
		for(Layer l : layers) {
			l.render(this);
			
			graphics.setClip(null);
		}
		
		driver.display();
	}
	
	/**
	 * Adds a layer to the render stack
	 *
	 * @param l the layer to add
	 */
	public void addLayer(Layer l)
	{
		layers.add(l);
		layers.sort(Layer::compareTo);
	}
	
	/**
	 * Enable or disable antialiasing
	 *
	 * @param enabled whether to enable or disable antialiasing
	 */
	public void setAntialiasingEnabled(boolean enabled)
	{
		renderingHints.put(RenderingHints.KEY_ANTIALIASING, enabled ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	
	@Override
	public void getDependencies(Set<Class<? extends Component>> dependencies)
	{
		dependencies.add(GraphicsDriver.class); // for actual rendering
		dependencies.add(GameLoop.class); // for listening to frame ticks
	}
	
	@Override
	public void initialize(Container container)
	{
		driver = container.require(GraphicsDriver.class);
		container.require(GameLoop.class).events.subscribe(Frame.class, event -> this.render());
	}
	
	@Override
	public void setFillColor(Color color)
	{
		fillColor = color;
	}
	
	@Override
	public void setStrokeColor(Color color)
	{
		strokeColor = color;
	}
	
	@Override
	public void setStrokeWidth(float width)
	{
		strokeWidth = width;
	}
	
	@Override
	public void fill(Color color)
	{
		Vector2i size = driver.getCanvasDimensions();
		graphics.setColor(color.toAWTColor());
		graphics.fillRect(0, 0, size.x, size.y);
	}
	
	@Override
	public void setAnchorMode(AnchorMode mode)
	{
		if(mode != null) anchorMode = mode;
	}
	
	@Override
	public void setTextAnchorMode(TextAnchorMode mode)
	{
		if(mode != null) textAnchorMode = mode;
	}
	
	@Override
	public void setFont(Font font)
	{
		if(font != null) {
			this.font = font;
		}
	}
	
	@Override
	public void setFontSize(float size)
	{
		fontSize = size;
	}
	
	@Override
	public void setFontStyle(Font.Style style)
	{
		fontStyle = style;
	}
	
	@Override
	public void drawLine(float x1, float y1, float x2, float y2)
	{
		if(strokeColor != null) {
			graphics.setColor(strokeColor.toAWTColor());
			graphics.setStroke(new BasicStroke(strokeWidth));
			graphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
		}
	}
	
	@Override
	public void drawRect(float x, float y, float width, float height)
	{
		Vector2f topLeft = anchorMode.getTopLeft(x, y, width, height);
		
		if(fillColor != null) {
			graphics.setColor(fillColor.toAWTColor());
			graphics.fillRect((int) topLeft.x, (int) topLeft.y, (int) width, (int) height);
		}
		
		if(strokeColor != null) {
			graphics.setColor(strokeColor.toAWTColor());
			graphics.setStroke(new BasicStroke(strokeWidth));
			graphics.drawRect((int) topLeft.x, (int) topLeft.y, (int) width, (int) height);
		}
	}
	
	@Override
	public void drawEllipse(float x, float y, float width, float height)
	{
		Vector2f topLeft = anchorMode.getTopLeft(x, y, width, height);
		
		if(fillColor != null) {
			graphics.setColor(fillColor.toAWTColor());
			graphics.fillOval((int) topLeft.x, (int) topLeft.y, (int) width, (int) height);
		}
		
		if(strokeColor != null) {
			graphics.setColor(strokeColor.toAWTColor());
			graphics.setStroke(new BasicStroke(strokeWidth));
			graphics.drawOval((int) topLeft.x, (int) topLeft.y, (int) width, (int) height);
		}
	}
	
	@Override
	public void drawText(float x, float y, String text)
	{
		if(fillColor != null) {
			
			//noinspection MagicConstant
			graphics.setFont(this.font.getAWTFont().deriveFont(fontStyle.getAWTFlags(), fontSize));
			FontMetrics metrics = graphics.getFontMetrics();
			
			Vector2f baseLeft = textAnchorMode.getBaseLeft(x, y, metrics, text);
			
			graphics.setColor(fillColor.toAWTColor());
			graphics.drawString(text, baseLeft.x, baseLeft.y);
		}
	}
}
