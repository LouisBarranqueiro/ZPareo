package beans;

import java.util.Set;

public class MatterScore implements Comparable<MatterScore>
{
	private Long id;
	private Matter matter;
	private Float average;
	private Set<Test> tests;
	
	/**
	 * Constructor
	 */
	public MatterScore()
	{
		this.id      = null;
		this.matter  = null;
		this.average = null;
		this.tests   = null;
	}
	
	/**
	 * Constructor
	 * 
	 * @param matterScore
	 */
	public MatterScore(MatterScore matterScore)
	{
		this.id      = matterScore.getId();
		this.matter  = matterScore.getMatter();
		this.average = matterScore.getAverage();
		this.tests   = matterScore.getTests();
	}
	/**
	 * Returns matterscore id
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Sets matterscore id
	 * 
	 * @return id 
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}

	/**
	 * Returns matterscore matter
	 * 
	 * @return matter 
	 */
	public Matter getMatter() 
	{
		return matter;
	}
	
	/**
	 * Sets matterscore matter
	 * 
	 * @param matter 
	 */
	public void setMatter(Matter matter) 
	{
		this.matter = matter;
	}
	
	/**
	 * Returns matterscore average
	 * 
	 * @return average 
	 */
	public Float getAverage() 
	{
		return average;
	}
	
	/**
	 * Sets matterscore average
	 * 
	 * @param average 
	 */
	public void setAverage(Float average) 
	{
		this.average = average;
	}
	
	/**
	 * Returns matterscore tests
	 * 
	 * @return tests 
	 */
	public Set<Test> getTests()
	{
		return tests;
	}
	
	/**
	 * Sets matterscore tests
	 * 
	 * @param tests 
	 */
	public void setTests(Set<Test> tests) 
	{
		this.tests = tests;
	}

	/**
	 * Compares two matterscore name
	 * 
	 * @param matterScore2 
	 */
	@Override
	public int compareTo(MatterScore matterScore2)
	{
		Matter matter1 = new Matter(this.getMatter());
		Matter matter2 = new Matter(matterScore2.getMatter());
        int compId = matter2.getName().compareTo(matter1.getName());
        
        return ((compId != 0) ? compId : 0 );
	}
	
}
