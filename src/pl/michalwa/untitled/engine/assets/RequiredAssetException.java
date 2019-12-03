package pl.michalwa.untitled.engine.assets;

/**
 * Thrown by {@link Assets} when there is a problem with a required asset
 */
public class RequiredAssetException extends RuntimeException
{
	RequiredAssetException() {}
	
	RequiredAssetException(String message)
	{
		super(message);
	}
	
	RequiredAssetException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	RequiredAssetException(Throwable cause)
	{
		super(cause);
	}
	
	RequiredAssetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
