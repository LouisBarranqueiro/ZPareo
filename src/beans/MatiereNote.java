package beans;

import java.util.Set;

public class MatiereNote implements Comparable<MatiereNote>
{
	private Long id;
	private Matiere matiere;
	private Float moyenne;
	private Set<Examen> listeExamens;
	
	/**
	 * Constructeur
	 */
	public MatiereNote()
	{
		this.id = null;
		this.matiere = null;
		this.listeExamens = null;
	}
	
	/**
	 * Constructeur
	 * 
	 * @param matiereNote Un objet MatiereNote.
	 */
	public MatiereNote(MatiereNote matiereNote)
	{
		this.id = matiereNote.getId();
		this.matiere = matiereNote.getMatiere();
		this.listeExamens = matiereNote.getListeExamens();
	}
	/**
	 * Retourne le numéro d'identification de l'objet
	 * 
	 * @return id Le numéro d'identification de l'objet.
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * Défini le numéro d'identification de l'objet.
	 * 
	 * @return id Un numéro d'identification.
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}

	/**
	 * Retourne la matière de l'objet
	 * 
	 * @return matiere La matiere de l'objet.
	 */
	public Matiere getMatiere() 
	{
		return matiere;
	}
	
	/**
	 * Défini la matière de l'objet
	 * 
	 * @param matiere Une matiere.
	 */
	public void setMatiere(Matiere matiere) 
	{
		this.matiere = matiere;
	}
	
	/**
	 * Retourne la moyenne de l'objet
	 * 
	 * @return matiere La moyenne de l'objet.
	 */
	public Float getMoyenne() 
	{
		return moyenne;
	}
	
	/**
	 * Défini la moyenne de l'objet
	 * 
	 * @param matiere Une moyenne.
	 */
	public void setMoyenne(Float moyenne) 
	{
		this.moyenne = moyenne;
	}
	
	/**
	 * Retourne la liste des examens de l'objet
	 * 
	 * @return listeExamens La liste d'examens de l'objet.
	 */
	public Set<Examen> getListeExamens()
	{
		return listeExamens;
	}
	
	/**
	 * Défini la liste d'examens de l'objet
	 * 
	 * @param listeExamens Une liste d'examens.
	 */
	public void setListeExamens(Set<Examen> listeExamens) 
	{
		this.listeExamens = listeExamens;
	}

	/**
	 * Compare le nom de la matière de deux objets MatiereNote
	 * 
	 * @param matiereNote2 Un objet MatiereNote.
	 */
	@Override
	public int compareTo(MatiereNote matiereNote2)
	{
		Matiere matiere1 = new Matiere(this.getMatiere());
		Matiere matiere2 = new Matiere(matiereNote2.getMatiere());
        int compId = matiere1.getNom().compareTo(matiere2.getNom());
        
        return ((compId != 0) ? compId : 0 );
	}
	
}
