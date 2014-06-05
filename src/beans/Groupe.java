package beans;

public class Groupe implements Comparable<Groupe>
{
	private Long id;
	private String nom;
	
	public Groupe() 
	{
		this.id = null;
		this.nom = null;
	}
	
	public Groupe(Groupe groupe) 
	{
		this.setId(groupe.getId());
		this.setNom(groupe.getNom());
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
	public int compareTo(Groupe groupe2) 
	{
		int compId = this.getId().compareTo(groupe2.getId());
        if(compId != 0) return compId;
        return 0;
	}
}