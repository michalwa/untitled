package pl.michalwa.untitled.engine.assets;

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
	 * Asset definitions parsed from the asset index document
	 */
	private final List<AssetDefinition> indexEntries;
	
	/**
	 * Loaded asset store
	 */
	private final AssetStore store = new AssetStore();
	
	/**
	 * Loaders for different types of assets
	 */
	private final List<Loader<?>> loaders;
	
	/**
	 * Constructs an asset loader and parses the specified asset index.
	 * The passed {@code Loader<XML>} is immediately registered as the loader for XML assets
	 *
	 * @param xmlLoader the loader responsible for loading XML documents
	 * @param rootDir the root asset directory
	 * @param indexPath the absolute path to the asset index XML file
	 *
	 * @throws AssetLoaderException if {@code xmlLoader} throws {@link AssetLoaderException}
	 * @throws AssetIndexException if {@code parser} throws {@link AssetIndexException}
	 */
	public Assets(
		Loader<XML> xmlLoader,
		String      rootDir,
		String      indexPath
	) throws
		AssetLoaderException,
		AssetIndexException
	{
		loaders = new ArrayList<>();
		registerLoader(xmlLoader);

		AssetDefinition definition = new AssetDefinition(
				null,
				XML.class.getCanonicalName(),
				Collections.singletonList(new Source(indexPath)));
		
		XML index = (XML) load(definition);
		AssetIndexParser parser = new AssetIndexParser();
		indexEntries = parser.parse(index.getDocument(), rootDir);
	}
	
	/**
	 * Registers a loader to be used for the associated asset type.
	 * If a loader for the same asset type is already registered, it is removed and the
	 * given loader is registered to be used instead.
	 *
	 * @param loader the loader
	 *
	 * @return {@code this} for method call chaining
	 */
	public <T extends Asset> Assets registerLoader(Loader<T> loader)
	{
		Loader<?> existing;
		if((existing = getLoader(loader.getAssetType().getCanonicalName())) != null) {
			loaders.remove(existing);
		}
		loaders.add(loader);
		return this;
	}
	
	/**
	 * Returns a registered loader for the specified asset type
	 *
	 * @param typename the asset type name to return the loader for - the simple or canonical asset class name
	 *
	 * @return the loader or {@code null} if no such loader is found
	 */
	public Loader<?> getLoader(String typename)
	{
		for(Loader<?> loader : loaders) {
			Class<? extends Asset> loaderType = loader.getAssetType();
			if(typename.equals(loaderType.getCanonicalName()) || typename.equals(loaderType.getSimpleName())) {
				return loader;
			}
		}
		return null;
	}
	
	/**
	 * Returns an already loaded asset or loads one if defined and returns it
	 *
	 * @param id the ID of the asset to return
	 *
	 * @return the asset wrapped in an {@link Optional} or an empty {@link Optional}
	 *  if the asset is not defined or can't be loaded
	 */
	public Optional<Asset> get(String id)
	{
		try {
			return Optional.ofNullable(require(id));
		} catch(RequiredAssetException e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Same as {@link #get(String)} but will throw a runtime exception if the asset
	 * is not defined or can't be loaded
	 *
	 * @param id the ID of the asset to return
	 *
	 * @return the asset
	 */
	public Asset require(String id)
	{
		Optional<Asset> opt = store.get(id);
		if(opt.isPresent()) return opt.get();
		
		Optional<AssetDefinition> def = indexEntries.stream()
			.filter(e -> e.getId().equals(id))
			.findAny();
		
		if(def.isPresent()) {
			try {
				Asset asset = load(def.get());
				store.add(id, asset);
				return asset;
			} catch(AssetLoaderException e) {
				throw new RequiredAssetException(e);
			}
		}
		
		throw new RequiredAssetException("Undefined asset: " + id);
	}
	
	/**
	 * Same as {@link #require(String)} but will throw a runtime exception if the asset
	 * is not of the specified type
	 *
	 * @param type the expected type of the asset to return
	 * @param id the ID of the asset to return
	 *
	 * @return the asset
	 */
	@SuppressWarnings("unchecked")
	public <T extends Asset> T require(Class<T> type, String id)
	{
		Asset asset = require(id);
		if(type.isInstance(asset)) {
			return (T) asset; // "unchecked" cast
		}
		throw new RequiredAssetException("Asset `" + id + "` not of type: " + type.getName());
	}
	
	/**
	 * Loads an asset based on the given definition using the appropriate loader
	 *
	 * @param definition the asset index entry describing the asset to load
	 */
	public Asset load(AssetDefinition definition) throws AssetLoaderException
	{
		Loader<?> loader = getLoader(definition.getTypename());
		if(loader != null) {
			return loader.load(definition, this);
		}
		throw new AssetLoaderException("No loader registered for type: " + definition.getTypename());
	}
	
	/**
	 * Adds an asset definition to the asset index
	 *
	 * @param entry the asset index entry describing the asset to add
	 */
	public void addDefinition(AssetDefinition entry)
	{
		indexEntries.add(entry);
	}
	
	/**
	 * Returns the asset store used to store loaded assets
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
	public void getDependencies(Set<Class<? extends Component>> dependencies) {}
	
	@Override
	public void initialize(Container container) {}
}
