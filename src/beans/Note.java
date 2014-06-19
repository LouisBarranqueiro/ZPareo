package beans;

public class Note implements Comparable<Note>
{
	private Long id;
	private Float note;
	private Etudiant etudiant;
	
	/**
	 * Constructeur
	 */
	public Note()
	{
		this.id = null;
		this.note = null;
		this.etudiant = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param note Une note.
	 */
	public Note(Note note)
	{
		this.setId(note.getId());
		this.setNote(note.getNote());
		this.setEtudiant(note.getEtudiant());
	}
	
	/**
	 * Constructeur
	 * 
	 * @param etudiant Un etudiant.
	 */
	public Note(Etudiant etudiant)
	{
		this.id = null;
		this.note = null;
		this.setEtudiant(etudiant);
	}
	
	/**
	 * Retourne le num�ro d'identification de la note
	 * 
	 * @return id Le num�ro d'identification de la note.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * D�fini le num�ro d'identification de la note
	 * 
	 * @param id Un num�ro d'identification.
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne la note de la note
	 * 
	 * @return note La note de la note.
	 */
	public Float getNote()
	{
		return note;
	}
	
	/**
	 * D�fini la note de la note
	 * 
	 * @param note Une note.
	 */
	public void setNote(Float note) 
	{
		this.note = note;
	}
	
	/**
	 * Retoune l'etudiant de la note
	 * 
	 * @return etudiant L'�tudiant de la note.
	 */
	public Etudiant getEtudiant() 
	{
		return etudiant;
	}
	
	/**
	 * D�fini l'�tudiant de la note
	 * 
	 * @param etudiant Un �tudiant.
	 */
	public void setEtudiant(Etudiant etudiant) 
	{
		this.etudiant = etudiant;
	}
	
	/**
	 * Compare le nom des �tudiants de deux notes
	 * 
	 * @param note Une note.
	 */
	@Override
	public int compareTo(Note note) 
	{
		Etudiant etudiant1 = new Etudiant(this.getEtudiant());
		Etudiant etudiant2 = new Etudiant(note.getEtudiant());
        int compId = etudiant1.getNom().compareTo(etudiant2.getNom());
        
        return ((compId != 0) ? compId : 0);
	}
}
