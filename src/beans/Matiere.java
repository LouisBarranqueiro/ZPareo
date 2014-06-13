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
	 * Constructeur
	 * 
	 * @param matiere Une matiere.
	 */
	public Matiere(Matiere matiere)
	{
		this.setId(matiere.getId());
		this.setNom(matiere.getNom());
	}
	
	/**
	 * Retourne le numéro d'identification de la matière
	 * 
	 * @return id Le numéro d'identification de la matière.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification de la matière+
	 * 
	 * @param id Un numéro d'identification.
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne le nom de la matière
	 * 
	 * @return nom Le nom de la matière.
	 */
	public String getNom() 
	{
		return nom;
	}
	
	/**
	 * Défini le nom de la matière
	 * 
	 * @param nom Un nom.
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	/**
	 * Compare le numéro d'identification de deux matières
	 * 
	 * @param matiere2 Une matière.
	 */
	@Override
	public int compareTo(Matiere matiere2) 
	{
        int compId = this.getId().compareTo(matiere2.getId());
        if(compId != 0) return compId;
        return 0;
	}
	
}
