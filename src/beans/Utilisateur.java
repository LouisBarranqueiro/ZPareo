package beans;

public abstract class Utilisateur 
{
	private Long id;
	private String nom;
	private String prenom;
	private String adresseMail;
	private String motDePasse;
	
	/**
	 * Constructeur
	 */
	public Utilisateur()
	{
		this.id = null;
		this.nom = null;
		this.prenom = null;
		this.adresseMail = null;
		this.motDePasse = null;
	}
	
	/**
	 * Constrcuteur 
	 * 
	 * @param utilisateur Un utilisateur.
	 */
	public Utilisateur(Utilisateur utilisateur)
	{
		this.setId(utilisateur.getId());
		this.setNom(utilisateur.getNom());
		this.setPrenom(utilisateur.getPrenom());
		this.setAdresseMail(utilisateur.getAdresseMail());
		this.setMotDePasse(utilisateur.getMotDePasse());
	}
	
	/**
	 * Retourne le numro d'identification de l'utilisateur
	 * 
	 * @return id Le numro d'identification.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Dfini le numro d'identification de l'utilisateur
	 * 
	 * @return id Un numro d'identification.
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne le nom de l'utilisateur
	 * 
	 * @return id Le nom de l'utilisateur.
	 */
	public String getNom() 
	{
		return nom;
	}
	
	/**
	 * Dfini le nom de l'utilisateur
	 * 
	 * @return id Un nom.
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	/**
	 * Retourne le prenom de l'utilisateur
	 * 
	 * @return prenom Le prenom de l'utilisateur.
	 */
	public String getPrenom() 
	{
		return prenom;
	}
	
	/**
	 * Dfini le prenom de l'utilisateur
	 * 
	 * @param prenom Un prenom.
	 */
	public void setPrenom(String prenom) 
	{
		this.prenom = prenom;
	}
	
	/**
	 * Retourne l'adresse mail de l'utilisateur
	 * 
	 * @return adresseMail Une adresse mail.
	 */
	public String getAdresseMail() 
	{
		return adresseMail;
	}
	 /**
	  * Dfini l'adresse mail de l'utilisateur
	  * 
	  * @param adresseMail Une adresse mail.
	  */
	public void setAdresseMail(String adresseMail)
	{
		this.adresseMail = adresseMail;
	}
	
	/**
	 * Retourne le mot de passe de l'utilisateur
	 * 
	 * @return motDePasse Le mot de passe de l'utilisateur.
	 */
	public String getMotDePasse()
	{
		return motDePasse;
	}
	
	/**
	 * Dfini le mot de passe de l'utilisateur
	 * 
	 * @param motDePasse Un mot de passe.
	 */
	public void setMotDePasse(String motDePasse) 
	{
		this.motDePasse = motDePasse;
	}
	
}
