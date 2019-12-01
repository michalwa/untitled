package pl.michalwa.untitled.engine.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import pl.michalwa.untitled.engine.assets.AssetLoaderException;
import pl.michalwa.untitled.engine.assets.Assets;
import pl.michalwa.untitled.engine.assets.Loader;
import pl.michalwa.untitled.engine.assets.Source;

/**
 * Loads {@link Config} assets
 */
public class ConfigLoader implements Loader<Config>
{
	@Override
	public Config load(String id, List<Source> sources, Assets assets) throws AssetLoaderException
	{
		Properties properties = new Properties();
		for(Source source : sources) {
			try {
				properties.load(source.open());
			} catch(IOException e) {
				throw new AssetLoaderException("Could not load properties", e);
			}
		}
		return new Config(id, properties);
	}
}
