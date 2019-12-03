package pl.michalwa.untitled.engine.graphics;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Set;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.window.Window;

/**
 * Controls the graphics displayed on a {@link Canvas} with no processing
 */
public class DefaultGraphicsDriver implements GraphicsDriver
{
	/**
	 * The window component
	 */
	private Window window = null;
	
	/**
	 * The buffer strategy
	 */
	private BufferStrategy bs = null;
	
	/**
	 * The current active graphics
	 */
	private Graphics2D graphics;
	
	@Override
	public Graphics2D getGraphics()
	{
		if(bs == null) {
			throw new IllegalStateException("Graphics driver not initialized");
		}
		if(graphics == null) {
			graphics = (Graphics2D) bs.getDrawGraphics();
		}
		return graphics;
	}
	
	@Override
	public Vector2i getCanvasDimensions()
	{
		if(window == null) {
			throw new IllegalStateException("Graphics driver not initialized");
		}
		return window.getSize();
	}
	
	@Override
	public void display()
	{
		graphics.dispose();
		graphics = null;
		bs.show();
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName();
	}
	
	@Override
	public void getDependencies(Set<Class<? extends Component>> dependencies)
	{
		dependencies.add(Window.class);
	}
	
	@Override
	public void initialize(Container container)
	{
		window = container.require(Window.class);
		Canvas canvas = window.getCanvas();
		canvas.createBufferStrategy(3);  // use triple buffering
		bs = canvas.getBufferStrategy();
	}
}
