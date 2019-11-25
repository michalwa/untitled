package pl.michalwa.untitled.engine.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import pl.michalwa.untitled.engine.assets.AssetLoaderException;
import pl.michalwa.untitled.engine.assets.Loader;

/**
 * Loads {@link Config} assets
 */
public class ConfigLoader implements Loader<Config>
{
	@Override
	public Config load(InputStream is) throws AssetLoaderException
	{
		try {
			Properties properties = new Properties();
			properties.load(is);
			return new Config(properties);
		} catch(IOException e) {
			throw new AssetLoaderException("Could not load properties", e);
		}
	}
}
