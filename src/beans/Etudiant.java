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
	 * @param etudiant Un étudiant.
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
	 * @return groupe Le groupe de l'étudiant.
	 */
	public Groupe getGroupe() 
	{
		return groupe;
	}
	
	/**
	 * Défini le groupe de l'étudiant
	 * 
	 * @param groupe Un groupe.
	 */
	public void setGroupe(Groupe groupe) 
	{
		this.groupe = groupe;
	}

	/**
	 * Retourne la liste des notes de l'etudiant
	 * 
	 * @return bulletin Le bulletin de l'etudiant.
	 */
	public Bulletin getBulletin() 
	{
		return bulletin;
	}
	
	/**
	 * Défini le bulletin de l'étudiant
	 * 
	 * @param bulletin Un bulletin.
	 */
	public void setBulletin(Bulletin bulletin) 
	{
		this.bulletin = bulletin;
	}
	
	/**
	 * Compare le numéro d'identification de deux étudiants
	 * 
	 * @param etudiant2 Un étudiant.
	 */
	@Override
	public int compareTo(Etudiant etudiant2) 
	{
		int compId = this.getId().compareTo(etudiant2.getId());
        
		return ((compId != 0) ? compId : 0);
	}
	
}