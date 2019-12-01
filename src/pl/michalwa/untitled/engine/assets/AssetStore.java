package pl.michalwa.untitled.engine.assets;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import pl.michalwa.untitled.engine.component.Component;
import pl.michalwa.untitled.engine.component.Container;

/**
 * Stores existing assets
 */
public class AssetStore implements Component
{
	/**
	 * The stored assets
	 */
	private final Map<String, Asset> assets = new HashMap<>();
	
	/**
	 * Adds the given asset to the store
	 *
	 * @param id the ID of the asset
	 * @param asset the asset to add
	 *
	 * @throws IllegalArgumentException if an asset with the same ID already exists
	 */
	public void add(String id, Asset asset)
	{
		if(assets.containsKey(id)) {
			throw new IllegalArgumentException("An asset with the ID `" + id + "` already exists");
		}
		
		assets.put(id, asset);
	}
	
	/**
	 * Returns the asset with the given ID
	 *
	 * @param id the ID of the asset to return
	 * @return the asset wrapped in an {@link Optional}, if defined in the store
	 * or an empty {@link Optional} otherwise
	 */
	public Optional<Asset> get(String id)
	{
		return Optional.ofNullable(assets.get(id));
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
