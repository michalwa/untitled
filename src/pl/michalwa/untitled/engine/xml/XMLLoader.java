package pl.michalwa.untitled.engine.xml;

import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import pl.michalwa.untitled.engine.assets.*;

/**
 * Loads {@link XML} assets
 */
public class XMLLoader implements Loader<XML>
{
	/**
	 * Document builder factory
	 */
	private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	@Override
	public Class<XML> getAssetType()
	{
		return XML.class;
	}
	
	@Override
	public XML load(AssetDefinition definition, Assets assets) throws AssetLoaderException
	{
		if(definition.getSources().size() != 1) {
			throw new AssetLoaderException("An XML asset must have exactly 1 source provided");
		}
		
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document = builder.parse(definition.getSources().get(0).open());
			return new XML(definition, document);
		} catch(Exception e) {
			throw new AssetLoaderException("Could not load XML", e);
		}
	}
}
