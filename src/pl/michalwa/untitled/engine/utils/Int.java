package pl.michalwa.untitled.engine.utils;

import java.util.Optional;

/**
 * Utility methods for working with integers
 */
public abstract class Int
{
	/**
	 * Parses the given string into an integer. Allows for a + or -
	 * sign at the beginning of the string. Supports the following notations:
	 *
	 * <table>
	 *     <tr><th>Radix</th><th>Notation</th><th>Prefix</th></tr>
	 *     <tr><td>2<td>Binary</td><td><code>0b</code></td></tr>
	 *     <tr><td>8<td>Octal</td><td><code>0</code></td></tr>
	 *     <tr><td>10<td>Decimal</td><td></td></tr>
	 *     <tr><td>16<td>Hexadecimal</td><td><code>0x</code></td></tr>
	 * </table>
	 *
	 * @param string the string to parse
	 *
	 * @return the parsed integer
	 */
	public static int parse(String string) throws NumberFormatException
	{
		int beginIndex = 0;
		char sign = string.charAt(0);
		String unsigned = string;
		if(sign == '-' || sign == '+') {
			beginIndex = 1;
			unsigned = string.substring(1);
		}
		
		int radix = 10;
		if(unsigned.startsWith("0b")) {
			radix = 2;
			beginIndex += 2;
		} else if(unsigned.startsWith("0x")) {
			radix = 16;
			beginIndex += 2;
		} else if(unsigned.startsWith("0") && unsigned.length() > 1) {
			radix = 8;
			beginIndex += 1;
		}
		
		return Integer.parseInt(string.substring(beginIndex), radix);
	}
	
	/**
	 * Tries to parse the given string into an integer by calling {@link #parse(String)}.
	 * If the call succeeds, an optional containing the parsed integer is returned.
	 * If the call throws a {@link NumberFormatException}, an empty {@link Optional}
	 * is returned.
	 *
	 * @param string the string to parse
	 *
	 * @return the parsed integer
	 */
	public static Optional<Integer> tryParse(String string)
	{
		try {
			return Optional.of(parse(string));
		} catch(NumberFormatException e) {
			return Optional.empty();
		}
	}
}
