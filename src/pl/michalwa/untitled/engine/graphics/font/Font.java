package pl.michalwa.untitled.engine.graphics.font;

import pl.michalwa.untitled.engine.assets.Asset;
import pl.michalwa.untitled.engine.assets.AssetDefinition;

/**
 * A font asset
 */
public class Font extends Asset
{
	/**
	 * The default font
	 */
	public static final Font defaultFont = new Font(null, java.awt.Font.decode(null));
	
	/**
	 * Font style
	 */
	public enum Style
	{
		PLAIN       (java.awt.Font.PLAIN),
		ITALIC      (java.awt.Font.ITALIC),
		BOLD        (java.awt.Font.BOLD),
		BOLD_ITALIC (java.awt.Font.BOLD | java.awt.Font.ITALIC);
		
		/**
		 * The AWT font style flags
		 */
		private final int flags;
		
		Style(int flags)
		{
			this.flags = flags;
		}
		
		/**
		 * Returns the AWT font style flags for this style
		 *
		 * @return the AWT font style flags
		 */
		public int getAWTFlags()
		{
			return flags;
		}
	}
	
	/**
	 * The actual font
	 */
	private final java.awt.Font font;
	
	/**
	 * Constructs an asset
	 *
	 * @param definition the asset definition
	 * @param font the actual AWT font
	 */
	Font(AssetDefinition definition, java.awt.Font font)
	{
		super(definition);
		this.font = font;
	}
	
	/**
	 * Returns the actual AWT font
	 *
	 * @return the AWT font
	 */
	public java.awt.Font getAWTFont()
	{
		return font;
	}
}
