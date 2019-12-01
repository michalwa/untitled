package pl.michalwa.untitled.engine.assets;

/**
 * An asset loaded from a file resource
 */
public abstract class Asset
{
	/**
	 * The ID of this asset ({@code null} if not set or not relevant)
	 */
	private final String id;
	
	/**
	 * Whether this asset has been loaded from a resource
	 */
	private final boolean loaded;
	
	/**
	 * Constructs an asset
	 *
	 * @param id the ID of this asset (can be {@code null} if the asset was not loaded or the ID is not relevant)
	 * @param loaded whether this asset has been loaded from a resource or instantiated internally
	 */
	protected Asset(String id, boolean loaded)
	{
		this.id = id;
		this.loaded = loaded;
	}
	
	/**
	 * Returns the ID of this asset
	 *
	 * @return the ID of this asset
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * Returns {@code true}, if this asset has been loaded from a resource
	 * or {@code false} if it has been instantiated internally during runtime.
	 *
	 * @return whether this asset is loaded from a resource
	 */
	public boolean isLoaded()
	{
		return loaded;
	}
}
