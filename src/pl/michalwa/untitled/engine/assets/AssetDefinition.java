package pl.michalwa.untitled.engine.assets;

import java.util.List;

/**
 * A definition of an asset based on which an asset can be loaded
 */
public class AssetDefinition
{
	/**
	 * The ID of the asset
	 */
	final String id;
	
	/**
	 * The name of the type of the asset
	 */
	final String type;
	
	/**
	 * Sources
	 */
	final List<Source> sources;
	
	/**
	 * Constructs a new asset index entry
	 *
	 * @param id the ID of the asset
	 * @param type the name of the type of the asset
	 * @param sources source definitions
	 */
	public AssetDefinition(String id, String type, List<Source> sources)
	{
		this.id = id;
		this.type = type;
		this.sources = sources;
	}
}
