package pl.michalwa.untitled.engine.component;

/**
 * Thrown by components upon initialization
 */
public class ComponentInitializationException extends Exception
{
	public ComponentInitializationException() {}
	
	public ComponentInitializationException(String message)
	{
		super(message);
	}
	
	public ComponentInitializationException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public ComponentInitializationException(Throwable cause)
	{
		super(cause);
	}
	
	public ComponentInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
