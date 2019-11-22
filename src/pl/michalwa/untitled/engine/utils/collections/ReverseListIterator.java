package pl.michalwa.untitled.engine.utils.collections;

import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Iterates through a list in reverse order
 *
 * @param <E> the list element type
 */
public class ReverseListIterator<E> implements Iterator<E>
{
	/**
	 * The underlying iterator
	 */
	private final ListIterator<E> iterator;
	
	/**
	 * Constructs a new reverse list iterator iterating through the given list
	 *
	 * @param list the list to iterate through
	 */
	public ReverseListIterator(List<E> list)
	{
		iterator = list.listIterator(list.size());
	}
	
	@Override
	public boolean hasNext()
	{
		return iterator.hasPrevious();
	}
	
	@Override
	public E next()
	{
		return iterator.previous();
	}
}
