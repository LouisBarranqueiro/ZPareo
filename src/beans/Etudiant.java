package beans;

import java.util.Set;

import beans.Groupe;
import beans.Note;
import beans.Utilisateur;

public class Etudiant extends Utilisateur implements Comparable<Etudiant>
{
	private Groupe groupe;
	private Set<Note> listeNotes;

	public Etudiant()
	{
		super();
		this.groupe = null;
		this.listeNotes = null;
	}
	
	public Etudiant(Etudiant etudiant)
	{
		super(etudiant);
		this.setGroupe(etudiant.getGroupe());
		this.setListeNote(listeNotes);
	}
	
	public Groupe getGroupe() 
	{
		return groupe;
	}
	
	public void setGroupe(Groupe groupe) 
	{
		this.groupe = groupe;
	}
	
	public Set<Note> getListeNote() 
	{
		return listeNotes;
	}
	
	public void setListeNote(Set<Note> listeNote) 
	{
		this.listeNotes = listeNote;
	}
	
	@Override
	public int compareTo(Etudiant etudiant2) 
	{
		int compId = this.getId().compareTo(etudiant2.getId());
        if(compId != 0) return compId;
        return 0;
	}
	
}