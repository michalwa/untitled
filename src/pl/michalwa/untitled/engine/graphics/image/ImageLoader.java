package pl.michalwa.untitled.engine.graphics.image;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import pl.michalwa.untitled.engine.assets.AssetLoaderException;
import pl.michalwa.untitled.engine.assets.Loader;

/**
 * Loader for {@link Image} assets
 */
public class ImageLoader implements Loader<Image>
{
	@Override
	public Image load(InputStream is) throws AssetLoaderException
	{
		try {
			return new Image(ImageIO.read(is));
		} catch(IOException e) {
			throw new AssetLoaderException("Could not load image", e);
		}
	}
}
