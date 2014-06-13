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
	 * Retourne le numéro d'identification de la note
	 * 
	 * @return id Le numéro d'identification de la note.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification de la note
	 * 
	 * @param id Un numéro d'identification.
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
	 * Défini la note de la note
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
	 * @return etudiant L'étudiant de la note.
	 */
	public Etudiant getEtudiant() 
	{
		return etudiant;
	}
	
	/**
	 * Défini l'étudiant de la note
	 * 
	 * @param etudiant Un étudiant.
	 */
	public void setEtudiant(Etudiant etudiant) 
	{
		this.etudiant = etudiant;
	}
	
	/**
	 * Compare le nom des étudiants de deux notes
	 * 
	 * @param note Une note.
	 */
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
