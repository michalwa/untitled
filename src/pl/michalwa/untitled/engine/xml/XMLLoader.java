package pl.michalwa.untitled.engine.xml;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import pl.michalwa.untitled.engine.assets.AssetLoaderException;
import pl.michalwa.untitled.engine.assets.Loader;

/**
 * Loader for {@link XML} assets
 */
public class XMLLoader implements Loader<XML>
{
	/**
	 * Document builder factory
	 */
	private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	@Override
	public XML load(InputStream is) throws AssetLoaderException
	{
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			return new XML(builder.parse(is));
		} catch(Exception e) {
			throw new AssetLoaderException("Could not load XML", e);
		}
	}
}
