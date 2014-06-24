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
	 * @param note
	 */
	public Note(Note note)
	{
		this.id = note.getId();
		this.note = note.getNote();
		this.etudiant = note.getEtudiant();
	}
	
	/**
	 * Constructeur
	 * 
	 * @param etudiant
	 */
	public Note(Etudiant etudiant)
	{
		this.id = null;
		this.note = null;
		this.etudiant = etudiant;
	}
	
	/**
	 * Retourne le numéro d'identification de la note
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification de la note
	 * 
	 * @param id 
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne la note de la note
	 * 
	 * @return note 
	 */
	public Float getNote()
	{
		return note;
	}
	
	/**
	 * Défini la note de la note
	 * 
	 * @param note 
	 */
	public void setNote(Float note) 
	{
		this.note = note;
	}
	
	/**
	 * Retoune l'etudiant de la note
	 * 
	 * @return etudiant 
	 */
	public Etudiant getEtudiant() 
	{
		return etudiant;
	}
	
	/**
	 * Défini l'étudiant de la note
	 * 
	 * @param etudiant 
	 */
	public void setEtudiant(Etudiant etudiant) 
	{
		this.etudiant = etudiant;
	}
	
	/**
	 * Compare le nom des étudiants de deux notes
	 * 
	 * @param note 
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
