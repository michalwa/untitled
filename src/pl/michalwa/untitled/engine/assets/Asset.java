package pl.michalwa.untitled.engine.assets;

/**
 * An asset loaded from a file resource
 */
public abstract class Asset
{
	/**
	 * The definition from which this asset was loaded. {@code null} means the asset was not loaded
	 * from a source file but rather created in memory during runtime
	 */
	private final AssetDefinition definition;
	
	/**
	 * Constructs an asset
	 *
	 * @param definition the definition from which this asset was loaded.
	 *  {@code null} means the asset was not loaded
	 *  from a source file but rather created in memory during runtime
	 */
	protected Asset(AssetDefinition definition)
	{
		this.definition = definition;
	}
	
	/**
	 * Returns the definition from which this asset was loaded or {@code null}
	 * if the asset was not loaded from a source file but rather created in memory during runtime
	 */
	public AssetDefinition getDefinition()
	{
		return definition;
	}
}
