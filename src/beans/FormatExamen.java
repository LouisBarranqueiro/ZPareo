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
	 * @param format Un format d'examen.
	 */
	public FormatExamen(FormatExamen format) 
	{
		this.setId(format.getId());
		this.setNom(format.getNom());
	}
	
	/**
	 * Retourne le numéro d'identification du format d'examen
	 * 
	 * @return id Le numéro d'identification du format d'examen.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification du format d'examen
	 * 
	 * @param id Un numéro d'identification.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 *  Retourne le nom du format d'examen
	 *  
	 * @return nom Le nom du format d'examen.
	 */
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Défini le nom du format d'examen
	 * 
	 * @param nom Un nom.
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
 
}
