package forms;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import dao.EtudiantDao;
import dao.ExamenDao;

import javax.servlet.http.HttpServletRequest;

import beans.Etudiant;
import beans.Examen;
import beans.Groupe;
import beans.Matiere;
import beans.Professeur;

public final class ExamenForm 
{
	private static final String CHAMP_ID               = "id";
    private static final String CHAMP_NOM              = "nom";
    private static final String CHAMP_GROUPE           = "groupe";
    private static final String CHAMP_MATIERE          = "matiere";
    private static final String CHAMP_MOYENNE_GENERALE = "moyenneGenerale";
    private Map<String, String> erreurs                = new HashMap<String, String>();
    private ExamenDao examenDao;
    
    /**
     * Récupère l'objet : etudiantDao
     * 
     * @param examenDao
     */
    public ExamenForm(ExamenDao examenDao) 
    {
    	this.examenDao = examenDao;
    }
    
    /**
     * Retourne les erreurs
     * 
     * @return erreurs
     */
    public Map<String, String> getErreurs() 
    {
        return erreurs;
    }
  
    /**
     * Recherche un ou des examen(s) dans la base de données
     * 
     * @param request
     * @return listeExamens
     */
    public TreeSet<Examen> rechercherExamen(Professeur professeur, HttpServletRequest request) 
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String groupeId = getValeurChamp(request, CHAMP_GROUPE);
    	String matiereId = getValeurChamp(request, CHAMP_MATIERE);
    	String moyenneGenerale = getValeurChamp(request, CHAMP_MOYENNE_GENERALE);
    	TreeSet<Examen> listeExamens = new TreeSet<Examen>();
    	Examen examen = new Examen();
    	Matiere matiere = new Matiere();
    	Groupe groupe = new Groupe();
    	
    	if(id != null) 
    	{
    		examen.setId(Long.parseLong(id));
    	}
    	
    	if( groupeId != null ) 
    	{
    		groupe.setId(Long.parseLong(groupeId));
    	}
    	
    	if( matiereId != null ) 
    	{
    		matiere.setId(Long.parseLong(matiereId));
    	}
    	if( moyenneGenerale != null ) 
    	{
    		examen.setMoyenneGenerale(Float.parseFloat(moyenneGenerale));
    	}
    	examen.setNom(nom);
    	examen.setGroupe(groupe);
    	examen.setMatiere(matiere);
    	examen.setProfesseur(professeur);
    	
    	System.out.println("CA ENTRE");
    	listeExamens = examenDao.rechercher(examen);
        
    	return listeExamens;
    }
    
    /**
     *  Traite l'attribut : nom
     *  
     * @param nom
     * @param examen
     */
    private void traiterNom(String nom, Examen examen) 
    {
    	try 
    	{
            validationNom(nom);
        } 
    	catch (Exception e) 
    	{
            setErreur(CHAMP_NOM, e.getMessage());
        }
    	
    	examen.setNom(nom.substring(0, 1).toUpperCase() + nom.substring(1).toLowerCase());
    }

    /**
     * Valide l'attribut : nom
     * 
     * @param nom
     * @throws Exception
     */
    private void validationNom(String nom) throws Exception 
    {
        if ((nom == null) || (nom.length() < 2) || (nom.length() > 50)) 
        {
            throw new Exception("Veuillez entrer un nom de 2 à 50 caractères");
        }
    }
    
    /**
     * Enregistre une erreur
     * 
     * @param champ
     * @param message
     */
    private void setErreur(String champ, String message)
    {
        erreurs.put(champ, message);
    }
    
    /**
     * Retourne les valeurs des champs du formulaire
     * 
     * @param request
     * @param nomChamp
     * @return valeur
     */
    private static String getValeurChamp(HttpServletRequest request, String nomChamp) 
    {
        String valeur = request.getParameter(nomChamp);
        
        if ((valeur == null) || (valeur.trim().length() == 0)) 
        {
            return null;
        }
        else 
        {
            return valeur.trim();
        }
    }
}

