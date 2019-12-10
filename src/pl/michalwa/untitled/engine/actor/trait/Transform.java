package pl.michalwa.untitled.engine.actor.trait;

import java.util.Optional;
import pl.michalwa.untitled.engine.actor.Actor;
import pl.michalwa.untitled.engine.actor.events.AncestorAddedEvent;
import pl.michalwa.untitled.engine.actor.events.AncestorRemovedEvent;
import pl.michalwa.untitled.engine.geom.ObservableVector2f;
import pl.michalwa.untitled.engine.geom.Vector2f;
import pl.michalwa.untitled.engine.utils.struct.ObservableFloat;

/**
 * A trait that contains information about where and how an actor is positioned
 * relative to the lowest ancestor with this trait
 */
public class Transform extends Trait
{
	/**
	 * The parent transform (attached to closest ancestor) or {@code null}
	 */
	private Transform parent = null;
	
	/**
	 * Position relative to parent actor
	 */
	public final ObservableVector2f position;
	
	/**
	 * Absolute position
	 */
	public final ObservableVector2f absolutePosition;
	
	/**
	 * Rotation angle relative to parent actor
	 */
	public final ObservableFloat rotation;
	
	/**
	 * Absolute rotation angle
	 */
	public final ObservableFloat absoluteRotation;
	
	/**
	 * Initializes the transform trait
	 */
	public Transform()
	{
		position = new ObservableVector2f(Vector2f.ZERO);
		rotation = new ObservableFloat(0.0f);
		absolutePosition = new ObservableVector2f(Vector2f.ZERO);
		absoluteRotation = new ObservableFloat(0.0f);
	}
	
	/**
	 * Returns the parent transform (attached to the closest ancestor with the {@link Transform} trait)
	 * or an empty {@link Optional} if there is none.
	 *
	 * @return the parent transform
	 */
	public Optional<Transform> getParent()
	{
		return Optional.ofNullable(parent);
	}
	
	/**
	 * Adds the given vector to the position of this transform
	 *
	 * @param v the vector to translate by
	 */
	public void translate(Vector2f v)
	{
		position.add(v);
	}
	
	/**
	 * Adds the specified vector to the position of this transform
	 *
	 * @param x the X coordinate of the vector to translate by
	 * @param y the Y coordinate of the vector to translate by
	 */
	public void translate(float x, float y)
	{
		position.add(x, y);
	}
	
	/**
	 * Adds the specified angle value to the rotation of this transform
	 *
	 * @param angle the angle to rotate by
	 */
	public void rotate(float angle)
	{
		rotation.add(angle);
	}
	
	@Override
	protected void onAttached(Actor actor)
	{
		resetBindings();
		parent = actor.findInAncestors(Transform.class).orElse(null);
		bindToParent();
		
		actor.subscribe(AncestorAddedEvent.class, event -> {
			Transform newParent = actor.findInAncestors(Transform.class).orElse(null);
			if(parent != newParent) {
				parent = newParent;
				bindToParent();
			}
		});
		
		actor.subscribe(AncestorRemovedEvent.class, event -> {
			if(parent != null && parent.getActor() == event.actor) {
				resetBindings();
				parent = null;
			}
		});
	}
	
	/**
	 * Binds properties of this transform to the parent transform, if it exists
	 */
	private void bindToParent()
	{
		// Bindings:
		//
		//     this.absolutePosition -> this.position          (x - parent.absolutePosition).rotate(-parent.absoluteRotation)
		//     this.position         -> this.absolutePosition  (x.rotate(parent.absoluteRotation) + parent.absolutePosition)
		//   parent.absolutePosition -> this.absolutePosition  (x + this.position.rotate(parent.absoluteRotation))
		//   parent.absoluteRotation -> this.absolutePosition  (parent.absolutePosition + this.position.rotate(x))
		//     this.absoluteRotation -> this.rotation          (x - parent.absoluteRotation)
		//     this.rotation         -> this.absoluteRotation  (x + parent.absoluteRotation)
		//   parent.absoluteRotation -> this.absoluteRotation  (x + this.rotation)
		
		if(parent == null) return;
		
		absolutePosition.bindTwoWay(position,
			x -> x.rotate(parent.absoluteRotation.get()).add(parent.absolutePosition),
			x -> x.sub(parent.absolutePosition).rotate(-parent.absoluteRotation.get()));
		absolutePosition.bindTo(parent.absolutePosition, x -> x.add(position.get().rotate(parent.absoluteRotation.get())));
		absolutePosition.bindTo(parent.absoluteRotation, x -> parent.absolutePosition.get().add(position.get().rotate(x)));
		absoluteRotation.bindTwoWay(rotation, x -> x + parent.absoluteRotation.get(), x -> x - parent.absoluteRotation.get());
		absoluteRotation.bindTo(parent.absoluteRotation, x -> x + rotation.get());
	}
	
	/**
	 * Resets the bindings of the properties of this transform
	 */
	private void resetBindings()
	{
		if(parent != null) {
			absolutePosition.unbindFrom(parent.absolutePosition);
			absolutePosition.unbindFrom(parent.absoluteRotation);
			absoluteRotation.unbindFrom(parent.absoluteRotation);
		}
		absolutePosition.unbindTwoWay(position);
		absoluteRotation.unbindTwoWay(rotation);
		
		absolutePosition.bindTwoWay(position);
		absoluteRotation.bindTwoWay(rotation);
	}
	
	@Override
	public String toString()
	{
		return "Transform(" + position.get() + ", " + rotation.get() + ")";
	}
}
