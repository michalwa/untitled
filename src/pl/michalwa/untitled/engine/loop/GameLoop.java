package pl.michalwa.untitled.engine.loop;

import java.util.Set;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.loop.events.EverySecond;
import pl.michalwa.untitled.engine.loop.events.Frame;
import pl.michalwa.untitled.engine.loop.events.Tick;
import pl.michalwa.untitled.engine.events.EventDispatcher;

/**
 * Responsible for running the game in a loop
 */
public class GameLoop extends LoopThread implements Component
{
	/**
	 * The event dispatcher
	 */
	public final EventDispatcher events;
	
	/**
	 * The target number of ticks per second the game loop will try to maintain
	 */
	private int targetTPS = 60;
	
	/**
	 * The target number of frames per second the game loop will try to maintain.
	 * If less than or equal to 0, frames per second will not be limited
	 */
	private int targetFPS = 0;
	
	/**
	 * The last time {@link #loop()} was run in nanoseconds
	 */
	private long lastLoop;
	
	/**
	 * Accumulates {@code loop iteration time / full tick time} for each {@link #loop()} iteration
	 */
	private double tickDelta = 0.0;
	
	/**
	 * Accumulates {@code loop iteration time / full frame time} for each {@link #loop()} iteration
	 */
	private double frameDelta = 0.0;
	
	/**
	 * Accumulates {@code loop iteration time / 1 second} for each {@link #loop()} iteration
	 */
	private double secondDelta = 0.0;
	
	/**
	 * Stores the accumulated delta time for the last tick
	 */
	private double deltaTime = 0.0;
	
	/**
	 * Stores the number of ticks executed during the current second
	 */
	private int ticks = 0;
	
	/**
	 * Stores the number of ticks executed during the last second
	 */
	private int tps = 0;
	
	/**
	 * Stores the number of frames rendered during the current second
	 */
	private int frames = 0;
	
	/**
	 * Stores the number of frames rendered during the last second
	 */
	private int fps = 0;
	
	/**
	 * Constructs a new game loop
	 */
	public GameLoop()
	{
		events = new EventDispatcher();
	}
	
	@Override
	public void start()
	{
		lastLoop = System.nanoTime();
		super.start();
	}
	
	@Override
	protected void loop()
	{
		long now = System.nanoTime();
		
		// fullTickNano = 1,000,000,000 / targetTPS
		// delta = (now - lastLoop) / fullTickNano
		tickDelta += (double) (now - lastLoop) / 1000000000.0 * (double) targetTPS;
		secondDelta += (double) (now - lastLoop) / 1000000000.0;
		
		// Tick
		if(tickDelta >= 1.0) {
			deltaTime = tickDelta;
			tickDelta -= 1.0;
			tick();
			ticks++;
		}
		
		// Frame
		if(targetFPS > 0) {
			frameDelta += (double) (now - lastLoop) / 1000000000.0 * (double) targetFPS;
			if(frameDelta >= 1.0) {
				frameDelta -= 1.0;
				frame();
				frames++;
			}
		} else {
			frame();
			frames++;
		}
		
		// 1-second timer for counting TPS and FPS
		if(secondDelta >= 1.0) {
			secondDelta -= 1.0;
			
			tps = ticks;
			ticks = 0;
			fps = frames;
			frames = 0;
			
			events.dispatch(new EverySecond());
		}
		
		lastLoop = now;
	}
	
	/**
	 * Executed for each tick
	 */
	private void tick()
	{
		events.dispatch(new Tick());
	}
	
	/**
	 * Executed for each frame
	 */
	private void frame()
	{
		events.dispatch(new Frame());
	}
	
	/**
	 * Sets the target number of ticks per second the game loop will try to maintain
	 *
	 * @param tps the new target TPS
	 */
	public void setTargetTPS(int tps)
	{
		targetTPS = tps;
	}
	
	/**
	 * Sets the target number of frames per second the game loop will try to maintain.
	 * If the given value is less than or equal to 0, frames per second will not be limited.
	 *
	 * @param fps the new target FPS
	 */
	public void setTargetFPS(int fps)
	{
		targetFPS = fps;
	}
	
	/**
	 * Returns the delta-time value for the last tick.
	 *
	 * <p>
	 * Delta time indicates how long the last tick took to execute in proportion to the expected time for one tick to execute.
	 * Delta time will be less than 1 if a new tick started too early, equal to 1 for ticks that took exactly the expected
	 * time to execute and greater than 1 if the tick took longer than expected.
	 * </p>
	 * <p>
	 * This value can be used to ensure uniform distribution of certain processes over time, e.g. physics simulations
	 * by compensating irregularities in the tick interval.
	 * </p>
	 *
	 * @return delta time for the last tick
	 */
	public double getDeltaTime()
	{
		return deltaTime;
	}
	
	/**
	 * Returns the number of ticks executed during the last second.
	 * This can be used to monitor performance
	 *
	 * @return last TPS value
	 */
	public int getTPS()
	{
		return tps;
	}
	
	/**
	 * Returns the number of frames rendered during the last second.
	 * This can be used to monitor performance
	 *
	 * @return last FPS value
	 */
	public int getFPS()
	{
		return fps;
	}
	
	@Override
	public void getDependencies(Set<Class<? extends Component>> dependencies) {}
	
	@Override
	public void initialize(Container container) {}
}
