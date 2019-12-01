package pl.michalwa.untitled.engine.window.cursor;

import java.awt.Point;
import java.awt.Toolkit;
import pl.michalwa.untitled.engine.assets.Asset;
import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.graphics.image.Image;

/**
 * Stores a cursor image and properties
 *
 * <p>
 * Cursor assets are loaded from {@code .properties} files which must contain an {@code image} property
 * pointing to the cursor image file (relative to the properties file) and may contain the following properties:
 * </p>
 * <ul>
 *     <li>{@code hotSpotX} - the X coordinate of the pixel of the cursor image that will be centered on the cursor position</li>
 *     <li>{@code hotSpotY} - the Y coordinate of the pixel of the cursor image that will be centered on the cursor position</li>
 * </ul>
 */
public class Cursor extends Asset
{
	/**
	 * Cursor image
	 */
	private final Image image;
	
	/**
	 * Cursor hotspot
	 */
	private final Vector2i hotSpot;
	
	/**
	 * Created AWT cursor
	 */
	private java.awt.Cursor cursor = null;
	
	/**
	 * Constructs a cursor asset
	 *
	 * @param id the ID of the asset
	 * @param image the cursor image
	 * @param hotSpot the coordinates of the pixel to be centered on the mouse position
	 */
	Cursor(String id, Image image, Vector2i hotSpot)
	{
		super(id, true);
		this.image = image;
		this.hotSpot = hotSpot;
	}
	
	/**
	 * Creates a new {@link java.awt.Cursor} instance from this cursor asset or returns an existing
	 * instance, if already created.
	 *
	 * @return the created {@link java.awt.Cursor}
	 */
	public java.awt.Cursor getAWTCursor()
	{
		if(cursor == null) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			cursor = toolkit.createCustomCursor(image.getImage(), new Point(hotSpot.x, hotSpot.y), null);
		}
		return cursor;
	}
}
