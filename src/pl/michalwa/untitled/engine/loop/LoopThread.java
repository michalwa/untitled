package pl.michalwa.untitled.engine.loop;

/**
 * A thread that runs an action in a loop
 */
public abstract class LoopThread
{
	/**
	 * Whether the loop is running
	 */
	private boolean running = false;
	
	/**
	 * The thread
	 */
	private final Thread thread;
	
	/**
	 * Constructs a loop thread that will repeat until stopped
	 */
	public LoopThread()
	{
		thread = new Thread(this::run);
	}
	
	/**
	 * Starts the loop if not already started
	 */
	public void start()
	{
		if(!running) {
			running = true;
			thread.start();
		}
	}
	
	/**
	 * Stops the loop if running
	 */
	public void stop()
	{
		if(running) {
			running = false;
			try {
				thread.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method is passed to the super constructor as the thread run target
	 * (this is what will be run on that thread)
	 */
	private void run()
	{
		while(running) loop();
	}
	
	/**
	 * Runs a single iteration of this loop thread
	 */
	protected abstract void loop();
}
