package pl.michalwa.untitled.engine.actor;

/**
 * An actor with a label
 *
 * @see Actor
 */
public class LabeledActor extends Actor
{
	/**
	 * The label
	 */
	private String label = null;
	
	/**
	 * Constructs a new labeled actor with no label
	 */
	public LabeledActor() {}
	
	/**
	 * Constructs a new labeled actor with the given label
	 *
	 * @param label the label
	 */
	public LabeledActor(String label)
	{
		this.label = label;
	}
	
	@Override
	public String toString()
	{
		return label == null ? super.toString() : label;
	}
	
	/**
	 * Returns the label for this actor
	 *
	 * @return the label for this actor
	 */
	public String getLabel()
	{
		return label;
	}
	
	/**
	 * Sets the label for this actor
	 *
	 * @param label the new label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}
}
