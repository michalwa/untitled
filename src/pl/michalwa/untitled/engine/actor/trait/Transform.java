package pl.michalwa.untitled.engine.actor.trait;

import pl.michalwa.untitled.engine.actor.Actor;
import pl.michalwa.untitled.engine.geom.Vector2f;
import pl.michalwa.untitled.engine.utils.struct.Observable;

/**
 * A trait that contains information about where and how an actor is positioned
 * relative to the lowest ancestor with this trait
 */
public class Transform extends Trait
{
	/**
	 * Position relative to parent actor
	 */
	public final Observable<Vector2f> position;
	
	/**
	 * Absolute position
	 */
	private Observable<Vector2f> absolutePosition;
	
	/**
	 * Rotation angle relative to parent actor
	 */
	public final Observable<Float> rotation;
	
	/**
	 * Absolute rotation angle
	 */
	private Observable<Float> absoluteRotation;
	
	/**
	 * Initializes the transform trait
	 */
	public Transform()
	{
		position = new Observable<>(Vector2f.ZERO);
		rotation = new Observable<>(0.0f);
	}
	
	/**
	 * Returns the observable absolute position of this transform
	 *
	 * @return the absolute position of this transform
	 */
	public Observable<Vector2f> absolutePosition()
	{
		return absolutePosition;
	}
	
	/**
	 * Returns the observable absolute rotation angle of this transform
	 *
	 * @return the absolute rotation angle of this transform
	 */
	public Observable<Float> absoluteRotation()
	{
		return absoluteRotation;
	}
	
	/**
	 * Adds the given vector to the position of this transform
	 *
	 * @param v the vector to translate by
	 */
	public void translate(Vector2f v)
	{
		position.set(position.get().add(v));
	}
	
	/**
	 * Adds the specified vector to the position of this transform
	 *
	 * @param x the X coordinate of the vector to translate by
	 * @param y the Y coordinate of the vector to translate by
	 */
	public void translate(float x, float y)
	{
		position.set(position.get().add(x, y));
	}
	
	/**
	 * Adds the specified angle value to the rotation of this transform
	 *
	 * @param angle the angle to rotate by
	 */
	public void rotate(float angle)
	{
		rotation.set(rotation.get() + angle);
	}
	
	@Override
	protected void onAttached(Actor actor)
	{
		// TODO: Position affected by parent rotation
		
		absolutePosition = position.map(
			
			relative -> getActor().findInAncestors(Transform.class)
				.map(t -> t.absolutePosition.get().add(relative))
				.orElse(relative),
			
			absolute -> getActor().findInAncestors(Transform.class)
				.map(t -> absolute.sub(t.absolutePosition.get()))
				.orElse(absolute));
		
		absoluteRotation = rotation.map(
			
			relative -> getActor().findInAncestors(Transform.class)
				.map(t -> t.absoluteRotation.get() + relative)
				.orElse(relative),
			
			absolute -> getActor().findInAncestors(Transform.class)
				.map(t -> absolute - t.absoluteRotation.get())
				.orElse(absolute));
	}
}
