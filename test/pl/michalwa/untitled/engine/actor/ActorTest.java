package pl.michalwa.untitled.engine.actor;

import java.util.Collections;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import pl.michalwa.untitled.engine.actor.events.AncestorAddedEvent;
import pl.michalwa.untitled.engine.actor.events.AncestorRemovedEvent;
import pl.michalwa.untitled.engine.actor.events.DescendantAddedEvent;
import pl.michalwa.untitled.engine.actor.events.DescendantRemovedEvent;
import pl.michalwa.untitled.engine.actor.trait.Trait;

import static org.junit.Assert.*;

/**
 * Tests for {@link Actor}
 */
public class ActorTest
{
	private Actor parent, child;
	private Trait parentTrait, childTrait;
	
	/**
	 * Variables set by subscribers to see if events were fired
	 */
	private boolean event1 = false, event2 = false, event3 = false;
	
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
	
	@Test
	public void ancestorAdded()
	{
		Actor a = new Actor();
		Actor b = new Actor();
		Actor c = new Actor();
		Actor d = new Actor();
		
		b.addChild(c);
		c.addChild(d);
		
		b.subscribe(AncestorAddedEvent.class, event -> {
			assertEquals(a, event.actor);
			event1 = true;
		});
		
		c.subscribe(AncestorAddedEvent.class, event -> {
			assertEquals(a, event.actor);
			event2 = true;
			event.consume();
		});
		
		d.subscribe(AncestorAddedEvent.class, event -> {
			/* (consumed) */
			event3 = true;
		});
		
		a.addChild(b);
		
		assertTrue(event1);
		assertTrue(event2);
		assertFalse(event3);
		
		event1 = event2 = event3 = false;
	}
	
	@Test
	public void ancestorRemoved()
	{
		Actor a = new Actor();
		Actor b = new Actor();
		Actor c = new Actor();
		Actor d = new Actor();
		
		a.addChild(b);
		b.addChild(c);
		c.addChild(d);
		
		b.subscribe(AncestorRemovedEvent.class, event -> {
			assertEquals(a, event.actor);
			event1 = true;
		});
		
		c.subscribe(AncestorRemovedEvent.class, event -> {
			assertEquals(a, event.actor);
			event2 = true;
			event.consume();
		});
		
		d.subscribe(AncestorRemovedEvent.class, event -> {
			/* (consumed) */
			event3 = true;
		});
		
		a.removeChild(b);
		
		assertTrue(event1);
		assertTrue(event2);
		assertFalse(event3);
		
		event1 = event2 = event3 = false;
	}
	
	@Test
	public void descendantAdded()
	{
		Actor a = new Actor();
		Actor b = new Actor();
		Actor c = new Actor();
		Actor d = new Actor();
		
		a.addChild(b);
		b.addChild(c);
		
		a.subscribe(DescendantAddedEvent.class, event -> {
			/* (consumed) */
			event1 = true;
		});
		
		b.subscribe(DescendantAddedEvent.class, event -> {
			assertEquals(d, event.actor);
			event2 = true;
			event.consume();
		});
		
		c.subscribe(DescendantAddedEvent.class, event -> {
			assertEquals(d, event.actor);
			event3 = true;
		});
		
		c.addChild(d);
		
		assertFalse(event1);
		assertTrue(event2);
		assertTrue(event3);
		
		event1 = event2 = event3 = false;
	}
	
	@Test
	public void descendantRemoved()
	{
		Actor a = new Actor();
		Actor b = new Actor();
		Actor c = new Actor();
		Actor d = new Actor();
		
		a.addChild(b);
		b.addChild(c);
		c.addChild(d);
		
		a.subscribe(DescendantRemovedEvent.class, event -> {
			/* (consumed) */
			event1 = true;
		});
		
		b.subscribe(DescendantRemovedEvent.class, event -> {
			assertEquals(d, event.actor);
			event2 = true;
			event.consume();
		});
		
		c.subscribe(DescendantRemovedEvent.class, event -> {
			assertEquals(d, event.actor);
			event3 = true;
		});
		
		c.removeChild(d);
		
		assertFalse(event1);
		assertTrue(event2);
		assertTrue(event3);
		
		event1 = event2 = event3 = false;
	}
	
	static class TestTrait extends Trait
	{
		@Override
		protected void onAttached(Actor actor) {}
	}
}
