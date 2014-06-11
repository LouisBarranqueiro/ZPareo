package beans;

public class FormatExamen
{
	private Long id;
	private String nom;
	
	public FormatExamen() 
	{
		this.id = null;
		this.nom = null;
	}
	
	public FormatExamen(FormatExamen format) 
	{
		this.setId(format.getId());
		this.setNom(format.getNom());
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
	
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
 
}
