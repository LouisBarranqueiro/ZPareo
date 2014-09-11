package beans;

import java.util.Set;

public class Teacher extends User implements Comparable<Teacher>
{
	private Set<Matter> matters;
	private Set<Group> groups;
	
	/**
	 * Constructor
	 */
	public Teacher()
	{
		super();
		this.matters = null;
		this.groups = null;
	}
	/**
	 * Constructor
	 * 
	 * @param teacher 
	 */
	public Teacher(Teacher teacher)
	{
		super(teacher);
		this.matters = teacher.getMatters();
		this.groups = teacher.getGroups();
	}
	
	/**
	 * Returns teacher matters
	 * 
	 * @return matters
	 */
	public Set<Matter> getMatters()
	{
		return matters;
	}
	
	/**
	 * Sets teacher matters
	 * 
	 * @param matters 
	 */
	public void setMatters(Set<Matter> matters) 
	{
		this.matters = matters;
	}
	
	/**
	 * Returns teacher groups
	 * 
	 * @return groups 
	 */
	public Set<Group> getGroups() 
	{
		return groups;
	}
	
	/**
	 * Sets teacher groups
	 * 
	 * @param groups 
	 */
	public void setGroups(Set<Group> groups) 
	{
		this.groups = groups;
	}
	
	/**
	 * Compares two teacher id
	 * 
	 * @param teacher2 
	 */
	@Override
	public int compareTo(Teacher teacher2) 
	{
        int compId = teacher2.getId().compareTo(this.getId());

        return ((compId != 0) ? compId : 0);
	}
	
}