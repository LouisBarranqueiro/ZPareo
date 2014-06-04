package forms;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import dao.ExamenDao;
import javax.servlet.http.HttpServletRequest;
import beans.Examen;
import beans.FormatExamen;
import beans.Groupe;
import beans.Matiere;
import beans.Professeur;

public final class ExamenForm 
{
	private static final String CHAMP_ID               = "id";
	private static final String CHAMP_FORMAT           = "format";
	private static final String CHAMP_PROFESSEUR       = "professeur";
	private static final String CHAMP_COEFFICIENT      = "coefficient";
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
     * Créer un examen dans la base de données
     * 
     * @param examen
     * @return listeExamens
     */
    public Examen creerExamen(HttpServletRequest request) 
    {
    	String date = getValeurChamp(request, CHAMP_DATE);
    	String format = getValeurChamp(request, CHAMP_FORMAT);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String coefficient = getValeurChamp(request, CHAMP_COEFFICIENT);
    	String professeurId = getValeurChamp(request, CHAMP_PROFESSEUR);
    	String groupeId = getValeurChamp(request, CHAMP_GROUPE);
    	String matiereId = getValeurChamp(request, CHAMP_MATIERE);
    	Examen examen = new Examen();
    	
        try 
        {
        	traiterFormat(format, examen);
        	traiterNom(nom, examen);
        	traiterDate(date, examen);
        	traiterCoefficient(coefficient, examen);
        	traiterGroupeId(groupeId, examen);
        	traiterMatiereId(matiereId, examen);
        	traiterProfesseurId(professeurId, examen);
            
            if (erreurs.isEmpty()) 
            {
            	examenDao.creer(examen);
            }
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return examen;
    }
    
    /**
     * Cherche un examen dans la base de données
     * 
     * @param examen
     * @return
     */
    public Examen trouverExamen(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Examen examen = new Examen();
    	
    	traiterId(id, examen);
    	examen = examenDao.trouver(examen);
    
    	return examen;
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

    	traiterId(id, examen);
    	traiterMatiereId(matiereId, examen);
    	traiterGroupeId(groupeId, examen);
    	traiterMoyenneGenerale(moyenneGenerale, examen);
    	traiterFormat(format, examen);
    	examen.setDate(date);
    	examen.setNom(nom);
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
    	try
    	{
    		validationDate(date);
    		
    		if (date.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)"))
    		{
    			date = modifFormatDate("dd/MM/yyyy","yyyy-MM-dd",date);
    		}
    	} 	
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    	
    	examen.setDate(date);
    }
    
    /**
     *  Traite l'attribut : coefficient
     *  
     * @param coefficient
     * @param examen
     */
    private void traiterCoefficient(String coefficient, Examen examen) 
    {
    	try
    	{
    		validationCoefficient(coefficient);
    	} 	
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    	
    	examen.setCoefficient(Float.parseFloat(coefficient.replace(",",".")));
    }
    
    /**
     *  Traite l'attribut : matiereId
     *  
     * @param matiereId
     * @param examen
     */
    private void traiterMatiereId(String matiereId, Examen examen) 
    {
    	Matiere matiere = new Matiere();
    	
    	if (matiereId != null) 
    	{
    		matiere.setId(Long.parseLong(matiereId));
    	}
    	
    	examen.setMatiere(matiere);
    }
    
    /**
     *  Traite l'attribut : groupeId
     *  
     * @param groupeId
     * @param examen
     */
    private void traiterGroupeId(String groupeId, Examen examen) 
    {
    	Groupe groupe = new Groupe();
    	
    	if (groupeId != null) 
    	{
    		groupe.setId(Long.parseLong(groupeId));
    	}
    	
    	examen.setGroupe(groupe);
    }
    
    /**
     *  Traite l'attribut : professeurId
     *  
     * @param professeurId
     * @param examen
     */
    private void traiterProfesseurId(String professeurId, Examen examen) 
    {
    	Professeur professeur = new Professeur();
    	
    	if (professeurId != null) 
    	{
    		professeur.setId(Long.parseLong(professeurId));
    	}
    	
    	examen.setProfesseur(professeur);
    }
    
    /**
     *  Traite l'attribut : formatId
     *  
     * @param formatId
     * @param examen
     */
    private void traiterFormat(String formatId, Examen examen) 
    {
    	FormatExamen format = new FormatExamen();
    	
    	if (formatId != null) 
    	{
    		format.setId(Long.parseLong(formatId));
    	}
    	
    	examen.setFormat(format);
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
        if ((date == null) || ((!date.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)")) && (!date.matches("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")))) 
        {
            throw new Exception("Veuillez entrer un date au format JJ/MM/AAAA");
        }
    }
    
    /**
     * Valide l'attribut : coefficient
     * 
     * @param coefficient
     * @throws Exception
     */
    private void validationCoefficient(String coefficient) throws Exception 
    {
        if (coefficient == null) 
        {
            throw new Exception("Veuillez entrer un nombre");
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
    
    /**
     * Modifier le format d'une date
     * 
     * @param ancienFormat
     * @param nouvFormat
     * @param dateString
     * @return
     */
	private String modifFormatDate(String ancienFormat, String nouvFormat, String dateString)
    {
    	SimpleDateFormat ANC_FORMAT = new SimpleDateFormat(ancienFormat);
    	SimpleDateFormat NOUV_FORMAT = new SimpleDateFormat(nouvFormat);
    	java.util.Date dateUtil = new java.util.Date();
    	java.sql.Date dateSQL = null;
    	try
    	{
    		dateUtil = ANC_FORMAT.parse(dateString);
    		dateSQL = new java.sql.Date(dateUtil.getTime());
    	} 	
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    	return NOUV_FORMAT.format(dateSQL);
    }
}

