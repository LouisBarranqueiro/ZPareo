package beans;

public abstract class Utilisateur 
{
	private Long id;
	private String nom;
	private String prenom;
	private String adresseMail;
	private String motDePasse;
	
	public Utilisateur()
	{
		this.id = null;
		this.nom = null;
		this.prenom = null;
		this.adresseMail = null;
		this.motDePasse = null;
	}
	
	public Utilisateur(Utilisateur utilisateur)
	{
		this.setId(utilisateur.getId());
		this.setNom(utilisateur.getNom());
		this.setPrenom(utilisateur.getPrenom());
		this.setAdresseMail(utilisateur.getAdresseMail());
		this.setMotDePasse(utilisateur.getMotDePasse());
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
	
	public String getPrenom() 
	{
		return prenom;
	}
	
	public void setPrenom(String prenom) 
	{
		this.prenom = prenom;
	}
	
	public String getAdresseMail() 
	{
		return adresseMail;
	}
	
	public void setAdresseMail(String adresseMail)
	{
		this.adresseMail = adresseMail;
	}
	
	public String getMotDePasse()
	{
		return motDePasse;
	}
	
	public void setMotDePasse(String motDePasse) 
	{
		this.motDePasse = motDePasse;
	}
	
}
