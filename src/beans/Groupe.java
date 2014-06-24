package beans;

public class Groupe implements Comparable<Groupe>
{
	private Long id;
	private String nom;
	private Administrateur createur;
	private Administrateur editeur;
	
	/**
	 * Constructeur
	 */
	public Groupe() 
	{
		this.id = null;
		this.nom = null;
		this.createur = null;
		this.editeur = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param groupe 
	 */
	public Groupe(Groupe groupe) 
	{
		this.id = groupe.getId();
		this.nom = groupe.getNom();
		this.createur = groupe.getCreateur();
		this.createur = groupe.getEditeur();
	}
	
	/**
	 * Retourne le numéro d'identification du groupe
	 * 
	 * @return id 
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification du groupe
	 * 
	 * @param id 
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne le nom du groupe
	 * 
	 * @return nom 
	 */
	public String getNom() 
	{
		return nom;
	}
	
	/**
	 * Défini le nom du groupe
	 * 
	 * @param nom 
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	/**
	 * Retourne le créateur du groupe
	 * 
	 * @return createur Le créateur du groupe.
	 */
	public Administrateur getCreateur() 
	{
		return createur;
	}

	/**
	 * Défini le créateur du groupe
	 * 
	 * @param createur 
	 */
	public void setCreateur(Administrateur createur) 
	{
		this.createur = createur;
	}

	/**
	 * Retourne l'éditeur du groupe
	 * 
	 * @return editeur 
	 */
	public Administrateur getEditeur() 
	{
		return editeur;
	}

	/**
	 * Défini l'éditeur du groupe
	 * 
	 * @param editeur 
	 */
	public void setEditeur(Administrateur editeur)
	{
		this.editeur = editeur;
	}
	
	/**
	 * Compare le numéro d'identification de deux groupes
	 * 
	 * @param groupe2 
	 */
	@Override
	public int compareTo(Groupe groupe2) 
	{
		int compId = this.getId().compareTo(groupe2.getId());
        
		return ((compId != 0) ? compId : 0);
	}
}