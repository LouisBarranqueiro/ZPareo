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
	 * @param utilisateur Un utilisateur.
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
	 * @param administrateur Un administrateur.
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
	 * @return id Le numéro d'identification.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification de l'utilisateur
	 * 
	 * @return id Un numéro d'identification.
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
	 * Défini le nom de l'utilisateur
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
	 * Défini le prenom de l'utilisateur
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
	  * Défini l'adresse mail de l'utilisateur
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
	 * Défini le mot de passe de l'utilisateur
	 * 
	 * @param motDePasse Un mot de passe.
	 */
	public void setMotDePasse(String motDePasse) 
	{
		this.motDePasse = motDePasse;
	}

	/**
	 * Retourne le créateur de l'utilisateur
	 * 
	 * @return createur Le créateur de l'utilisateur.
	 */
	public Administrateur getCreateur() 
	{
		return createur;
	}

	/**
	 * Défini le créateur de l'utilisateur
	 * 
	 * @param createur Un utilisateur.
	 */
	public void setCreateur(Administrateur createur) 
	{
		this.createur = createur;
	}

	/**
	 * Retourne l'éditeur de l'utilisateur
	 * 
	 * @return editeur L'éditeur de l'utilisateur.
	 */
	public Administrateur getEditeur() 
	{
		return editeur;
	}

	/**
	 * Retourne l'éditeur de l'utilisateur
	 * 
	 * param editeur Un utilisateur.
	 */
	public void setEditeur(Administrateur editeur) 
	{
		this.editeur = editeur;
	}
	
}
