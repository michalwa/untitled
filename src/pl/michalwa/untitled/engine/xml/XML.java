package pl.michalwa.untitled.engine.xml;

import org.w3c.dom.Document;
import pl.michalwa.untitled.engine.assets.Asset;

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
	 * @param id the ID of the asset
	 * @param document the XML document
	 */
	XML(String id, Document document)
	{
		super(id, true);
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
