package pl.michalwa.untitled.engine.graphics.image;

import java.io.IOException;
import javax.imageio.ImageIO;
import pl.michalwa.untitled.engine.assets.AssetDefinition;
import pl.michalwa.untitled.engine.assets.AssetLoaderException;
import pl.michalwa.untitled.engine.assets.Assets;
import pl.michalwa.untitled.engine.assets.Loader;

/**
 * Loads {@link Image} assets
 */
public class ImageLoader implements Loader<Image>
{
	@Override
	public Class<Image> getAssetType()
	{
		return Image.class;
	}
	
	@Override
	public Image load(AssetDefinition definition, Assets assets) throws AssetLoaderException
	{
		if(definition.getSources().size() != 1) {
			throw new AssetLoaderException("An image asset must have exactly 1 source provided");
		}
		
		try {
			return new Image(definition, ImageIO.read(definition.getSources().get(0).open()));
		} catch(IOException e) {
			throw new AssetLoaderException("Could not load image", e);
		}
	}
}
