package models;

import java.util.Set;

public class Examen implements Comparable<Examen>
{
	private Long id;
	private String nom;
	private Groupe groupe;
	private Matiere matiere;
	private Set<Note> listeNotes;
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public String getNom()
	{
		return nom;
	}
	
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	public Groupe getGroupe()
	{
		return groupe;
	}
	
	public void setGroupe(Groupe groupe)
	{
		this.groupe = groupe;
	}
	
	public Matiere getMatiere()
	{
		return matiere;
	}
	
	public void setMatiere(Matiere matiere)
	{
		this.matiere = matiere;
	}
	
	public Set<Note> getListeNotes()
	{
		return listeNotes;
	}
	
	public void setListeNotes(Set<Note> listeNotes) 
	{
		this.listeNotes = listeNotes;
	}

	@Override
	public int compareTo(Examen examen2) 
	{
		int compId = this.getId().compareTo(examen2.getId());
        if(compId != 0) return compId;
        return 0;
	}
}
