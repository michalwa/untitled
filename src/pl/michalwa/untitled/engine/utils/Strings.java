package pl.michalwa.untitled.engine.utils;

/**
 * Utility methods for working with strings
 */
public abstract class Strings
{
	/**
	 * If {@code string.startsWith(prefix)} return {@code true}, returns {@code string}
	 * unchanged. Otherwise, returns {@code prefix + string}.
	 *
	 * @param prefix the prefix to ensure exists at the beginning of the string
	 * @param string the string to prefix
	 *
	 * @return the prefixed string or {@code null} if any of the arguments are {@code null}
	 */
	public static String ensureStartsWith(String prefix, String string)
	{
		if(prefix == null || string == null) return string;
		return string.startsWith(prefix) ? string : (prefix + string);
	}
	
	/**
	 * If {@code string.endsWith(suffix)} return {@code true}, returns {@code string}
	 * unchanged. Otherwise, returns {@code string + suffix}.
	 *
	 * @param suffix the suffix to ensure exists at the end of the string
	 * @param string the string to suffix
	 *
	 * @return the suffixed string or {@code null} if any of the arguments are {@code null}
	 */
	public static String ensureEndsWith(String suffix, String string)
	{
		if(suffix == null || string == null) return string;
		return string.endsWith(suffix) ? string : (string + suffix);
	}
}
