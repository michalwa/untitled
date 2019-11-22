package pl.michalwa.untitled.engine.graphics;

import java.awt.Graphics2D;
import pl.michalwa.untitled.engine.component.Component;

/**
 * Provides graphics for rendering and a method for displaying
 * the rendered contents to the game window
 */
public interface GraphicsDriver extends Component
{
	/**
	 * Returns the graphics for rendering to the game window
	 *
	 * @return the graphics for rendering
	 */
	Graphics2D getGraphics();
	
	/**
	 * Displays the rendered content to the game window
	 */
	void display();
}
