package forms;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import dao.ExamenDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import beans.Etudiant;
import beans.Examen;
import beans.FormatExamen;
import beans.Groupe;
import beans.Matiere;
import beans.Note;
import beans.Professeur;

public final class ExamenForm 
{
	private static final String SESSION_PROFESSEUR = "sessionProfesseur";
	private static final String CHAMP_ID           = "id";
	private static final String CHAMP_FORMAT       = "format";
	private static final String CHAMP_PROFESSEUR   = "professeur";
	private static final String CHAMP_COEFFICIENT  = "coefficient";
	private static final String CHAMP_DATE         = "date";
	private static final String CHAMP_NOTES        = "notes";
	private static final String CHAMP_ETUDIANTS    = "etudiants";
    private static final String CHAMP_NOM          = "nom";
    private static final String CHAMP_GROUPE       = "groupe";
    private static final String CHAMP_MATIERE      = "matiere";
    private static final String CHAMP_MOYENNE      = "moyenneGenerale";
    private Map<String, String> erreurs            = new HashMap<String, String>();
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
     * @param request
     * @return examen
     */
    public Examen creerExamen(HttpServletRequest request) 
    {
    	String date = getValeurChamp(request, CHAMP_DATE);
    	String format = getValeurChamp(request, CHAMP_FORMAT);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String coefficient = getValeurChamp(request, CHAMP_COEFFICIENT);
    	String groupeId = getValeurChamp(request, CHAMP_GROUPE);
    	String matiereId = getValeurChamp(request, CHAMP_MATIERE);
    	Professeur professeur = (Professeur) getValeurSession(request, SESSION_PROFESSEUR);
    	Examen examen = new Examen();
    	
        try 
        {
        	traiterFormatId(format, examen);
        	traiterNom(nom, examen);
        	traiterDate(date, examen);
        	traiterCoefficient(coefficient, examen);
        	traiterGroupeId(groupeId, examen);
        	traiterMatiereId(matiereId, examen);
        	traiterProfesseur(professeur, examen);
            
            if (erreurs.isEmpty()) examenDao.ajouter(examen);
            
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return examen;
    }
    
    /**
     * Edite un examen dans la base de données
     * 
     * @param request
     * @return examen
     */
    public Examen editerExamen(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String date = getValeurChamp(request, CHAMP_DATE);
    	String format = getValeurChamp(request, CHAMP_FORMAT);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String coefficient = getValeurChamp(request, CHAMP_COEFFICIENT);
    	String matiereId = getValeurChamp(request, CHAMP_MATIERE);
    	String etudiants = getValeurChamp(request, CHAMP_ETUDIANTS);
    	String notes = getValeurChamp(request, CHAMP_NOTES);
    	Professeur professeur = (Professeur) getValeurSession(request, SESSION_PROFESSEUR);
    	Examen examen = new Examen();

    	try 
        {
    		traiterId(id, examen);
        	traiterFormatId(format, examen);
        	traiterNom(nom, examen);
        	traiterDate(date, examen);
        	traiterCoefficient(coefficient, examen);
        	traiterMatiereId(matiereId, examen);
        	traiterProfesseur(professeur, examen);
        	traiterNotes(notes, etudiants, examen);
            
            if (erreurs.isEmpty()) examenDao.editer(examen);
            else examen = examenDao.trouver(examen);
            
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
     * @param request
     * @return examen
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
    public Set<Examen> rechercherExamen(HttpServletRequest request) 
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String date = getValeurChamp(request, CHAMP_DATE);
    	String format = getValeurChamp(request, CHAMP_FORMAT);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String groupeId = getValeurChamp(request, CHAMP_GROUPE);
    	String matiereId = getValeurChamp(request, CHAMP_MATIERE);
    	String moyenne = getValeurChamp(request, CHAMP_MOYENNE);
    	Set<Examen> listeExamens = new TreeSet<Examen>();
    	Professeur professeur = (Professeur) getValeurSession(request, SESSION_PROFESSEUR);
    	Examen examen = new Examen();

    	traiterId(id, examen);
    	traiterMatiereId(matiereId, examen);
    	traiterGroupeId(groupeId, examen);
    	traiterMoyenne(moyenne, examen);
    	traiterFormatId(format, examen);
    	traiterDate(date, examen);
    	examen.setNom(nom);
    	examen.setProfesseur(professeur);
    	listeExamens = examenDao.rechercher(examen);
        
    	return listeExamens;
    }
    
    /**
     * Supprime un examen dans la base de données
     * 
     * @param request
     */
    public void supprimerExamen(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Examen examen = new Examen();
    	Professeur professeur = (Professeur) getValeurSession(request, SESSION_PROFESSEUR);
    	
    	try 
        {
    		traiterId(id, examen);
    		traiterProfesseur(professeur, examen);
            
            if (erreurs.isEmpty()) examenDao.supprimer(examen);
            
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
    }
    
    /**
     *  Traite le numéro d'identification de l'examen
     *  
     * @param id
     * @param examen
     */
    private void traiterId(String id, Examen examen)
    {
    	try
    	{
    		validationId(id);
    		examen.setId(Long.parseLong(id));
    	}
    	catch (Exception e) 
    	{
            setErreur(CHAMP_ID, e.getMessage());
        }
    }
    
    /**
     *  Traite le nom de l'examen
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
     *  Traite la date de l'examen
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
    			date = modifFormatDate("dd/MM/yyyy","yyyy-MM-dd", date);
    		}
    	} 	
    	catch (Exception e) 
    	{
    		setErreur(CHAMP_DATE, e.getMessage());
    	}
    	
    	examen.setDate(date);
    }
    
    /**
     *  Traite le coefficient de l'examen
     *  
     * @param coefficient
     * @param examen
     */
    private void traiterCoefficient(String coefficient, Examen examen) 
    {
    	try
    	{
    		validationCoefficient(coefficient);
    		examen.setCoefficient(Float.parseFloat(coefficient.replace(",",".")));
    	} 	
    	catch (Exception e) 
    	{
    		setErreur(CHAMP_COEFFICIENT, e.getMessage());
    	}
    }
    
    /**
     *  Traite le numéro d'identification de la matière de l'examen
     *  
     * @param matiereId
     * @param examen
     */
    private void traiterMatiereId(String matiereId, Examen examen) 
    {
    	Matiere matiere = new Matiere();
    	
    	try
    	{
    		validationMatiereId(matiereId);
    		matiere.setId(Long.parseLong(matiereId));
    	}
    	catch (Exception e) 
    	{
    		setErreur(CHAMP_MATIERE, e.getMessage());
    	}
    	
    	examen.setMatiere(matiere);
    }
    
    /**
     *  Traite le numéro d'identification de la groupe de l'examen
     *  
     * @param groupeId
     * @param examen
     */
    private void traiterGroupeId(String groupeId, Examen examen) 
    {
    	Groupe groupe = new Groupe();
    	
    	try
    	{
    		validationGroupeId(groupeId);
    		groupe.setId(Long.parseLong(groupeId));
    	}
    	catch (Exception e) 
    	{
    		setErreur(CHAMP_GROUPE, e.getMessage());
    	}
    	
    	examen.setGroupe(groupe);
    }
    
    /**
     *  Traite le professeur de l'examen
     *  
     * @param professeur
     * @param examen
     */
    private void traiterProfesseur(Professeur professeur, Examen examen) 
    {
    	try
    	{
    		validationProfesseur(professeur);
    	}
    	catch (Exception e) 
    	{
    		setErreur(CHAMP_PROFESSEUR, e.getMessage());
    	}
   
    	examen.setProfesseur(professeur);
    }
    
    /**
     *  Traite le numéro d'identification du format de l'examen
     *  
     * @param formatId
     * @param examen
     */
    private void traiterFormatId(String formatId, Examen examen) 
    {
    	FormatExamen format = new FormatExamen();
    	
    	try
    	{
    		validationFormatId(formatId);
    		format.setId(Long.parseLong(formatId));
    	}
    	catch (Exception e) 
    	{
    		setErreur(CHAMP_FORMAT, e.getMessage());
    	}
    	
    	examen.setFormat(format);
    }

    /**
     *  Traite la moyenne de l'examen
     *  
     * @param moyenneGenerale
     * @param examen
     */
    private void traiterMoyenne(String moyenne, Examen examen) 
    {
    	try
    	{
    		validationMoyenne(moyenne);
    		examen.setMoyenne(Float.parseFloat(moyenne));
    	}
    	catch (Exception e) 
    	{
    		setErreur(CHAMP_MOYENNE, e.getMessage());
    	}
    }
    
    /**
     *  Traite le numéro d'identification d'un étudiant
     *  
     * @param etudiantId
     * @param note
     */
    private void traiterEtudiantId(String etudiantId, Note note)
    {
    	Etudiant etudiant = new Etudiant();
    	
    	try
    	{
    		validationEtudiantId(etudiantId);
    		etudiant.setId(Long.parseLong(etudiantId));
    	}
    	catch (Exception e) 
    	{
    		setErreur(CHAMP_ETUDIANTS, e.getMessage());
    	}
 
    	note.setEtudiant(etudiant);
    }
    
    /**
     * Traite une note de l'examen
     * 
     * @param noteString
     * @param note
     */
    private void traiterNote(String noteString, Note note)
    {
    	try
    	{
    		validationNote(noteString);
    		note.setNote(Float.parseFloat(noteString.replace(",",".")));
    	}
    	catch(Exception e)
    	{
    		setErreur(CHAMP_NOTES, e.getMessage());
    	}
    }
    
    /**
     * Traite les notes de l'examen
     * 
     * @param notes
     * @param etudiants
     * @param examen
     */
    private void traiterNotes(String notes, String etudiants, Examen examen)
    {
    	String[] tabNotes = notes.split("-");
    	String[] tabEtudiantsId = etudiants.split("-");
    	Set<Note> listeNotes = new HashSet<Note>();
    	
        for (int i=0;i<tabNotes.length;i++)
        {
            Note note = new Note();
            traiterEtudiantId(tabEtudiantsId[i], note);
            traiterNote(tabNotes[i], note);
            listeNotes.add(note);
        }
        
    	examen.setListeNotes(listeNotes);
    }

    /**
     * Valide le numéro d'identification d'un examen
     * 
     * @param id
     * @throws Exception
     */
    private void validationId(String id) throws Exception 
    {
        if ((id == null)) throw new Exception("Le numéro d'identification est nul");
    }
    
    /**
     * Valide le nom de l'examen
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
     * Valide la date de l'examen
     * 
     * @param date
     * @throws Exception
     */
    private void validationDate(String date) throws Exception 
    {
        if ((date == null) || ((!date.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)")) 
        		&& (!date.matches("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")))) 
        {
            throw new Exception("Veuillez entrer un date au format JJ/MM/AAAA");
        }
    }
    
    /**
     * Valide le numéro d'identification de la matière de l'examen
     * 
     * @param matiereId
     * @throws Exception
     */
    private void validationMatiereId(String matiereId) throws Exception 
    {
        if (matiereId == null) throw new Exception("Veuillez sélectionner une matière");
        
    }
    
    /**
     * Valide le professeur de l'examen
     * 
     * @param professeur
     * @throws Exception
     */
    private void validationProfesseur(Professeur professeur) throws Exception 
    {
        if (professeur == null) throw new Exception("Professeur inconnu");
        
    }
    
    /**
     * Valide le numéro d'identification du groupe de l'examen
     * 
     * @param groupeId
     * @throws Exception
     */
    private void validationGroupeId(String groupeId) throws Exception 
    {
        if (groupeId == null) throw new Exception("Veuillez sélectionner un groupe");
        
    }
    
    /**
     * Valide le numéro d'identification d'un étudiant
     * 
     * @param etudiantId
     * @throws Exception
     */
    private void validationEtudiantId(String etudiantId) throws Exception 
    {
        if (etudiantId == null) throw new Exception("Etudiant inconnu");
        
    }
    
    /**
     * Valide le numéro d'identification du format de l'examen
     * 
     * @param formatId
     * @throws Exception
     */
    private void validationFormatId(String formatId) throws Exception 
    {
        if (formatId == null) throw new Exception("format d'examen inconnu");
        
    }
    
    /**
     * Valide la moyenne de l'examen
     * 
     * @param moyenne
     * @throws Exception
     */
    private void validationMoyenne(String moyenne) throws Exception 
    {
        if (moyenne == null) throw new Exception("Veuillez entrez un nombre décimal");
        
    }
    
    /**
     * Valide le coefficient de l'examen
     * 
     * @param coefficient
     * @throws Exception
     */
    private void validationCoefficient(String coefficient) throws Exception 
    {
        if ((coefficient == null) || (!coefficient.matches("[0-9,.]{1,5}"))) 
        {
            throw new Exception("Veuillez entrer un nombre");
        }
    }

    /**
     * Valide une note de l'examen
     * 
     * @param note
     * @throws Exception
     */
    private void validationNote(String note) throws Exception 
    {
        if ((note == null) || (!note.matches("[0-9,.]{1,5}"))) throw new Exception("Veuillez entrer un nombre");
        
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
     * Retourne la valeur d'un paramètre de la requête
     * 
     * @param request
     * @param nomChamp
     * @return valeur
     */
    private static String getValeurChamp(HttpServletRequest request, String nomChamp) 
    {
        String valeur = request.getParameter(nomChamp);
        
        return ((valeur == null) || (valeur.trim().length() == 0) ? null : valeur.trim());
    }
    
    /**
     * Retourne la valeur d'un paramètre de session
     * 
     * @param request
     * @param nomSession
     * @return objet
     */
    private static Object getValeurSession(HttpServletRequest request, String nomSession) 
    {
    	HttpSession session = request.getSession();
    	Object objet = session.getAttribute(nomSession);
    	
        return ((objet == null) ? null : objet);
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

