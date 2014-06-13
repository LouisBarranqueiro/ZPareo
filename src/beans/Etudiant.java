package beans;

import java.util.Set;

public class Etudiant extends Utilisateur implements Comparable<Etudiant>
{
	private Groupe groupe;
	private Set<Note> listeNotes;

	/**
	 * Constructeur
	 */
	public Etudiant()
	{
		super();
		this.groupe = null;
		this.listeNotes = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param etudiant Un étudiant.
	 */
	public Etudiant(Etudiant etudiant)
	{
		super(etudiant);
		this.setGroupe(etudiant.getGroupe());
		this.setListeNote(listeNotes);
	}
	
	/**
	 * Retourne le groupe de l'étudiant
	 * 
	 * @return groupe Le groupe de l'étudiant.
	 */
	public Groupe getGroupe() 
	{
		return groupe;
	}
	
	/**
	 * Défini le groupe de l'étudiant
	 * 
	 * @param groupe Un groupe.
	 */
	public void setGroupe(Groupe groupe) 
	{
		this.groupe = groupe;
	}
	
	/**
	 * Retourne la liste des notes de l'étudiant
	 * 
	 * @return listeNotes La liste des notes de l'étudiant.
	 */
	public Set<Note> getListeNote() 
	{
		return listeNotes;
	}
	
	/**
	 * Défini la liste des notes de l'étudiant
	 * 
	 * @param listeNote Un liste de notes.
	 */
	public void setListeNote(Set<Note> listeNote) 
	{
		this.listeNotes = listeNote;
	}
	
	/**
	 * Compare le numéro d'identification de deux étudiants
	 * 
	 * @param etudiant2 Un étudiant.
	 */
	@Override
	public int compareTo(Etudiant etudiant2) 
	{
		int compId = this.getId().compareTo(etudiant2.getId());
        if(compId != 0) return compId;
        return 0;
	}
	
}