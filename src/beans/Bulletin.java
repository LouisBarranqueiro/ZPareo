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
	 * @param bulletin Une bulletin.
	 */
	public Bulletin(Bulletin bulletin)
	{
		this.setId(bulletin.getId());
		this.setMoyenne(bulletin.getMoyenne());
		this.setListeMatiereNote(bulletin.getListeMatiereNote());
	}
	
	/**
	 * Retourne le numéro d'identification du bulletin
	 * 
	 * @return id Le numéro d'identification du bulletin.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification du bulletin
	 * 
	 * @param id Un numéro d'identification.
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne la moyenne générale du bulletin
	 * 
	 * @return moyenne La moyenne générale du bulletin.
	 */
	public Float getMoyenne() 
	{
		return moyenne;
	}
	/**
	 * Retourne la moyenne générale du bulletin
	 * 
	 * @param moyenne Une moyenne.
	 */
	public void setMoyenne(Float moyenne) 
	{
		this.moyenne = moyenne;
	}
	
	/**
	 * Retourne la liste des résultats des matieres du bulletin
	 * 
	 * @return listeMatiereNotes La liste des résultats des matières du bulletin.
	 */
	public Set<MatiereNote> getListeMatiereNote()
	{
		return listeMatiereNote;
	}
	
	/**
	 * Défini la liste des résultats des matières du bulletin
	 * 
	 * @param listeMatiereNote Une liste de MatiereNotes.
	 */
	public void setListeMatiereNote(Set<MatiereNote> listeMatiereNote)
	{
		this.listeMatiereNote = listeMatiereNote;
	}
}
