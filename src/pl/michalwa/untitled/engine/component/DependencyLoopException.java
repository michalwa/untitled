package pl.michalwa.untitled.engine.component;

/**
 * Thrown when components in a container create a dependency loop
 */
public class DependencyLoopException extends Exception
{
	public DependencyLoopException() {}
	
	public DependencyLoopException(String message)
	{
		super(message);
	}
	
	public DependencyLoopException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public DependencyLoopException(Throwable cause)
	{
		super(cause);
	}
	
	public DependencyLoopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
