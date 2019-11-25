package pl.michalwa.untitled.engine.assets;

/**
 * Thrown when an error is detected in the asset index XML document
 */
public class AssetIndexException extends Exception
{
	public AssetIndexException() {}
	
	public AssetIndexException(String message)
	{
		super(message);
	}
	
	public AssetIndexException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public AssetIndexException(Throwable cause)
	{
		super(cause);
	}
	
	public AssetIndexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
