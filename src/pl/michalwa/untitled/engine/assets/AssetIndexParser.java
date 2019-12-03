package pl.michalwa.untitled.engine.assets;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import pl.michalwa.untitled.engine.utils.collections.StreamUtils;

/**
 * Parses the asset index file
 * <br><br>
 * The asset index document must follow the format shown in the example below:
 * <pre>
 * &lt;?xml version="1.0" encoding="utf-8" ?&gt;
 *
 * &lt;!-- Required root tag --&gt;
 * &lt;assets&gt;
 *
 *     &lt;!--
 *     Asset definition with a single source,
 *     where the src attribute value is a path relative to the root
 *     asset directory pointing to the source file
 *     --&gt;
 *     &lt;asset id="my-asset" type="xml" src="xml/my-asset.xml"/&gt;
 *
 *     &lt;!-- Or with multiple sources: --&gt;
 *     &lt;asset id="my-config" type="config"&gt;
 *         &lt;source src="config/v1.properties"/&gt;
 *         &lt;source src="config/v2.properties"/&gt;
 *         &lt;source src="config/v3.properties"/&gt;
 *     &lt;/asset&gt;
 *
 *     &lt;!--
 *     An asset can also have no source definition, but that will most
 *     likely cause the loader to throw an exception
 *     --&gt;
 *     &lt;asset id="my-empty-asset" type="image"/&gt;
 *
 * &lt;/assets&gt;
 * </pre>
 */
public class AssetIndexParser
{
	/**
	 * The root asset index XML tag name
	 */
	private static final String ROOT_TAG = "assets";
	
	/**
	 * The name of the tag for defining an asset in the asset index
	 */
	private static final String ASSET_TAG = "asset";
	
	/**
	 * The name of the asset id attribute
	 */
	private static final String ID_ATTR = "id";
	
	/**
	 * The name of the asset type attribute
	 */
	private static final String TYPE_ATTR = "type";
	
	/**
	 * The name of the asset source attribute
	 */
	private static final String SOURCE_ATTR = "src";
	
	/**
	 * The name of the asset source tag
	 */
	private static final String SOURCE_TAG = "source";
	
	/**
	 * Parses the asset index and returns all defined assets
	 *
	 * @param index the asset index XML document
	 * @param rootDir the root asset directory path; all source paths will be prepended with this path
	 */
	public List<AssetDefinition> parse(Document index, String rootDir) throws AssetIndexException
	{
		List<AssetDefinition> entries = new ArrayList<>();
		
		// Get root element
		Element root = index.getDocumentElement();
		if(!root.getTagName().equals(ROOT_TAG)) {
			throw new AssetIndexException("Asset index must have <" + ROOT_TAG + "> as root");
		}
		
		// Iterate through children
		Node childNode = root.getFirstChild();
		while(childNode != null) {
			if(childNode.getNodeType() != Node.ELEMENT_NODE) {
				childNode = childNode.getNextSibling();
				continue;
			}
			Element child = (Element) childNode;
			if(!child.getTagName().equals(ASSET_TAG)) {
				childNode = childNode.getNextSibling();
				continue;
			}
			
			// Check for required attributes
			for(String attr : new String[] {
				ID_ATTR,
				TYPE_ATTR
			}) {
				if(!child.hasAttribute(attr)) {
					throw new AssetIndexException(
						"<" + ASSET_TAG + "> tags must have an `" + attr + "` attribute");
				}
			}
			
			// Check for source attribute
			List<Source> sources = new ArrayList<>();
			if(child.hasAttribute(SOURCE_ATTR)) {
				sources.add(new Source(Paths.get(rootDir, child.getAttribute(SOURCE_ATTR))));
			}
			
			// Parse source tags, if no source attribute is present
			else {
				Node childNode2 = child.getFirstChild();
				while(childNode2 != null) {
					if(childNode2.getNodeType() != Node.ELEMENT_NODE) {
						childNode2 = childNode2.getNextSibling();
						continue;
					}
					Element child2 = (Element) childNode2;
					if(!child2.getTagName().equals(SOURCE_TAG)) {
						childNode2 = childNode2.getNextSibling();
						continue;
					}
					
					// Check for source attribute on the source tag
					if(!child2.hasAttribute(SOURCE_ATTR)) {
						throw new AssetIndexException(
							"<" + SOURCE_TAG + "> tags must have an `" + SOURCE_ATTR + "` attribute");
					}
					
					sources.add(new Source(Paths.get(rootDir, child2.getAttribute(SOURCE_ATTR))));
					
					childNode2 = childNode2.getNextSibling();
				}
			}
			
			// Parse entry
			AssetDefinition entry = new AssetDefinition(
				child.getAttribute(ID_ATTR),
				child.getAttribute(TYPE_ATTR),
				sources
			);
			entries.add(entry);
			
			childNode = childNode.getNextSibling();
		}
		
		// Asset definitions with repeated IDs will be ignored
		return entries.stream()
			.filter(StreamUtils.distinctByKey(AssetDefinition::getId))
			.collect(Collectors.toList());
	}
}
