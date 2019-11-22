package pl.michalwa.untitled.engine.graphics;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Set;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.window.Window;

/**
 * Controls the graphics displayed on a {@link Canvas}
 */
public class DefaultGraphicsDriver implements GraphicsDriver
{
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
	public void display()
	{
		graphics.dispose();
		graphics = null;
		bs.show();
	}
	
	@Override
	public void getDependencies(Set<Class<? extends Component>> dependencies)
	{
		dependencies.add(Window.class);
	}
	
	@Override
	public void initialize(Container container)
	{
		container.get(Window.class).ifPresent(window -> {
			Canvas canvas = window.getCanvas();
			canvas.createBufferStrategy(3);  // use triple buffering
			bs = canvas.getBufferStrategy();
		});
	}
}
