package pl.michalwa.untitled.engine.utils.struct;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link Observable}
 */
public class ObservableTest
{
	private int i = 0;
	
	@Test
	public void subscribe()
	{
		Observable<Integer> o = new Observable<>(42);
		o.subscribe(value -> i = value);
		
		assertEquals(0, i);
		
		o.set(420);
		
		assertEquals(420, i);
	}
	
	@Test
	public void simpleOneWayBinding()
	{
		// b := a
		
		Observable<Integer> a = new Observable<>(5);
		Observable<Integer> b = new Observable<>(0);
		b.bindTo(a);
		
		assertEquals(5, (int) a.get());
		assertEquals(5, (int) b.get());
		
		a.set(123);
		
		assertEquals(123, (int) a.get());
		assertEquals(123, (int) b.get());
	}
	
	@Test
	public void mappingOneWayBinding()
	{
		// b := 2a
		
		Observable<Integer> a = new Observable<>(5);
		Observable<Integer> b = new Observable<>(0);
		b.bindTo(a, x -> x * 2);
		
		assertEquals(5, (int) a.get());
		assertEquals(10, (int) b.get());
		
		a.set(123);
		
		assertEquals(123, (int) a.get());
		assertEquals(246, (int) b.get());
	}
	
	@Test
	public void simpleTwoWayBinding()
	{
		// a = b
		
		Observable<Integer> a = new Observable<>(5);
		Observable<Integer> b = new Observable<>(0);
		b.bindTwoWay(a);
		
		assertEquals(5, (int) a.get());
		assertEquals(5, (int) b.get());
		
		a.set(123);
		
		assertEquals(123, (int) a.get());
		assertEquals(123, (int) b.get());
		
		b.set(321);
		
		assertEquals(321, (int) a.get());
		assertEquals(321, (int) b.get());
	}
	
	@Test
	public void mappingTwoWayBinding()
	{
		// a + 2 = b
		
		Observable<Integer> a = new Observable<>(5);
		Observable<Integer> b = new Observable<>(0);
		b.bindTwoWay(a, x -> x + 2, x -> x - 2);
		
		assertEquals(5, (int) a.get());
		assertEquals(7, (int) b.get());
		
		a.set(123);
		
		assertEquals(123, (int) a.get());
		assertEquals(125, (int) b.get());
		
		b.set(321);
		
		assertEquals(319, (int) a.get());
		assertEquals(321, (int) b.get());
	}
	
	@Test
	public void simpleOneWayDoubleBinding()
	{
		// a := b + c
		Observable<Integer> a = new Observable<>(0);
		Observable<Integer> b = new Observable<>(1);
		Observable<Integer> c = new Observable<>(2);
		
		a.bindTo(b, c, Integer::sum);
		
		assertEquals(3, (int) a.get());
		assertEquals(1, (int) b.get());
		assertEquals(2, (int) c.get());
		
		b.set(2);
		
		assertEquals(4, (int) a.get());
		assertEquals(2, (int) b.get());
		assertEquals(2, (int) c.get());
		
		c.set(6);
		
		assertEquals(8, (int) a.get());
		assertEquals(2, (int) b.get());
		assertEquals(6, (int) c.get());
	}
}
