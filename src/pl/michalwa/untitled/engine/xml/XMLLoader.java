package pl.michalwa.untitled.engine.xml;

import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import pl.michalwa.untitled.engine.assets.AssetLoaderException;
import pl.michalwa.untitled.engine.assets.Loader;
import pl.michalwa.untitled.engine.assets.Source;

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
	public XML load(List<Source> sources) throws AssetLoaderException
	{
		if(sources.size() != 1) {
			throw new AssetLoaderException("An XML asset must have exactly 1 source provided");
		}
		
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document = builder.parse(sources.get(0).open());
			return new XML(document);
		} catch(Exception e) {
			throw new AssetLoaderException("Could not load XML", e);
		}
	}
}
