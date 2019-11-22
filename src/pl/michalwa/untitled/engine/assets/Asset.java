package pl.michalwa.untitled.engine.assets;

/**
 * An asset loaded from a file resource
 */
public abstract class Asset
{
	/**
	 * Whether this asset has been loaded from a resource
	 */
	private final boolean loaded;
	
	/**
	 * Constructs an asset
	 *
	 * @param loaded whether this asset has been loaded from a resource
	 *               or instantiated internally
	 */
	protected Asset(boolean loaded)
	{
		this.loaded = loaded;
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
