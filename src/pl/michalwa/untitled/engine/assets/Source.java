package pl.michalwa.untitled.engine.assets;

import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import pl.michalwa.untitled.engine.utils.Strings;

/**
 * Defines a source file from which an asset can be loaded
 */
public class Source
{
	/**
	 * Path to the source file
	 */
	private final String path;
	
	/**
	 * Constructs a new source file definition
	 *
	 * @param rootDir the root asset directory
	 * @param file path to the source file
	 */
	Source(String rootDir, String file)
	{
		path = Strings.ensureStartsWith("/", Paths.get(rootDir, file).toString());
	}
	
	/**
	 * Opens and returns an input stream from the resource pointed to by this source definition.
	 * If the source points to a file that doesn't exist, {@link NoSuchFileException} is thrown.
	 *
	 * @return the created input stream
	 *
	 * @throws NoSuchFileException when the source file doesn't exist
	 */
	public InputStream open() throws NoSuchFileException
	{
		InputStream is = Source.class.getResourceAsStream(path);
		if(is == null) {
			throw new NoSuchFileException(path, null, "Source file does not exist");
		}
		return is;
	}
}
