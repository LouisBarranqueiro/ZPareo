package forms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import beans.Administrateur;
import beans.Matiere;
import dao.MatiereDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class MatiereForm 
{
	private static final String SESSION_ADMINISTRATEUR = "sessionAdministrateur";
	private static final String CHAMP_ID               = "id";
    private static final String CHAMP_NOM              = "nom";
    private Map<String, String> erreurs                = new HashMap<String, String>();
    private MatiereDao matiereDao;

    /**
     * Récupère l'objet : matiereDao
     * 
     * @param matiereDao
     */
    public MatiereForm(MatiereDao matiereDao) 
    {
    	this.matiereDao = matiereDao;
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
     * Ajoute une matière dans la base de données
     * 
     * @param request
     * @return matiere
     */
    public Matiere creerMatiere(HttpServletRequest request) 
    {
        String nom = getValeurChamp(request, CHAMP_NOM);
        Administrateur createur = (Administrateur) getValeurSession(request, SESSION_ADMINISTRATEUR);
        Matiere matiere = new Matiere();
        
        try 
        {
            traiterNom(nom, matiere);
            traiterMatiere(matiere);
            traiterCreateur(createur, matiere);
            
            if (erreurs.isEmpty()) matiereDao.ajouter(matiere);
            
        } 
        catch ( Exception e ) 
        {
        	e.printStackTrace();
        }

        return matiere;
    }
    
    /**
     * Chercher une ou des matière(s) dans la base de donn�es
     * 
     * @param request
     * @return listeMatiere
     */
    public Set<Matiere> rechercherMatiere(HttpServletRequest request) 
    {
    	String id = getValeurChamp(request, CHAMP_ID );
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	Set<Matiere> listeMatiere = new HashSet<Matiere>();
    	Matiere matiere = new Matiere();
    	
    	traiterId(id, matiere);
    	matiere.setNom(nom);
        listeMatiere = matiereDao.rechercher(matiere);
        
    	return listeMatiere;
    }
   
    /**
     * Edite une matière dans la base de données
     * 
     * @param request
     * @return matiere
     */
    public Matiere editerMatiere(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	Administrateur editeur = (Administrateur) getValeurSession(request, SESSION_ADMINISTRATEUR);
    	Matiere matiere = new Matiere();
    	
    	traiterId(id, matiere);
    	traiterNom(nom, matiere);
    	traiterMatiere(matiere);
        traiterEditeur(editeur, matiere);
        
    	if (erreurs.isEmpty()) matiereDao.editer(matiere);
       	

    	return matiere;
    }
    
    /**
     * Retourne une matière dans la base de données
     * 
     * @param request
     * @return matiere
     */
    public Matiere trouverMatiere(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Matiere matiere = new Matiere();
    	
    	traiterId(id, matiere);
    	matiere = matiereDao.trouver( matiere);
    	
    	return matiere;
    }
    
    /**
     * Supprime une matière dans la base de données
     * 
     * @param request
     */
    public void supprimerMatiere(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Administrateur editeur = (Administrateur) getValeurSession(request, SESSION_ADMINISTRATEUR);
    	Matiere matiere = new Matiere();
    	
    	traiterId(id, matiere);
    	traiterEditeur(editeur, matiere);
    	matiereDao.supprimer(matiere);
    }
   
    /**
     *  Traite le numéro d'identification d'une matière
     *  
     * @param id
     * @param matiere
     */
    private void traiterId(String id, Matiere matiere)
    {
    	try
    	{
    		validationId(id);
    		matiere.setId(Long.parseLong(id));
    	}
    	catch (Exception e) 
    	{
            setErreur(CHAMP_ID, e.getMessage());
        }
    }
    
    /**
     *  Traite le nom d'une matière
     *  
     * @param nom
     * @param matiere
     */
    private void traiterNom(String nom, Matiere matiere) 
    {
    	try 
    	{
            validationNom(nom);
        } 
    	catch (Exception e) 
    	{
            setErreur(CHAMP_NOM, e.getMessage());
        }
    	
        matiere.setNom(nom);
    }
    
    /**
     *  Traite une matière
     *  
     * @param matiere
     */
    private void traiterMatiere(Matiere matiere) 
    {
    	try 
    	{
    		validationMatiere(matiere);
        } 
    	catch (Exception e) 
    	{
            setErreur("matiere", e.getMessage());
        }
    }
    
    /**
     *  Traite le créateur d'une matière
     *  
     * @param createur
     * @param matiere
     */
    private void traiterCreateur(Administrateur createur, Matiere matiere) 
    {
    	try 
    	{
    		validationCreateur(createur);
        } 
    	catch (Exception e) 
    	{
            setErreur("administrateur", e.getMessage());
        }
    	
    	matiere.setCreateur(createur);
    }
    
    /**
     *  Traite l'éditeur d'une matière
     *  
     * @param editeur
     * @param matiere
     */
    private void traiterEditeur(Administrateur editeur, Matiere matiere) 
    {
    	try 
    	{
    		validationCreateur(editeur);
        } 
    	catch (Exception e) 
    	{
            setErreur("administrateur", e.getMessage());
        }
    	
    	matiere.setEditeur(editeur);
    }
    
    /**
     * Valide le numéro d'identification d'une matière
     * 
     * @param id
     * @throws Exception
     */
    private void validationId(String id) throws Exception 
    {
        if ((id == null)) throw new Exception("Le numéro d'identification est nul");
    }
    
    /**
     * Valide le nom d'une matière
     * 
     * @param nom
     * @throws Exception
     */
    private void validationNom(String nom) throws Exception 
    {
        if ((nom == null) || (nom.length() < 3) || (nom.length() > 55)) throw new Exception("Veuillez entrer un nom de 3 à 55 caractères");
        
    }
    
    /**
     * Valide une matière
     * 
     * @param matiere
     * @throws Exception
     */
    private void validationMatiere(Matiere matiere) throws Exception 
    {
        if (matiereDao.verifExistance(matiere) != 0) throw new Exception("Cette matière existe déja");
        
    }
    
    /**
     * Valide le créateur ou éditeur d'une matière
     * 
     * @param createur
     * @throws Exception
     */
    private void validationCreateur(Administrateur createur) throws Exception 
    {
        if (createur.getId() == null) throw new Exception("Administrateur inconnu");
        
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
   
        return (((valeur == null) || (valeur.trim().length() == 0)) ? null : valeur.trim());
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
}

