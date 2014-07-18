package beans;

import java.util.Set;

public class Gradebook
{
	private Long id;
	private Float average;
	private Set<MatterScore> matterScores;
	
	/**
	 * Constructeur
	 */
	public Gradebook()
	{
		this.id           = null;
		this.average      = null;
		this.matterScores = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param gradebook 
	 */
	public Gradebook(Gradebook gradebook)
	{
		this.id           = gradebook.getId();
		this.average      = gradebook.getAverage();
		this.matterScores = gradebook.getMatterScores();
	}
	
	/**
	 * Returns gradebook id
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Sets gradebook id
	 * 
	 * @param id 
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Returns gradebook average
	 * 
	 * @return average 
	 */
	public Float getAverage() 
	{
		return average;
	}
	/**
	 * Sets gradebook average
	 * 
	 * @param average 
	 */
	public void setAverage(Float average) 
	{
		this.average = average;
	}
	
	/**
	 * Returns gradebook matterscores
	 * 
	 * @return matterscores 
	 */
	public Set<MatterScore> getMatterScores()
	{
		return matterScores;
	}
	
	/**
	 * Sets gradebook matterscores
	 * 
	 * @param matterScores
	 */
	public void setMatterScores(Set<MatterScore> matterScores)
	{
		this.matterScores = matterScores;
	}
}

