package pl.michalwa.untitled.engine.assets;

import java.util.*;
import java.util.stream.Collectors;
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
	public static final String XML_TYPE = "xml";
	
	/**
	 * The root asset directory
	 */
	private final String rootDir;
	
	/**
	 * Asset definitions parsed from the asset index document
	 */
	private final List<AssetIndexEntry> indexEntries;
	
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
		this.rootDir = rootDir;
		
		loaders = new HashMap<>();
		registerLoader(XML_TYPE, xmlLoader);

		AssetIndexEntry definition = new AssetIndexEntry(
				null,
				XML_TYPE,
				Collections.singletonList(indexFilename));

		XML index = (XML) load(definition);
		indexEntries = parser.parse(index.getDocument());
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
	 * Returns a registered loader for the specified asset type
	 *
	 * @param type the type of asset loader to return
	 *
	 * @return the loader or {@code null} if no such loader is registered
	 */
	public Loader<?> getLoader(String type)
	{
		return loaders.get(type);
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
			for(AssetIndexEntry entry : indexEntries) {
				if(entry.id.equals(id)) {
					Asset asset = load(entry);
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
			for(AssetIndexEntry entry : indexEntries) {
				if(entry.id.equals(id)) {
					Asset asset = load(entry);
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
	 * Loads an asset based on the given definition using the appropriate loader
	 *
	 * @param definition the asset index entry describing the asset to load
	 */
	public Asset load(AssetIndexEntry definition) throws AssetLoaderException
	{
		if(loaders.containsKey(definition.type)) {
			List<Source> sources = definition.sources.stream()
				.map(s -> new Source(rootDir, s))
				.collect(Collectors.toList());
			
			return loaders.get(definition.type).load(definition.id, sources, this);
			
		} else {
			throw new AssetLoaderException("No loader found for asset type: " + definition.type);
		}
	}
	
	/**
	 * Adds an asset definition to the asset index
	 *
	 * @param entry the asset index entry describing the asset to add
	 */
	public void addDefinition(AssetIndexEntry entry)
	{
		indexEntries.add(entry);
	}
	
	/**
	 * Returns the asset store associated with this assets component
	 *
	 * @return the asset store
	 */
	public AssetStore getStore()
	{
		return store;
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
