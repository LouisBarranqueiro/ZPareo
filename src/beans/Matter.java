package beans;

public class Matter implements Comparable<Matter>
{
	private Long id;
	private String name;
	private Administrator creator;
	private Administrator editor;
	
	/**
	 * Constructor
	 */
	public Matter()
	{
		this.id      = null;
		this.name    = null;
		this.creator = null;
		this.editor  = null;
	}
	
	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public Matter(Long id)
	{
		this.id      = id;
		this.name    = null;
		this.creator = null;
		this.editor  = null;
	}
	
	/**
	 * Constructor
	 * 
	 * @param matter 
	 */
	public Matter(Matter matter)
	{
		this.id      = matter.getId();
		this.name    = matter.getName();
		this.creator = matter.getCreator();
		this.editor  = matter.getEditor();
	}
	
	/**
	 * Returns matter id
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Sets matter id
	 * 
	 * @param id 
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Returns matter name
	 * 
	 * @return name 
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * SetS matter name
	 * 
	 * @param name 
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Returns matter creator
	 * 
	 * @return creator 
	 */
	public Administrator getCreator() 
	{
		return creator;
	}

	/**
	 * Sets matter creator
	 * 
	 * @param creator 
	 */
	public void setCreator(Administrator creator) 
	{
		this.creator = creator;
	}

	/**
	 * Returns matter editor
	 * 
	 * @return editor 
	 */
	public Administrator getEditor() 
	{
		return editor;
	}

	/**
	 * Sets matter editor
	 * 
	 * @param editor 
	 */
	public void setEditor(Administrator editor)
	{
		this.editor = editor;
	}
	
	/**
	 * Compares two matter id
	 * 
	 * @param matter2 
	 */
	@Override
	public int compareTo(Matter matter2) 
	{
        int compId = matter2.getId().compareTo(this.getId());
        
        return ((compId != 0) ? compId : 0);
	}
	
}
