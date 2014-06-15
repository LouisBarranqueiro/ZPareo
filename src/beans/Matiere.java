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
	 * Retourne le num�ro d'identification de la mati�re
	 * 
	 * @return id Le num�ro d'identification de la mati�re.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * D�fini le num�ro d'identification de la mati�re+
	 * 
	 * @param id Un num�ro d'identification.
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne le nom de la mati�re
	 * 
	 * @return nom Le nom de la mati�re.
	 */
	public String getNom() 
	{
		return nom;
	}
	
	/**
	 * D�fini le nom de la mati�re
	 * 
	 * @param nom Un nom.
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	/**
	 * Compare le num�ro d'identification de deux mati�res
	 * 
	 * @param matiere2 Une mati�re.
	 */
	@Override
	public int compareTo(Matiere matiere2) 
	{
        int compId = this.getId().compareTo(matiere2.getId());
        
        return ((compId != 0) ? compId : 0);
	}
	
}
