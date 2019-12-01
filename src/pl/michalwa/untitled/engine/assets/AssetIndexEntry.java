package pl.michalwa.untitled.engine.assets;

import java.util.List;

/**
 * An entry parsed from the asset index
 */
public class AssetIndexEntry
{
	/**
	 * The ID of the asset
	 */
	final String id;
	
	/**
	 * The type of the asset
	 */
	final String type;
	
	/**
	 * Source paths
	 */
	final List<String> sources;
	
	/**
	 * Constructs a new asset index entry
	 *
	 * @param id the ID of the asset
	 * @param type the type of the asset
	 * @param sources source definitions
	 */
	public AssetIndexEntry(String id, String type, List<String> sources)
	{
		this.id = id;
		this.type = type;
		this.sources = sources;
	}
}
