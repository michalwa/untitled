package pl.michalwa.untitled.engine.config;

import java.util.Objects;
import java.util.Properties;
import pl.michalwa.untitled.engine.assets.Asset;
import pl.michalwa.untitled.engine.utils.Int;

/**
 * A wrapper around {@link java.util.Properties} for storing configuration
 */
public class Config extends Asset
{
	/**
	 * The stored properties
	 */
	private final Properties properties;
	
	/**
	 * Constructs a new config asset
	 *
	 * @param id the ID of the asset
	 * @param properties the loaded properties
	 */
	Config(String id, Properties properties)
	{
		super(id, true);
		this.properties = properties;
	}
	
	/**
	 * Constructs a new empty config asset
	 */
	public Config()
	{
		super(null, false);
		properties = new Properties();
	}
	
	/**
	 * Sets the specified property to the given value passed to {@link Objects#toString()}
	 *
	 * @param property the property to set
	 * @param value the new value for the property
	 */
	public void set(String property, Object value)
	{
		properties.setProperty(property, Objects.toString(value));
	}
	
	/**
	 * Returns the raw value of the specified property or the given default value,
	 * if the property is not set.
	 *
	 * @param property the property to return
	 * @param defaultValue the value to be returned when the property is not set
	 *
	 * @return the value of the specified property or {@code defaultValue}
	 */
	public String get(String property, String defaultValue)
	{
		return properties.getProperty(property, defaultValue);
	}
	
	/**
	 * Same as {@link #get(String, String)} but tries to cast the value to an {@code int}.
	 * If the property is not set or its value is not an integer, returns the default value.
	 *
	 * @param property the property to return
	 * @param defaultValue the value to be returned when the property is not set
	 *
	 * @return the value of the specified property as {@code int} or {@code defaultValue}
	 */
	public int getInt(String property, int defaultValue)
	{
		return Int.tryParse(get(property, "")).orElse(defaultValue);
	}
}
