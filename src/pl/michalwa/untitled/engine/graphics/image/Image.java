package pl.michalwa.untitled.engine.graphics.image;

import java.awt.image.BufferedImage;
import pl.michalwa.untitled.engine.assets.Asset;
import pl.michalwa.untitled.engine.assets.AssetDefinition;

/**
 * Wrapper for {@link BufferedImage}
 */
public class Image extends Asset
{
	/**
	 * The actual image
	 */
	private final BufferedImage image;
	
	/**
	 * Constructs an image asset with the given image
	 *
	 * @param definition the asset definition
	 * @param image the image
	 */
	Image(AssetDefinition definition, BufferedImage image)
	{
		super(definition);
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
