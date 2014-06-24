package beans;

public class Etudiant extends Utilisateur implements Comparable<Etudiant>
{
	private Groupe groupe;
	private Bulletin bulletin;

	/**
	 * Constructeur
	 */
	public Etudiant()
	{
		super();
		this.groupe = null;
		this.bulletin = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param etudiant 
	 */
	public Etudiant(Etudiant etudiant)
	{
		super(etudiant);
		this.groupe = etudiant.getGroupe();
		this.bulletin = etudiant.getBulletin();
	}
	
	/**
	 * Retourne le groupe de l'étudiant
	 * 
	 * @return groupe 
	 */
	public Groupe getGroupe() 
	{
		return groupe;
	}
	
	/**
	 * Défini le groupe de l'étudiant
	 * 
	 * @param groupe 
	 */
	public void setGroupe(Groupe groupe) 
	{
		this.groupe = groupe;
	}

	/**
	 * Retourne la liste des notes de l'etudiant
	 * 
	 * @return bulletin 
	 */
	public Bulletin getBulletin() 
	{
		return bulletin;
	}
	
	/**
	 * Défini le bulletin de l'étudiant
	 * 
	 * @param bulletin
	 */
	public void setBulletin(Bulletin bulletin) 
	{
		this.bulletin = bulletin;
	}
	
	/**
	 * Compare le numéro d'identification de deux étudiants
	 * 
	 * @param etudiant2 
	 */
	@Override
	public int compareTo(Etudiant etudiant2) 
	{
		int compId = this.getId().compareTo(etudiant2.getId());
        
		return ((compId != 0) ? compId : 0);
	}
	
}