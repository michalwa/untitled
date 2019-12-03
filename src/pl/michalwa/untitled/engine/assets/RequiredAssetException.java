package pl.michalwa.untitled.engine.assets;

/**
 * Thrown by {@link Assets} when there is a problem with a required asset
 */
public class RequiredAssetException extends RuntimeException
{
	public RequiredAssetException() {}
	
	public RequiredAssetException(String message)
	{
		super(message);
	}
	
	public RequiredAssetException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public RequiredAssetException(Throwable cause)
	{
		super(cause);
	}
	
	public RequiredAssetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
