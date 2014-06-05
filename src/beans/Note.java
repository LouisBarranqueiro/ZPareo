package beans;

import beans.Etudiant;

public class Note implements Comparable<Note>
{
	private Long id;
	private Float note;
	private Etudiant etudiant;
	
	public Note()
	{
		this.id = null;
		this.note = null;
		this.etudiant = null;
	}
	
	public Note(Note note)
	{
		this.setId(note.getId());
		this.setNote(note.getNote());
		this.setEtudiant(note.getEtudiant());
	}
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public Float getNote()
	{
		return note;
	}
	
	public void setNote(Float note) 
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
	
	@Override
	public int compareTo(Note note) 
	{
		Etudiant etudiant1 = new Etudiant(this.getEtudiant());
		Etudiant etudiant2 = new Etudiant(note.getEtudiant());
        int compId = etudiant1.getNom().compareTo(etudiant2.getNom());
        if(compId != 0) return compId;
        return 0;
	}
}
