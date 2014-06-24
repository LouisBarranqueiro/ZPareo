package beans;

public class FormatExamen
{
	private Long id;
	private String nom;
	
	/**
	 * Constructeur
	 */
	public FormatExamen() 
	{
		this.id = null;
		this.nom = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param format 
	 */
	public FormatExamen(FormatExamen format) 
	{
		this.id = format.getId();
		this.nom = format.getNom();
	}
	
	/**
	 * Retourne le numéro d'identification du format d'examen
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification du format d'examen
	 * 
	 * @param id 
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 *  Retourne le nom du format d'examen
	 *  
	 * @return nom 
	 */
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Défini le nom du format d'examen
	 * 
	 * @param nom 
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
 
}
