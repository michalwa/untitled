package pl.michalwa.untitled.engine.assets;

import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Defines a source file from which an asset can be loaded
 */
public class Source
{
	/**
	 * Path to the source file
	 */
	private final Path path;
	
	/**
	 * Constructs a new source file definition with the given path
	 *
	 * @param path path to the source file
	 */
	Source(Path path)
	{
		this.path = path;
	}
	
	/**
	 * Constructs a new source file definition with the specified path
	 *
	 * @param path elements of the path to the source file as a string
	 */
	Source(String... path)
	{
		this.path = Paths.get("", path);
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
		String resolvedPath = "/" + path;
		InputStream is = Source.class.getResourceAsStream(resolvedPath);
		if(is == null) {
			throw new NoSuchFileException(resolvedPath, null, "Source file does not exist");
		}
		return is;
	}
	
	/**
	 * Resolves the given relative path against the parent path of this source file definition
	 * and returns a new source file definition with the resulting path
	 *
	 * @param relativePath the relative path to resolve
	 *
	 * @return a new source file definition with the resolved path
	 */
	public Source relative(Path relativePath)
	{
		return new Source(path.resolveSibling(relativePath));
	}
	
	/**
	 * Resolves the specified relative path against the parent path of this source file definition
	 * and returns a new source file definition with the resulting path
	 *
	 * @param relativePath the relative path to resolve as a string
	 *
	 * @return a new source file definition with the resolved path
	 */
	public Source relative(String relativePath)
	{
		return new Source(path.resolveSibling(relativePath));
	}
}
