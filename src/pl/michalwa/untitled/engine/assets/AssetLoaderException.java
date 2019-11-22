package pl.michalwa.untitled.engine.assets;

/**
 * Thrown when an asset loader encounters a problem while loading an asset
 */
public class AssetLoaderException extends Exception
{
	public AssetLoaderException() {}
	
	public AssetLoaderException(String message)
	{
		super(message);
	}
	
	public AssetLoaderException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public AssetLoaderException(Throwable cause)
	{
		super(cause);
	}
	
	public AssetLoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
