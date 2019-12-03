package pl.michalwa.untitled.engine.window.cursor;

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
	public Class<Cursor> getAssetType()
	{
		return Cursor.class;
	}
	
	@Override
	public Cursor load(AssetDefinition definition, Assets assets) throws AssetLoaderException
	{
		List<Source> sources = definition.getSources();
		
		if(sources.size() != 1) {
			throw new AssetLoaderException("A cursor asset must have exactly 1 source provided");
		}
		
		// Load config
		Loader<?> configLoader = assets.getLoader(Config.class.getCanonicalName());
		Asset configAsset = configLoader.load(definition, assets);
		if(configAsset instanceof Config) {
			Config config = (Config) configAsset;
			String imagePath = config.get("image", null);
			
			if(imagePath == null) {
				throw new AssetLoaderException("A cursor must have an `image` property pointing to the cursor image file");
			}
			
			// Load image
			Source imageSource = sources.get(0).relative(imagePath);
			Asset imageAsset = assets.load(new AssetDefinition(
				definition.getId() + ".image",
				Image.class.getCanonicalName(),
				Collections.singletonList(imageSource)));
			
			if(imageAsset instanceof Image) {
			
				// Construct cursor
				return new Cursor(definition, (Image) imageAsset, new Vector2i(
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
