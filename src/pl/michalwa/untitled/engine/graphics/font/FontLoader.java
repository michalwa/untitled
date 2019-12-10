package pl.michalwa.untitled.engine.graphics.font;

import pl.michalwa.untitled.engine.assets.AssetDefinition;
import pl.michalwa.untitled.engine.assets.AssetLoaderException;
import pl.michalwa.untitled.engine.assets.Assets;
import pl.michalwa.untitled.engine.assets.Loader;

/**
 * Loads {@link Font} assets. Only truetype font files are supported.
 */
public class FontLoader implements Loader<Font>
{
	@Override
	public Class<Font> getAssetType()
	{
		return Font.class;
	}
	
	@Override
	public Font load(AssetDefinition definition, Assets assets) throws AssetLoaderException
	{
		if(definition.getSources().size() != 1) {
			throw new AssetLoaderException("A font asset must have exactly 1 source provided");
		}
		
		try {
			java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, definition.getSources().get(0).open());
			return new Font(definition, font);
		} catch(Exception e) {
			throw new AssetLoaderException("Could not load font", e);
		}
	}
}
