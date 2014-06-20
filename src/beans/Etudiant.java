package beans;

public class Etudiant extends Utilisateur implements Comparable<Etudiant>
{
	private Groupe groupe;
<<<<<<< HEAD
=======
	private Set<Note> listeNotes;
>>>>>>> bd663d8416c4ae10605929587e6a0762138f4060
	private Bulletin bulletin;

	/**
	 * Constructeur
	 */
	public Etudiant()
	{
		super();
		this.groupe = null;
<<<<<<< HEAD
=======
		this.listeNotes = null;
>>>>>>> bd663d8416c4ae10605929587e6a0762138f4060
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
<<<<<<< HEAD
		this.groupe = etudiant.getGroupe();
		this.bulletin = etudiant.getBulletin();
=======
		this.setGroupe(etudiant.getGroupe());
		this.setListeNote(listeNotes);
		this.setBulletin(etudiant.getBulletin());
>>>>>>> bd663d8416c4ae10605929587e6a0762138f4060
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
<<<<<<< HEAD
	 * Compare le numéro d'identification de deux étudiants
=======
	 * Retourne la liste des notes de l'�tudiant
	 * 
	 * @return bulletin Le bulletin de l'�tudiant.
	 */
	public Bulletin getBulletin() 
	{
		return bulletin;
	}
	
	/**
	 * Défini le bulletin de l'etudiant
	 * 
	 * @param bulletin Un bulletin.
	 */
	public void setBulletin(Bulletin bulletin) 
	{
		this.bulletin = bulletin;
	}
	
	/**
	 * Compare le num�ro d'identification de deux �tudiants
>>>>>>> bd663d8416c4ae10605929587e6a0762138f4060
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