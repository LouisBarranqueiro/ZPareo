package models;

import java.sql.Timestamp;

public class Matiere implements Comparable<Matiere>
{
	private Long id;
	private String nom;
	private Timestamp dateAjout;
	private Timestamp dateEdition;
	private Timestamp dateSuppression;
	private Professeur Editeur;
	
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
	
	public Timestamp getDateAjout() 
	{
		return dateAjout;
	}
	
	public void setDateAjout(Timestamp dateAjout) 
	{
		this.dateAjout = dateAjout;
	}
	
	public Timestamp getDateEdition() 
	{
		return dateEdition;
	}
	
	public void setDateEdition(Timestamp dateEdition) 
	{
		this.dateEdition = dateEdition;
	}
	
	public Timestamp getDateSuppression() 
	{
		return dateSuppression;
	}
	
	public void setDateSuppression(Timestamp dateSuppression)
	{
		this.dateSuppression = dateSuppression;
	}
	
	public Professeur getEditeur() 
	{
		return Editeur;
	}
	
	public void setEditeur(Professeur editeur) 
	{
		Editeur = editeur;
	}

	@Override
	public int compareTo(Matiere matiere2) 
	{
        int compId = this.getId().compareTo(matiere2.getId());
        if(compId != 0) return compId;
        return 0;
	}
	
}
