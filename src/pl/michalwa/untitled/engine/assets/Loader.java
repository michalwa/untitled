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
	 * Loads an asset of the appropriate type from the given input stream
	 *
	 * @param id the id of the asset to load
	 * @param sources the input stream to load the asset from
	 * @param assets the assets component calling this method
	 *
	 * @return the loaded asset
	 */
	T load(String id, List<Source> sources, Assets assets) throws AssetLoaderException;
}
