package pl.michalwa.untitled.engine.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

/**
 * Tests for {@link Container}
 */
public class ContainerTest
{
	private List<TestComponent> initializationOrder;
	
	@Before
	public void setup()
	{
		initializationOrder = new ArrayList<>();
	}
	
	/**
	 * Tests whether dependency loops are detected correctly
	 */
	@Test
	public void cyclicDependencyDetection() throws Exception
	{
		Container container = new Container();
		try {
			container.register(new CyclicA(), new CyclicB(), new CyclicC()).initialize();
			fail();
		} catch(DependencyLoopException e) {
			// expected
		}
	}
	
	/**
	 * Tests whether a valid setup successfully initializes in the correct order
	 */
	@Test
	public void initializationOrder() throws Exception
	{
		initializationOrder.clear();
		Container container = new Container();
		A a = new A();
		B b = new B();
		C c = new C();
		container.register(a, b, c).initialize();
		assertArrayEquals(new Object[] { b, c, a }, initializationOrder.toArray());
	}
	
	/**
	 * Tests whether missing dependencies cause an exception to be thrown
	 */
	@Test
	public void missingDependencies() throws Exception
	{
		Container container = new Container();
		try {
			container.register(new A(), new B()).initialize();
			fail();
		} catch(MissingComponentException e) {
			// expected
		}
	}
	
	private abstract class TestComponent implements Component
	{
		@Override
		public void initialize(Container container)
		{
			initializationOrder.add(this);
		}
		
		@Override
		public String toString()
		{
			return getClass().getSimpleName();
		}
	}
	
	private class CyclicA extends TestComponent
	{
		@Override
		public void getDependencies(Set<Class<? extends Component>> dependencies)
		{
			dependencies.add(CyclicB.class);
		}
	}
	
	private class CyclicB extends TestComponent
	{
		@Override
		public void getDependencies(Set<Class<? extends Component>> dependencies)
		{
			dependencies.add(CyclicC.class);
		}
	}
	
	private class CyclicC extends TestComponent
	{
		@Override
		public void getDependencies(Set<Class<? extends Component>> dependencies)
		{
			dependencies.add(CyclicA.class);
		}
	}
	
	private class A extends TestComponent
	{
		@Override
		public void getDependencies(Set<Class<? extends Component>> dependencies)
		{
			dependencies.add(B.class);
			dependencies.add(C.class);
		}
	}
	
	private class B extends TestComponent
	{
		@Override
		public void getDependencies(Set<Class<? extends Component>> dependencies) {}
	}
	
	private class C extends TestComponent
	{
		@Override
		public void getDependencies(Set<Class<? extends Component>> dependencies)
		{
			dependencies.add(B.class);
		}
	}
}
