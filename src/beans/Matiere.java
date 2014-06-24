package beans;

public class Matiere implements Comparable<Matiere>
{
	private Long id;
	private String nom;
	private Administrateur createur;
	private Administrateur editeur;
	
	/**
	 * Consutructeur
	 */
	public Matiere()
	{
		this.id = null;
		this.nom = null;
		this.createur = null;
		this.editeur = null;
	}
	
	/**
	 * Consutructeur
	 */
	public Matiere(Long id)
	{
		this.id = id;
		this.nom = null;
		this.createur = null;
		this.editeur = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param matiere 
	 */
	public Matiere(Matiere matiere)
	{
		this.id = matiere.getId();
		this.nom = matiere.getNom();
		this.createur = matiere.getCreateur();
		this.editeur = matiere.getEditeur();
	}
	
	/**
	 * Retourne le numéro d'identification de la matiére
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification de la matiére+
	 * 
	 * @param id 
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne le nom de la matiére
	 * 
	 * @return nom 
	 */
	public String getNom() 
	{
		return nom;
	}
	
	/**
	 * Défini le nom de la matiére
	 * 
	 * @param nom 
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	/**
	 * Retourne le créateur de la matière
	 * 
	 * @return createur 
	 */
	public Administrateur getCreateur() 
	{
		return createur;
	}

	/**
	 * Défini le créateur de la matière
	 * 
	 * @param createur 
	 */
	public void setCreateur(Administrateur createur) 
	{
		this.createur = createur;
	}

	/**
	 * Retourne l'éditeur de la matière
	 * 
	 * @return editeur 
	 */
	public Administrateur getEditeur() 
	{
		return editeur;
	}

	/**
	 * Défini l'éditeur de la matière
	 * 
	 * @param editeur 
	 */
	public void setEditeur(Administrateur editeur)
	{
		this.editeur = editeur;
	}
	
	/**
	 * Compare le numéro d'identification de deux matiéres
	 * 
	 * @param matiere2 
	 */
	@Override
	public int compareTo(Matiere matiere2) 
	{
        int compId = this.getId().compareTo(matiere2.getId());
        
        return ((compId != 0) ? compId : 0);
	}
	
}
