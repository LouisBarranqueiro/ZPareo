package beans;

import beans.Etudiant;


public class Note 
{
	private Long id;
	private String note;
	private Etudiant etudiant;
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public String getNote()
	{
		return note;
	}
	
	public void setNote(String note) 
	{
		this.note = note;
	}
	
	public Etudiant getEtudiant() 
	{
		return etudiant;
	}
	
	public void setEtudiant(Etudiant etudiant) 
	{
		this.etudiant = etudiant;
	}
	
}
