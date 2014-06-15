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
	 * @param etudiant Un �tudiant.
	 */
	public Etudiant(Etudiant etudiant)
	{
		super(etudiant);
		this.setGroupe(etudiant.getGroupe());
		this.setListeNote(listeNotes);
	}
	
	/**
	 * Retourne le groupe de l'�tudiant
	 * 
	 * @return groupe Le groupe de l'�tudiant.
	 */
	public Groupe getGroupe() 
	{
		return groupe;
	}
	
	/**
	 * D�fini le groupe de l'�tudiant
	 * 
	 * @param groupe Un groupe.
	 */
	public void setGroupe(Groupe groupe) 
	{
		this.groupe = groupe;
	}
	
	/**
	 * Retourne la liste des notes de l'�tudiant
	 * 
	 * @return listeNotes La liste des notes de l'�tudiant.
	 */
	public Set<Note> getListeNote() 
	{
		return listeNotes;
	}
	
	/**
	 * D�fini la liste des notes de l'�tudiant
	 * 
	 * @param listeNote Un liste de notes.
	 */
	public void setListeNote(Set<Note> listeNote) 
	{
		this.listeNotes = listeNote;
	}
	
	/**
	 * Compare le num�ro d'identification de deux �tudiants
	 * 
	 * @param etudiant2 Un �tudiant.
	 */
	@Override
	public int compareTo(Etudiant etudiant2) 
	{
		int compId = this.getId().compareTo(etudiant2.getId());
        
		return ((compId != 0) ? compId : 0);
	}
	
}