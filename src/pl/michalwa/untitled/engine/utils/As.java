package pl.michalwa.untitled.engine.utils;

/**
 * An object that can be converted to the type T.
 *
 * <blockquote>
 * Not much of a useful tool because of type erasure, but still useful in some cases.
 * </blockquote>
 *
 * @param <T> the type of the result of the possible conversion
 */
public interface As<T>
{
	/**
	 * Converts this object to the type T
	 *
	 * @return the converted object
	 */
	T as();
}
