package pl.michalwa.untitled.engine.assets;

import java.util.List;

/**
 * Responsible for loading a certain type of assets
 *
 * @param <T> the type of assets the loader is able to load
 */
public interface Loader<T extends Asset>
{
	/**
	 * Returns the asset type supported by this loader
	 *
	 * @return the supported asset type
	 */
	Class<T> getAssetType();
	
	/**
	 * Loads an asset of the appropriate type from the given input stream
	 *
	 * @param definition the definition of the asset to load
	 * @param assets the assets component calling this method
	 *
	 * @return the loaded asset
	 */
	T load(AssetDefinition definition, Assets assets) throws AssetLoaderException;
}
