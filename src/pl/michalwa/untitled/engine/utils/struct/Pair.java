package pl.michalwa.untitled.engine.utils.struct;

import java.util.Objects;

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
	public final T1 first;
	
	/**
	 * The second value of the pair
	 */
	public final T2 second;
	
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
	
	@Override
	public String toString()
	{
		return "(" + first + ", " + second + ")";
	}
	
	/**
	 * Returns {@code true} if {@code super.equals(obj)} returns {@code true}
	 * or the given object is a {@link Pair} and its values compared to the values
	 * of this pair with {@link Objects#equals(Object, Object)} return {@code true}
	 *
	 * @param obj the object to compare
	 *
	 * @return whether the objects are equal
	 */
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj)
			
			|| (obj instanceof Pair
			&& Objects.equals(((Pair<?, ?>) obj).first, first)
			&& Objects.equals(((Pair<?, ?>) obj).second, second));
	}
}
