package pl.michalwa.untitled.engine.assets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.xml.XML;

/**
 * Loads and manages assets
 */
public class Assets implements Component
{
	/**
	 * XML asset type name
	 */
	public static final String XML_TYPE = "text/xml";
	
	/**
	 * Asset definitions parsed from the asset index document
	 */
	private final List<AssetIndexParser.Entry> indexEntries;
	
	/**
	 * Loaded asset store
	 */
	private AssetStore store = null;
	
	/**
	 * Loaders for different types of assets
	 */
	private final Map<String, Loader<?>> loaders;
	
	/**
	 * Constructs an asset loader and parses the specified asset index.
	 * The passed {@code Loader<XML>} is immediately registered as the loader for "text/xml" type assets
	 *
	 * @param xmlLoader the loader responsible for loading XML documents
	 * @param parser the parser to use to parse the asset index document
	 * @param rootDir the root asset directory
	 * @param indexFilename the name of the asset index file in the asset directory
	 *
	 * @throws AssetLoaderException if {@code xmlLoader} throws {@link AssetLoaderException}
	 * @throws AssetIndexException if {@code parser} throws {@link AssetIndexException}
	 */
	public Assets(
		Loader<XML>      xmlLoader,
		AssetIndexParser parser,
		String           rootDir,
		String           indexFilename
	) throws
		AssetLoaderException,
		AssetIndexException
	{
		loaders = new HashMap<>();
		registerLoader(XML_TYPE, xmlLoader);

		AssetIndexParser.Entry definition = new AssetIndexParser.Entry(
				null,
				XML_TYPE,
				Collections.singletonList(new Source(rootDir, indexFilename)));

		XML index = (XML) loadAsset(definition);
		indexEntries = parser.parse(index.getDocument(), rootDir);
	}
	
	/**
	 * Registers a loader for the specified asset type
	 *
	 * @param type the asset type which the loader should load
	 * @param loader the loader
	 */
	public void registerLoader(String type, Loader<?> loader)
	{
		loaders.put(type, loader);
	}
	
	/**
	 * Returns an already loaded asset or loads one, if defined in the asset index
	 * and returns it
	 *
	 * @param id the ID of the asset to return
	 *
	 * @return the asset wrapped in an {@link Optional} or an empty {@link Optional}
	 * if the asset is undefined or can't be loaded
	 */
	public Optional<Asset> get(String id)
	{
		if(store == null) {
			throw new IllegalStateException("Assets component not initialized");
		}
		
		Optional<Asset> opt = store.get(id);
		if(opt.isPresent()) return opt;
		
		try {
			for(AssetIndexParser.Entry entry : indexEntries) {
				if(entry.id.equals(id)) {
					Asset asset = loadAsset(entry);
					store.add(id, asset);
					return Optional.ofNullable(asset);
				}
			}
		} catch(AssetLoaderException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	/**
	 * Same as {@link #get(String)} but will throw a runtime exception if the asset
	 * is not defined or can't be loaded
	 *
	 * @param id the ID of the asset to return
	 * @return the asset
	 */
	public Asset require(String id)
	{
		if(store == null) {
			throw new IllegalStateException("Assets component not initialized");
		}
		
		Optional<Asset> opt = store.get(id);
		if(opt.isPresent()) return opt.get();
		
		try {
			for(AssetIndexParser.Entry entry : indexEntries) {
				if(entry.id.equals(id)) {
					Asset asset = loadAsset(entry);
					store.add(id, asset);
					return asset;
				}
			}
		} catch(AssetLoaderException e) {
			throw new RuntimeException(e);
		}
		
		throw new IllegalStateException("Undefined asset: " + id);
	}
	
	/**
	 * Loads an asset using the appropriate loader
	 *
	 * @param definition the asset index entry defining the asset to load
	 */
	private Asset loadAsset(AssetIndexParser.Entry definition) throws AssetLoaderException
	{
		if(loaders.containsKey(definition.type)) {
			return loaders.get(definition.type).load(definition.sources);
		} else {
			throw new AssetLoaderException("No loader found for asset type: " + definition.type);
		}
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName();
	}
	
	@Override
	public void getDependencies(Set<Class<? extends Component>> dependencies)
	{
		dependencies.add(AssetStore.class);
	}
	
	@Override
	public void initialize(Container container)
	{
		this.store = container.require(AssetStore.class);
	}
}
