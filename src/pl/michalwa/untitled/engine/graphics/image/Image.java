package pl.michalwa.untitled.engine.graphics.image;

import java.awt.image.BufferedImage;
import pl.michalwa.untitled.engine.assets.Asset;

/**
 * Wrapper for {@link BufferedImage}
 */
public class Image extends Asset
{
	/**
	 * The actual image
	 */
	private BufferedImage image;
	
	/**
	 * Constructs an image asset with the given image
	 *
	 * @param id the ID of the asset
	 * @param image the image
	 */
	Image(String id, BufferedImage image)
	{
		super(id, true);
		this.image = image;
	}
	
	/**
	 * Returns the underlying {@link BufferedImage}
	 *
	 * @return the actual image
	 */
	public BufferedImage getImage()
	{
		return image;
	}
}
