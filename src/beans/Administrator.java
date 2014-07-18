package beans;

public class Administrator extends User implements Comparable<Administrator>
{
	/**
	 * Constructor
	 * 
	 */
	public Administrator()
	{
		super();
	}
	/**
	 * Constructor
	 * 
	 * @param administrator 
	 */
	public Administrator(Administrator administrator)
	{
		super(administrator);
	}
	
	/**
	 * Compares two administrators's id
	 * 
	 * @param administrator2 
	 */
	@Override
	public int compareTo(Administrator administrator2) 
	{
		int compId = this.getId().compareTo(administrator2.getId());
        
		return ((compId != 0) ? compId : 0);
	}

}
