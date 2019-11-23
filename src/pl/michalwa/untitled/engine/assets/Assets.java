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
	 * The root asset directory
	 */
	private final String rootDir;
	
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
	 * Constructs an asset loader and parses the given asset index.
	 * The passed {@code Loader<XML>} is immediately registered as the loader for "text/xml" type assets
	 *
	 * @param xmlLoader the loader responsible for loading XML documents
	 * @param parser the parser to use to parse the asset index document
	 * @param rootDir the root asset directory
	 * @param indexFilename the name of the asset index file in the asset directory
	 *
	 * @throws AssetLoaderException if {@code xmlLoader} throws {@link AssetLoaderException}
	 */
	public Assets(
		Loader<XML>      xmlLoader,
		AssetIndexParser parser,
		String           rootDir,
		String           indexFilename
	) throws AssetLoaderException
	{
		this.rootDir = rootDir;
		
		loaders = new HashMap<>();
		registerLoader(XML_TYPE, xmlLoader);
		
		XML index = (XML) loadAsset(XML_TYPE, assetPath(indexFilename));
		indexEntries = parser.parse(index.getDocument());
	}
	
	/**
	 * Registers a loader for the specified asset type
	 *
	 * @param type the asset type which the loader should load
	 * @param loader the loader
	 */
	public void registerLoader(String type, Loader<? extends Asset> loader)
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
		try {
			return Optional.of(require(id));
		} catch(AssetLoaderException e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Same as {@link #get(String)} but will throw an exception if the asset
	 * is not defined or can't be loaded
	 *
	 * @param id the ID of the asset to return
	 * @return the asset
	 *
	 * @throws AssetLoaderException if the asset is not defined or can't be loaded
	 */
	public Asset require(String id) throws AssetLoaderException
	{
		if(store == null) {
			throw new IllegalStateException("Asset component not initialized");
		}
		
		Optional<Asset> opt = store.get(id);
		if(opt.isPresent()) return opt.get();
		
		for(AssetIndexParser.Entry entry : indexEntries) {
			if(entry.id.equals(id)) {
				Asset asset = loadAsset(entry.type, assetPath(entry.source));
				store.add(id, asset);
				return asset;
			}
		}
		
		throw new AssetLoaderException("Undefined asset: " + id);
	}
	
	/**
	 * Loads an asset using the appropriate loader
	 *
	 * @param type the type of the asset to load (canonical or simple class name)
	 * @param path path to the file from which to load the asset
	 */
	private Asset loadAsset(String type, String path) throws AssetLoaderException
	{
		if(loaders.containsKey(type)) {
			InputStream is = Assets.class.getResourceAsStream(path);
			Asset asset = loaders.get(type).load(is);
			try {
				is.close();
			} catch(IOException e) {
				e.printStackTrace();  // when could this happen?
			}
			return asset;
		} else {
			throw new AssetLoaderException("No loader found for asset type: " + type);
		}
	}
	
	/**
	 * Returns the full path to the asset resource based on the given path
	 * relative to the asset root directory
	 *
	 * @param path the asset resource path relative to the asset root directory
	 * @return the full asset resource path
	 */
	private String assetPath(String path)
	{
		return "/" + Paths.get(rootDir, path);
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
