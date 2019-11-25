package pl.michalwa.untitled.engine.window;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Optional;
import java.util.Set;
import javax.swing.JFrame;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.events.Event;
import pl.michalwa.untitled.engine.events.EventDispatcher;
import pl.michalwa.untitled.engine.graphics.DefaultGraphicsDriver;
import pl.michalwa.untitled.engine.graphics.image.Image;
import pl.michalwa.untitled.engine.runtime.Application;
import pl.michalwa.untitled.engine.window.events.WindowClosingEvent;
import pl.michalwa.untitled.engine.window.events.WindowFocusEvent;

/**
 * Controls the game window
 */
public class Window extends EventDispatcher implements Component, WindowListener
{
	/**
	 * The application component or null
	 */
	private Application app;
	
	/**
	 * The actual window driver
	 */
	private final JFrame jframe;
	
	/**
	 * The canvas where the game contents are drawn
	 */
	private final Canvas canvas;
	
	/**
	 * The graphics driver for the canvas
	 */
	private DefaultGraphicsDriver graphicsDriver = null;
	
	/**
	 * Constructs and initializes the game window
	 */
	public Window()
	{
		jframe = new JFrame();
		jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jframe.addWindowListener(this);
		jframe.setResizable(false);
		
		canvas = new Canvas();
		jframe.add(canvas);
		
		// Set a default size so the window doesn't get fucked up
		setSize(640, 480);
		setTitle(null);
	}
	
	/**
	 * Returns the canvas where the game contents are displayed
	 *
	 * @return the game canvas
	 */
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	/**
	 * Sets the window title
	 *
	 * @param title the new window title
	 */
	public void setTitle(String title)
	{
		jframe.setTitle(title);
	}
	
	/**
	 * If {@code fullscreen} is {@code true}, sets the window dimensions to fit the screen
	 * and sets the window mode to undecorated (without border and title bar).
	 * If {@code fullscreen} is {@code false}, sets the window to decorated mode.
	 *
	 * @param fullscreen whether to set the window to fullscreen mode
	 */
	public void setFullscreen(boolean fullscreen)
	{
		if(fullscreen) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setSize(screenSize.width, screenSize.height);
			jframe.setUndecorated(true);
		} else {
			jframe.setUndecorated(false);
		}
	}
	
	/**
	 * Sets the size of the canvas where the game contents are drawn
	 *
	 * @param width the new width of the game canvas
	 * @param height the new height of the game canvas
	 */
	public void setSize(int width, int height)
	{
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		jframe.pack();
	}
	
	/**
	 * Returns the current size of the canvas
	 *
	 * @return current canvas size
	 */
	public Dimension getSize()
	{
		return canvas.getSize();
	}
	
	/**
	 * Sets or removes the window icon
	 *
	 * @param icon the new window icon or {@code null} for the default Java icon
	 */
	public void setIcon(Image icon)
	{
		jframe.setIconImage(Optional.ofNullable(icon).map(Image::getImage).orElse(null));
	}
	
	/**
	 * Shows or hides the game window depending on the given value
	 *
	 * @param visible whether to show or hide the window
	 */
	public void setVisible(boolean visible)
	{
		if(visible) {
			jframe.setLocationRelativeTo(null);
		}
		jframe.setVisible(visible);
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		Event event = new WindowClosingEvent();
		dispatch(event);
		
		// Exist the entire application if event not consumed
		if(!event.isConsumed()) {
			if(app != null) {
				app.quit();
			} else {
				System.exit(0);
			}
		}
	}
	
	@Override
	public void getDependencies(Set<Class<? extends Component>> dependencies) {}
	
	@Override
	public void initialize(Container container)
	{
		app = container.get(Application.class).orElse(null);
	}
	
	@Override
	public void windowActivated(WindowEvent e)
	{
		dispatch(new WindowFocusEvent(true));
	}
	
	@Override
	public void windowDeactivated(WindowEvent e)
	{
		dispatch(new WindowFocusEvent(false));
	}
	
	@Override
	public void windowIconified(WindowEvent e) {}
	
	@Override
	public void windowDeiconified(WindowEvent e) {}
	
	@Override
	public void windowOpened(WindowEvent e) {}
	
	@Override
	public void windowClosed(WindowEvent e) {}
}
