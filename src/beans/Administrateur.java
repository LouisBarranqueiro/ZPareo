package beans;

public class Administrateur extends Utilisateur implements Comparable<Administrateur>
{
	/**
	 * Constructeur
	 * 
	 */
	public Administrateur()
	{
		super();
	}
	/**
	 * Constructeur
	 * 
	 * @param administrateur 
	 */
	public Administrateur(Administrateur administrateur)
	{
		super(administrateur);
	}
	
	/**
	 * Compare le numéro d'identification de deux administrateurs
	 * 
	 * @param administrateur2 
	 */
	@Override
	public int compareTo(Administrateur administrateur2) 
	{
		int compId = this.getId().compareTo(administrateur2.getId());
        
		return ((compId != 0) ? compId : 0);
	}

}
