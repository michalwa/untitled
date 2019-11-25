package pl.michalwa.untitled.engine.graphics.image;

import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import pl.michalwa.untitled.engine.assets.AssetLoaderException;
import pl.michalwa.untitled.engine.assets.Loader;
import pl.michalwa.untitled.engine.assets.Source;

/**
 * Loader for {@link Image} assets
 */
public class ImageLoader implements Loader<Image>
{
	@Override
	public Image load(List<Source> sources) throws AssetLoaderException
	{
		if(sources.size() != 1) {
			throw new AssetLoaderException("An image asset must have exactly 1 source provided");
		}
		
		try {
			return new Image(ImageIO.read(sources.get(0).open()));
		} catch(IOException e) {
			throw new AssetLoaderException("Could not load image", e);
		}
	}
}
