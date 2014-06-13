package beans;

public class Groupe implements Comparable<Groupe>
{
	private Long id;
	private String nom;
	
	/**
	 * Constructeur
	 */
	public Groupe() 
	{
		this.id = null;
		this.nom = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param groupe un groupe.
	 */
	public Groupe(Groupe groupe) 
	{
		this.setId(groupe.getId());
		this.setNom(groupe.getNom());
	}
	
	/**
	 * Retourne le numéro d'identification du groupe
	 * 
	 * @return id Le numéro d'identification du groupe.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification du groupe
	 * 
	 * @param id Un numéro d'identification.
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * Retourne le nom du groupe
	 * 
	 * @return nom Le nom du groupe.
	 */
	public String getNom() 
	{
		return nom;
	}
	
	/**
	 * Défini le nom du groupe
	 * 
	 * @param nom Un nom.
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	/**
	 * Compare le numéro d'identification de deux groupes
	 * 
	 * @param groupe2 Un groupe.
	 */
	@Override
	public int compareTo(Groupe groupe2) 
	{
		int compId = this.getId().compareTo(groupe2.getId());
        if(compId != 0) return compId;
        return 0;
	}
}