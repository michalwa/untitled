package pl.michalwa.untitled.engine.utils.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Utility extensions to the Stream API
 */
public abstract class StreamUtils
{
	/**
	 * Returns a predicate that can be used with {@link java.util.stream.Stream#filter(Predicate)}
	 * to obtain a collection of elements where the value of a specific field of each element
	 * is distinct
	 *
	 * @param keyExtractor a function that recieves an element of the stream and
	 *                        returns the value of the field to be treated as the key
	 * @param <T> the type of elements the predicate will accept
	 *
	 * @return the constructed predicate
	 */
	public static <T> Predicate<T> distinctByKey(
		Function<? super T, ?> keyExtractor) {
		
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	/**
	 * Flattens the given stream of streams into a stream of elements of the type
	 * of the deeper streams.
	 *
	 * <p>
	 * Equivalent of calling {@code nestedStream.flatMap(Function.identity())}
	 * </p>
	 *
	 * @param nestedStream the nested stream
	 * @param <T> the element type of the deeper streams
	 *
	 * @return the flattened stream
	 */
	public static <T> Stream<T> flatten(Stream<Stream<T>> nestedStream)
	{
		return nestedStream.flatMap(Function.identity());
	}
}
