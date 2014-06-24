package beans;

import java.util.Set;

public class Examen implements Comparable<Examen>
{
	private Long id;
	private String nom;
	private String date;
	private FormatExamen format;
	private Professeur professeur;
	private Groupe groupe;
	private Matiere matiere;
	private Set<Note> listeNotes;
	private Float moyenne;
	private Float coefficient;
	
	/**
	 * Constructeur
	 */
	public Examen()
	{
		this.id = null;
		this.nom = null;
		this.date = null;
		this.format = null;
		this.professeur = null;
		this.groupe = null;
		this.matiere = null;
		this.listeNotes = null;
		this.moyenne = null;
		this.coefficient = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param examen 
	 */
	public Examen(Examen examen)
	{
		this.id = examen.getId();
		this.nom = examen.getNom();
		this.date = examen.getDate();
		this.format = examen.getFormat();
		this.professeur = examen.getProfesseur();
		this.groupe = examen.getGroupe();
		this.matiere = examen.getMatiere();
		this.listeNotes = examen.getListeNotes();
		this.moyenne = examen.getMoyenne();
		this.coefficient = examen.getCoefficient();
	}
	
	/**
	 * Retourne le numéro d'identification de l'examen
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification de l'examen
	 * 
	 * @param id 
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}

	/**
	 * Retourne le nom de l'examen
	 * 
	 * @return nom 
	 */
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Défini le nom de l'examen
	 * 
	 * @param nom
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	/**
	 * Retourne la date de l'examen
	 * 
	 * @return date
	 */
	public String getDate() 
	{
		return date;
	}

	/**
	 * Défini la date de l'examen
	 * 
	 * @param date 
	 */
	public void setDate(String date) 
	{
		this.date = date;
	}
	
	/**
	 * Retourne le format de l'examen
	 * 
	 * @return format 
	 */
	public FormatExamen getFormat() 
	{
		return format;
	}
	
	/**
	 * Défini le format de l'examen
	 * 
	 * @param format 
	 */
	public void setFormat(FormatExamen format) 
	{
		this.format = format;
	}

	/**
	 * Retourne le professeur responsable de l'examen
	 * 
	 * @return professeur 
	 */
	public Professeur getProfesseur()
	{
		return professeur;
	}
	
	/**
	 * Défini le professeur responsable de l'examen
	 * 
	 * @param professeur 
	 */
	public void setProfesseur(Professeur professeur) 
	{
		this.professeur = professeur;
	}
	
	/**
	 * Retourne le groupe de l'examen
	 * 
	 * @return groupe 
	 */
	public Groupe getGroupe()
	{
		return groupe;
	}
	
	/**
	 * Défini le groupe de l'examen
	 * 
	 * @param groupe 
	 */
	public void setGroupe(Groupe groupe)
	{
		this.groupe = groupe;
	}
	
	/**
	 * Retourne la matiére de l'examen
	 * 
	 * @return matiere 
	 */
	public Matiere getMatiere()
	{
		return matiere;
	}
	
	/**
	 * Défini la matiére de l'examen
	 * 
	 * @param matiere 
	 */
	public void setMatiere(Matiere matiere)
	{
		this.matiere = matiere;
	}
	
	/**
	 * Retourne la liste de notes de l'examen
	 * 
	 * @return listeNotes 
	 */
	public Set<Note> getListeNotes()
	{
		return listeNotes;
	}
	
	/**
	 * Défini la liste de notes de l'examen
	 * 
	 * @param listeNotes 
	 */
	public void setListeNotes(Set<Note> listeNotes) 
	{
		this.listeNotes = listeNotes;
	}
	
	/**
	 * Retourne la moyenne générale de l'examen
	 * 
	 * @return moyenneGenerale 
	 */
	public Float getMoyenne() {
		return moyenne;
	}

	/**
	 * Défini la moyenne générale de l'examen
	 * 
	 * @param moyenneGenerale 
	 */
	public void setMoyenne(Float moyenne) 
	{
		this.moyenne = moyenne;
	}

	/**
	 * Retourne le coefficient de l'examen
	 * 
	 * @return coefficient 
	 */
	public Float getCoefficient()
	{
		return coefficient;
	}
	
	/**
	 * Défini le coefficient de l'examen
	 * 
	 * @param coefficient 
	 */
	public void setCoefficient(Float coefficient) 
	{
		this.coefficient = coefficient;
	}
	
	/**
	 * Compare le numéro d'identification de deux examens
	 * 
	 * @param examen2 
	 */
	@Override
	public int compareTo(Examen examen2) 
	{
		int compId = this.getId().compareTo(examen2.getId());
        
		return ((compId != 0) ? compId : 0);
	}
	
}
