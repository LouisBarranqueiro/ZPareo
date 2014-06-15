package beans;

import java.util.Set;

public class Professeur extends Utilisateur implements Comparable<Professeur>
{
	private Set<Matiere> listeMatieres;
	private Set<Groupe> listeGroupes;
	
	/**
	 * Constructeur
	 */
	public Professeur()
	{
		super();
		this.listeMatieres = null;
		this.listeGroupes = null;
	}
	/**
	 * Constructeur
	 * 
	 * @param professeur Un professeur.
	 */
	public Professeur(Professeur professeur)
	{
		super(professeur);
		this.setListeMatieres(professeur.getListeMatieres());
		this.setListeGroupes(professeur.getListeGroupes());
	}
	
	/**
	 * Retourne la liste des mati�res du professeur
	 * 
	 * @return listeMatieres La liste des mati�res du professeur.
	 */
	public Set<Matiere> getListeMatieres()
	{
		return listeMatieres;
	}
	
	/**
	 * D�fini la liste des mai�res du professeu
	 * 
	 * @param listeMatieres Une liste de mati�res.
	 */
	public void setListeMatieres(Set<Matiere> listeMatieres) 
	{
		this.listeMatieres = listeMatieres;
	}
	
	/**
	 * Retourne la liste des groupes du professeur
	 * 
	 * @return listeGroupes La liste des groupes du professeur.
	 */
	public Set<Groupe> getListeGroupes() 
	{
		return listeGroupes;
	}
	
	/**
	 * D�fini la liste des groupes du professeu
	 * 
	 * @param listeGroupes Une liste de groupes.
	 */
	public void setListeGroupes(Set<Groupe> listeGroupes) 
	{
		this.listeGroupes = listeGroupes;
	}
	
	/**
	 * Compare le num�ro d'identification de deux professeurs
	 * 
	 * @param professeur2 Un professeur.
	 */
	@Override
	public int compareTo(Professeur professeur2) 
	{
        int compId = this.getId().compareTo(professeur2.getId());

        return ((compId != 0) ? compId : 0);
	}
	
}