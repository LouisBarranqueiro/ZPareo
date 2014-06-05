package beans;

public class Matiere implements Comparable<Matiere>
{
	private Long id;
	private String nom;
	
	public Matiere()
	{
		this.id = null;
		this.nom = null;
	}
	public Matiere(Matiere matiere)
	{
		this.setId(matiere.getId());
		this.setNom(matiere.getNom());
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
	
	@Override
	public int compareTo(Matiere matiere2) 
	{
        int compId = this.getId().compareTo(matiere2.getId());
        if(compId != 0) return compId;
        return 0;
	}
	
}
