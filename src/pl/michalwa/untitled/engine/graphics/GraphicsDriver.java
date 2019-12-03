package pl.michalwa.untitled.engine.graphics;

import java.awt.Graphics2D;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.geom.Vector2i;

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
	 * Returns the dimensions of the drawing canvas represented as a {@link Vector2i}
	 * where the X coordinate represents the width and the Y coordinate represents the height
	 *
	 * @return the dimensions of the drawing canvas as a {@link Vector2i}
	 */
	Vector2i getCanvasDimensions();
	
	/**
	 * Displays the rendered content to the game window
	 */
	void display();
}
