package pl.michalwa.untitled.engine.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import pl.michalwa.untitled.engine.assets.*;

/**
 * Loads {@link Config} assets
 */
public class ConfigLoader implements Loader<Config>
{
	@Override
	public Class<Config> getAssetType()
	{
		return Config.class;
	}
	
	@Override
	public Config load(AssetDefinition definition, Assets assets) throws AssetLoaderException
	{
		Properties properties = new Properties();
		for(Source source : definition.getSources()) {
			try {
				properties.load(source.open());
			} catch(IOException e) {
				throw new AssetLoaderException("Could not load properties", e);
			}
		}
		return new Config(definition, properties);
	}
}
