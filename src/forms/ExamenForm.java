package forms;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private static final String CHAMP_ID               = "id";
	private static final String CHAMP_FORMAT           = "format";
	private static final String CHAMP_DATE             = "date";
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
    	String date = getValeurChamp(request, CHAMP_DATE);
    	String format = getValeurChamp(request, CHAMP_FORMAT);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String groupeId = getValeurChamp(request, CHAMP_GROUPE);
    	String matiereId = getValeurChamp(request, CHAMP_MATIERE);
    	String moyenneGenerale = getValeurChamp(request, CHAMP_MOYENNE_GENERALE);
    	TreeSet<Examen> listeExamens = new TreeSet<Examen>();
    	Examen examen = new Examen();
    	Matiere matiere = new Matiere();
    	Groupe groupe = new Groupe();
    	System.out.println(format);
    	
    	traiterId(id, examen);
    	traiterMatiereId(matiereId, matiere);
    	traiterGroupeId(groupeId, groupe);
    	traiterMoyenneGenerale(moyenneGenerale, examen);
    	traiterDate(date, examen);
    	traiterFormat(format, examen);
    	examen.setNom(nom);
    	examen.setGroupe(groupe);
    	examen.setMatiere(matiere);
    	examen.setProfesseur(professeur);
    	
    	listeExamens = examenDao.rechercher(examen);
        
    	return listeExamens;
    }
    
    /**
     *  Traite l'attribut : id
     *  
     * @param id
     * @param examen
     */
    private void traiterId(String id, Examen examen) 
    {
    	if (id != null) 
    	{
			examen.setId(Long.parseLong(id));
    	}
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
     *  Traite l'attribut : date
     *  
     * @param date
     * @param examen
     */
    private void traiterDate(String date, Examen examen) 
    {
    	final SimpleDateFormat SQL_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date judDate1 = new java.util.Date();

    	try
    	{
    		validationDate(date);
    		judDate1 = SQL_FORMAT.parse(date);
    		java.sql.Date sqlDate1 = new java.sql.Date(judDate1.getTime());
    		examen.setDate(SQL_FORMAT.format(sqlDate1));
    	} 	
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    }
    
    /**
     *  Traite l'attribut : matiereId
     *  
     * @param matiereId
     * @param examen
     */
    private void traiterMatiereId(String matiereId, Matiere matiere) 
    {
    	if (matiereId != null) 
    	{
    		matiere.setId(Long.parseLong(matiereId));
    	}
    }
    
    /**
     *  Traite l'attribut : groupeId
     *  
     * @param groupeId
     * @param examen
     */
    private void traiterGroupeId(String groupeId, Groupe groupe) 
    {
    	if (groupeId != null) 
    	{
    		groupe.setId(Long.parseLong(groupeId));
    	}
    }
    
    /**
     *  Traite l'attribut : format
     *  
     * @param format
     * @param examen
     */
    private void traiterFormat(String format, Examen examen) 
    {
    	long formatId = 0;
    	if (format != null) 
    	{
    		formatId = Long.parseLong(format);
    	}
    	if (formatId == 1) 
    	{
    		examen.setFormat("Oral");
    	}
    	else if (formatId == 2) 
    	{
    		examen.setFormat("Ecrit");
    	}
    	System.out.println(format);
    	System.out.println(examen.getFormat());
    }

    /**
     *  Traite l'attribut : moyenneGenerale
     *  
     * @param moyenneGenerale
     * @param examen
     */
    private void traiterMoyenneGenerale(String moyenneGenerale, Examen examen) 
    {
    	if (moyenneGenerale != null) 
    	{
    		examen.setMoyenneGenerale(Float.parseFloat(moyenneGenerale));
    	}
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
     * Valide l'attribut : date
     * 
     * @param date
     * @throws Exception
     */
    private void validationDate(String date) throws Exception 
    {
        if ((date == null) || (date.matches("(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[012])/[0-9]{4}"))) 
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

