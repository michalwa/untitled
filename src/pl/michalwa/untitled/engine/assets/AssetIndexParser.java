package pl.michalwa.untitled.engine.assets;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import pl.michalwa.untitled.engine.utils.collections.StreamUtils;

/**
 * Parses the asset index file
 */
public class AssetIndexParser
{
	/**
	 * The root asset index XML tag name
	 */
	private static final String ROOT_TAG_NAME = "assets";
	
	/**
	 * The name of the tag for defining an asset in the asset index
	 */
	private static final String ASSET_TAG_NAME = "asset";
	
	/**
	 * The name of the asset id attribute
	 */
	private static final String ASSET_ID_ATTRIBUTE = "id";
	
	/**
	 * The name of the asset type attribute
	 */
	private static final String ASSET_TYPE_ATTRIBUTE = "type";
	
	/**
	 * The name of the asset source attribute
	 */
	private static final String ASSET_SOURCE_ATTRIBUTE = "src";
	
	/**
	 * Parses the asset index and returns all defined assets
	 *
	 * @param index the asset index XML document
	 */
	public List<Entry> parse(Document index) throws AssetLoaderException
	{
		List<Entry> entries = new ArrayList<>();
		
		// Get root element
		Element root = index.getDocumentElement();
		if(!root.getTagName().equals(ROOT_TAG_NAME)) {
			throw new AssetLoaderException("Asset index must have <" + ROOT_TAG_NAME + "> as root");
		}
		
		// Iterate through children
		Node childNode = root.getFirstChild();
		while(childNode != null) {
			if(childNode.getNodeType() != Node.ELEMENT_NODE) {
				childNode = childNode.getNextSibling();
				continue;
			}
			Element child = (Element) childNode;
			if(!child.getTagName().equals(ASSET_TAG_NAME)) {
				childNode = childNode.getNextSibling();
				continue;
			}
			
			// Check for required attributes
			for(String attr : new String[] {
				ASSET_ID_ATTRIBUTE,
				ASSET_TYPE_ATTRIBUTE,
				ASSET_SOURCE_ATTRIBUTE
			}) {
				if(!child.hasAttribute(attr)) {
					throw new AssetLoaderException(
						"<" + ASSET_TAG_NAME + "> tags must have an `" + attr + "` attribute");
				}
			}
			
			// Parse entry
			Entry entry = new Entry(
				child.getAttribute(ASSET_ID_ATTRIBUTE),
				child.getAttribute(ASSET_TYPE_ATTRIBUTE),
				child.getAttribute(ASSET_SOURCE_ATTRIBUTE)
			);
			entries.add(entry);
			
			childNode = childNode.getNextSibling();
		}
		
		// Asset definitions with repeated IDs will be ignored
		return entries.stream()
			.filter(StreamUtils.distinctByKey(e -> e.id))
			.collect(Collectors.toList());
	}
	
	/**
	 * An entry parsed from the asset index
	 */
	static class Entry
	{
		/**
		 * The ID of the asset
		 */
		final String id;
		
		/**
		 * The type of the asset
		 */
		final String type;
		
		/**
		 * The source file name
		 */
		final String source;
		
		/**
		 * Constructs a new asset index entry
		 *
		 * @param id the ID of the asset
		 * @param type the type of the asset
		 * @param source the source file name
		 */
		public Entry(String id, String type, String source)
		{
			this.id = id;
			this.type = type;
			this.source = source;
		}
	}
}
