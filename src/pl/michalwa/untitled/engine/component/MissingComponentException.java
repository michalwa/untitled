package pl.michalwa.untitled.engine.component;

/**
 * Thrown by {@link Container} if a required component is missing
 */
public class MissingComponentException extends Exception
{
	public MissingComponentException() {}
	
	public MissingComponentException(String message)
	{
		super(message);
	}
	
	public MissingComponentException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public MissingComponentException(Throwable cause)
	{
		super(cause);
	}
	
	public MissingComponentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
