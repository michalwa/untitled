package pl.michalwa.untitled.engine.actor;

import java.util.Collections;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import pl.michalwa.untitled.engine.actor.trait.Trait;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Actor}
 */
public class ActorTest
{
	private Actor parent, child;
	private Trait parentTrait, childTrait;
	
	@Before
	public void setup()
	{
		parent = new Actor();
		child = new Actor();
		
		parent.addChild(child);
		
		parentTrait = new TestTrait();
		childTrait = new TestTrait();
		
		parent.attach(parentTrait);
		child.attach(childTrait);
	}
	
	@Test
	public void getTrait()
	{
		assertEquals(Optional.of(parentTrait), parent.getTrait(TestTrait.class));
		assertEquals(Optional.of(childTrait), child.getTrait(TestTrait.class));
	}
	
	@Test
	public void findAllInDescendants()
	{
		assertEquals(Collections.singletonList(childTrait), parent.findAllInDescendants(TestTrait.class));
	}
	
	@Test
	public void findInAncestors()
	{
		assertEquals(Optional.ofNullable(parentTrait), child.findInAncestors(TestTrait.class));
	}
	
	static class TestTrait extends Trait
	{
		@Override
		protected void onAttached(Actor actor) {}
	}
}
