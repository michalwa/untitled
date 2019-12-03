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
	private final String id;
	
	/**
	 * The name of the type of the asset
	 */
	private final String typename;
	
	/**
	 * Sources
	 */
	private  List<Source> sources;
	
	/**
	 * Constructs a new asset index entry
	 *
	 * @param id the ID of the asset
	 * @param typename the name of the type of the asset - the simple or canonical asset class name
	 * @param sources source definitions
	 */
	public AssetDefinition(String id, String typename, List<Source> sources)
	{
		this.id = id;
		this.typename = typename;
		this.sources = sources;
	}
	
	/**
	 * Returns the ID of the asset
	 *
	 * @return the ID of the asset
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * Returns the name of the type of the asset
	 *
	 * @return the name of the type of the asset
	 */
	public String getTypename()
	{
		return typename;
	}
	
	/**
	 * Returns the list of source files from which the asset was loaded
	 *
	 * @return the list of sources for the asset
	 */
	public List<Source> getSources()
	{
		return sources;
	}
}
