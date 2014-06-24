package beans;

import java.util.Set;

public class Bulletin
{
	private Long id;
	private Float moyenne;
	private Set<MatiereNote> listeMatiereNote;
	
	/**
	 * Constructeur
	 */
	public Bulletin()
	{
		this.id = null;
		this.moyenne = null;
		this.listeMatiereNote = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param bulletin 
	 */
	public Bulletin(Bulletin bulletin)
	{
		this.id = bulletin.getId();
		this.moyenne = bulletin.getMoyenne();
		this.listeMatiereNote = bulletin.getListeMatiereNote();
	}
	
	/**
	 * Retourne le numéro d'identification du bulletin
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification du bulletin
	 * 
	 * @param id 
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne la moyenne générale du bulletin
	 * 
	 * @return moyenne 
	 */
	public Float getMoyenne() 
	{
		return moyenne;
	}
	/**
	 * Retourne la moyenne générale du bulletin
	 * 
	 * @param moyenne 
	 */
	public void setMoyenne(Float moyenne) 
	{
		this.moyenne = moyenne;
	}
	
	/**
	 * Retourne la liste des résultats des matieres du bulletin
	 * 
	 * @return listeMatiereNotes 
	 */
	public Set<MatiereNote> getListeMatiereNote()
	{
		return listeMatiereNote;
	}
	
	/**
	 * Défini la liste des résultats des matières du bulletin
	 * 
	 * @param listeMatiereNote
	 */
	public void setListeMatiereNote(Set<MatiereNote> listeMatiereNote)
	{
		this.listeMatiereNote = listeMatiereNote;
	}
}
