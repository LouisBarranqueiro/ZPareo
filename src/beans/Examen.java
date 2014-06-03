package beans;

import java.util.Date;
import java.util.Set;

public class Examen implements Comparable<Examen>
{
	private Long id;
	private String nom;
	private String date;
	private String format;
	private Professeur professeur;
	private Groupe groupe;
	private Matiere matiere;
	private Set<Note> listeNotes;
	private Float moyenneGenerale;
	
	public Examen()
	{
		
	}
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
	
	public String getDate() 
	{
		return date;
	}

	public void setDate(String date) 
	{
		this.date = date;
	}
	
	public String getFormat() 
	{
		return format;
	}
	
	public void setFormat(String format) 
	{
		this.format = format;
	}
	
	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public Professeur getProfesseur()
	{
		return professeur;
	}
	
	public void setProfesseur(Professeur professeur) 
	{
		this.professeur = professeur;
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
	
	public Float getMoyenneGenerale() {
		return moyenneGenerale;
	}

	public void setMoyenneGenerale(Float moyenneGenerale) 
	{
		this.moyenneGenerale = moyenneGenerale;
	}
	
	@Override
	public int compareTo(Examen examen2) 
	{
		int compId = this.getId().compareTo(examen2.getId());
        if(compId != 0) return compId;
        return 0;
	}
	
}
