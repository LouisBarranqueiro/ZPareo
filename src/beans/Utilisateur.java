package beans;

public abstract class Utilisateur 
{
	private Long id;
	private String nom;
	private String prenom;
	private String adresseMail;
	private String motDePasse;
	private Administrateur createur;
	private Administrateur editeur;
	
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
		this.createur = null;
		this.editeur = null;
	}
	
	/**
	 * Constructeur 
	 * 
	 * @param utilisateur
	 */
	public Utilisateur(Utilisateur utilisateur)
	{
		this.id = utilisateur.getId();
		this.nom = utilisateur.getNom();
		this.prenom = utilisateur.getPrenom();
		this.adresseMail = utilisateur.getAdresseMail();
		this.motDePasse = utilisateur.getMotDePasse();
		this.createur = utilisateur.getCreateur();
		this.editeur = utilisateur.getEditeur();
	}
	
	/**
	 * Constructeur 
	 * 
	 * @param administrateur 
	 */
	public Utilisateur(Administrateur administrateur)
	{
		this.id = administrateur.getId();
		this.nom = administrateur.getNom();
		this.prenom = administrateur.getPrenom();
		this.adresseMail = administrateur.getAdresseMail();
		this.motDePasse = administrateur.getMotDePasse();
		this.createur = administrateur.getCreateur();
		this.editeur = administrateur.getEditeur();
	}
	
	/**
	 * Retourne le numéro d'identification de l'utilisateur
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification de l'utilisateur
	 * 
	 * @return id 
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne le nom de l'utilisateur
	 * 
	 * @return id
	 */
	public String getNom() 
	{
		return nom;
	}
	
	/**
	 * Défini le nom de l'utilisateur
	 * 
	 * @return id
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	/**
	 * Retourne le prenom de l'utilisateur
	 * 
	 * @return prenom
	 */
	public String getPrenom() 
	{
		return prenom;
	}
	
	/**
	 * Défini le prenom de l'utilisateur
	 * 
	 * @param prenom 
	 */
	public void setPrenom(String prenom) 
	{
		this.prenom = prenom;
	}
	
	/**
	 * Retourne l'adresse mail de l'utilisateur
	 * 
	 * @return adresseMail 
	 */
	public String getAdresseMail() 
	{
		return adresseMail;
	}
	 /**
	  * Défini l'adresse mail de l'utilisateur
	  * 
	  * @param adresseMail 
	  */
	public void setAdresseMail(String adresseMail)
	{
		this.adresseMail = adresseMail;
	}
	
	/**
	 * Retourne le mot de passe de l'utilisateur
	 * 
	 * @return motDePasse 
	 */
	public String getMotDePasse()
	{
		return motDePasse;
	}
	
	/**
	 * Défini le mot de passe de l'utilisateur
	 * 
	 * @param motDePasse 
	 */
	public void setMotDePasse(String motDePasse) 
	{
		this.motDePasse = motDePasse;
	}

	/**
	 * Retourne le créateur de l'utilisateur
	 * 
	 * @return createur 
	 */
	public Administrateur getCreateur() 
	{
		return createur;
	}

	/**
	 * Défini le créateur de l'utilisateur
	 * 
	 * @param createur 
	 */
	public void setCreateur(Administrateur createur) 
	{
		this.createur = createur;
	}

	/**
	 * Retourne l'éditeur de l'utilisateur
	 * 
	 * @return editeur 
	 */
	public Administrateur getEditeur() 
	{
		return editeur;
	}

	/**
	 * Retourne l'éditeur de l'utilisateur
	 * 
	 * param editeur
	 */
	public void setEditeur(Administrateur editeur) 
	{
		this.editeur = editeur;
	}
	
}
