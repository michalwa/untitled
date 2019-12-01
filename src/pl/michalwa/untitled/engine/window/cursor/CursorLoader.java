package pl.michalwa.untitled.engine.window.cursor;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import pl.michalwa.untitled.engine.assets.*;
import pl.michalwa.untitled.engine.config.Config;
import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.graphics.image.Image;

/**
 * Loads {@link Cursor} assets
 */
public class CursorLoader implements Loader<Cursor>
{
	@Override
	public Cursor load(String id, List<Source> sources, Assets assets) throws AssetLoaderException
	{
		if(sources.size() != 1) {
			throw new AssetLoaderException("A cursor asset must have exactly 1 source provided");
		}
		
		// Load config
		Loader<?> configLoader = assets.getLoader("config");
		Asset configAsset = configLoader.load(null, sources, assets);
		if(configAsset instanceof Config) {
			Config config = (Config) configAsset;
			String imagePath = config.get("image", null);
			
			if(imagePath == null) {
				throw new AssetLoaderException("A cursor must have an `image` property pointing to the cursor image file");
			}
			
			// Resolve image path relative to config file directory
			imagePath = Paths.get(sources.get(0).getPath()).resolveSibling(imagePath).toString();
			
			// Load image
			Asset imageAsset = assets.load(new AssetIndexEntry(id + ".image", "image", Collections.singletonList(imagePath)));
			if(imageAsset instanceof Image) {
			
				// Construct cursor
				return new Cursor(id, (Image) imageAsset, new Vector2i(
					config.getInt("hotSpotX", 0),
					config.getInt("hotSpotY", 0)
				));
			
			} else {
				throw new AssetLoaderException("Inappropriate loader registered for type: image");
			}
		} else {
			throw new AssetLoaderException("Inappropriate loader registered for type: config");
		}
	}
}
