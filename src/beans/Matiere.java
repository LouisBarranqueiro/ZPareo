package beans;

public class Matiere implements Comparable<Matiere>
{
	private Long id;
	private String nom;
	
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
