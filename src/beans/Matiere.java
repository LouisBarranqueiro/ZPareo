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
	}
	
	/**
	 * Consutructeur
	 */
	public Matiere(Long id)
	{
		this.id = id;
		this.nom = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param matiere Une matiere.
	 */
	public Matiere(Matiere matiere)
	{
		this.id = matiere.getId();
		this.nom = matiere.getNom();
	}
	
	/**
	 * Retourne le numéro d'identification de la matiére
	 * 
	 * @return id Le numéro d'identification de la matiére.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification de la matiére+
	 * 
	 * @param id Un numéro d'identification.
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne le nom de la matiére
	 * 
	 * @return nom Le nom de la matiére.
	 */
	public String getNom() 
	{
		return nom;
	}
	
	/**
	 * Défini le nom de la matiére
	 * 
	 * @param nom Un nom.
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	/**
	 * Retourne le créateur de la matière
	 * 
	 * @return createur Le créateur de la matière.
	 */
	public Administrateur getCreateur() 
	{
		return createur;
	}

	/**
	 * Défini le créateur de la matière
	 * 
	 * @param createur Un administrateur.
	 */
	public void setCreateur(Administrateur createur) 
	{
		this.createur = createur;
	}

	/**
	 * Retourne l'éditeur de la matière
	 * 
	 * @return editeur L'éditeur de la matière.
	 */
	public Administrateur getEditeur() 
	{
		return editeur;
	}

	/**
	 * Défini l'éditeur de la matière
	 * 
	 * @param editeur Un administrateur.
	 */
	public void setEditeur(Administrateur editeur)
	{
		this.editeur = editeur;
	}
	
	/**
	 * Compare le numéro d'identification de deux matiéres
	 * 
	 * @param matiere2 Une matiére.
	 */
	@Override
	public int compareTo(Matiere matiere2) 
	{
        int compId = this.getId().compareTo(matiere2.getId());
        
        return ((compId != 0) ? compId : 0);
	}
	
}
