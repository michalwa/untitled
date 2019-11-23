package pl.michalwa.untitled.engine.utils.struct;

/**
 * A 2-value tuple
 *
 * @param <T1> the type of the first value
 * @param <T2> the type of the second value
 */
public class Pair<T1, T2>
{
	/**
	 * The first value of the pair
	 */
	private final T1 first;
	
	/**
	 * The second value of the pair
	 */
	private final T2 second;
	
	/**
	 * Constructs a new pair from the given values
	 *
	 * @param first the first value of the pair
	 * @param second the second value of the pair
	 */
	public Pair(T1 first, T2 second)
	{
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Returns the first value of the pair
	 *
	 * @return the first value of the pair
	 */
	public T1 getFirst()
	{
		return first;
	}
	
	/**
	 * Returns the second value of the pair
	 *
	 * @return the second value of the pair
	 */
	public T2 getSecond()
	{
		return second;
	}
	
	@Override
	public String toString()
	{
		return "(" + first + ", " + second + ")";
	}
}
