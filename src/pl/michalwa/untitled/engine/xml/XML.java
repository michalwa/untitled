package pl.michalwa.untitled.engine.xml;

import org.w3c.dom.Document;
import pl.michalwa.untitled.engine.assets.Asset;
import pl.michalwa.untitled.engine.assets.AssetDefinition;

/**
 * ML document wrapper
 */
public class XML extends Asset
{
	/**
	 * The encapsulated XML document
	 */
	private Document document;
	
	/**
	 * Constructs a new XML document wrapper
	 *
	 * @param assetDefinition the asset definition
	 * @param document the XML document
	 */
	XML(AssetDefinition assetDefinition, Document document)
	{
		super(assetDefinition);
		this.document = document;
	}
	
	/**
	 * Returns the encapsulated XML document
	 *
	 * @return the encapsulated XML document
	 */
	public Document getDocument()
	{
		return document;
	}
}
