package pl.michalwa.untitled.engine.graphics;

import java.util.Locale;

/**
 * An RGBA float32 color
 */
public class Color
{
	public static Color BLACK   = new Color(0.0f, 0.0f, 0.0f);
	public static Color BLUE    = new Color(0.0f, 0.0f, 1.0f);
	public static Color GREEN   = new Color(0.0f, 1.0f, 0.0f);
	public static Color CYAN    = new Color(0.0f, 1.0f, 1.0f);
	public static Color RED     = new Color(1.0f, 0.0f, 0.0f);
	public static Color MAGENTA = new Color(1.0f, 0.0f, 1.0f);
	public static Color YELLOW  = new Color(1.0f, 1.0f, 0.0f);
	public static Color WHITE   = new Color(1.0f, 1.0f, 1.0f);
	public static Color TRANSPARENT = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	
	/**
	 * The red component (0.0 - 1.0)
	 */
	private final float red;
	
	/**
	 * The green component (0.0 - 1.0)
	 */
	private final float green;
	
	/**
	 * The blue component (0.0 - 1.0)
	 */
	private final float blue;
	
	/**
	 * The alpha component (0.0 - 1.0)
	 */
	private final float alpha;
	
	/**
	 * Constructs an RGBA color with the specified component values
	 */
	public Color(float red, float green, float blue, float alpha)
	{
		this.red   = red;
		this.green = green;
		this.blue  = blue;
		this.alpha = alpha;
	}
	
	/**
	 * Constructs an RGBA color with full opacity (alpha = 1.0) and the specified RGB values
	 */
	public Color(float red, float green, float blue)
	{
		this(red, green, blue, 1.0f);
	}
	
	/**
	 * Parses color HEX/CSS notation
	 *
	 * @param hex the hex string to parse
	 */
	public Color(String hex)
	{
		alpha = 1.0f;
		
		String r, g, b;
		
		hex = hex.toLowerCase(Locale.ROOT);
		if(hex.startsWith("#")) hex = hex.substring(1);
		if(hex.length() == 3) {
			r = "" + hex.charAt(0) + hex.charAt(0);
			g = "" + hex.charAt(1) + hex.charAt(1);
			b = "" + hex.charAt(2) + hex.charAt(2);
		} else if(hex.length() == 6) {
			r = hex.substring(0, 2);
			g = hex.substring(2, 4);
			b = hex.substring(4, 6);
		} else {
			throw new IllegalArgumentException("Invalid color code: " + hex);
		}
		
		try {
			red   = (float) Integer.parseInt(r, 16) / 255.0f;
			green = (float) Integer.parseInt(g, 16) / 255.0f;
			blue  = (float) Integer.parseInt(b, 16) / 255.0f;
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Invalid color code: " + hex, e);
		}
	}
	
	/**
	 * Returns this color as a {@link java.awt.Color} instance
	 *
	 * @return this color as a {@link java.awt.Color}
	 */
	public java.awt.Color toAWTColor()
	{
		return new java.awt.Color(red, green, blue, alpha);
	}
	
	@Override
	public String toString()
	{
		return "RGBA(" + red + ", " + green + ", " + blue + ", " + alpha + ")";
	}
}
