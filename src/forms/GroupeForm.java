package forms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import beans.Administrateur;
import beans.Groupe;
import dao.GroupeDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class GroupeForm 
{
	private static final String SESSION_ADMINISTRATEUR = "sessionAdministrateur";
	private static final String CHAMP_ID               = "id";
    private static final String CHAMP_NOM              = "nom";
    private Map<String, String> erreurs                = new HashMap<String, String>();
    private GroupeDao groupeDao;

    /**
     * Récupère l'objet GroupeDao
     * 
     * @param groupeDao
     */
    public GroupeForm(GroupeDao groupeDao) 
    {
    	this.groupeDao = groupeDao;
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
     * Crée un groupe dans la base de données
     * 
     * @param request
     * @return groupe
     */
    public Groupe creerGroupe(HttpServletRequest request) 
    {
        String nom = getValeurChamp(request, CHAMP_NOM);
        Administrateur createur = (Administrateur) getValeurSession(request, SESSION_ADMINISTRATEUR);
        Groupe groupe = new Groupe();
        
        try 
        {
            traiterNom(nom, groupe);
            traiterGroupe(groupe);
            traiterCreateur(createur, groupe);
            
            if (erreurs.isEmpty()) groupeDao.ajouter(groupe);
          
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return groupe;
    }
    
    /**
     * Cherche un ou plusieurs groupe(s) dans la base de données
     * 
     * @param request
     * @return listeGroupes
     */
    public Set<Groupe> rechercherGroupe(HttpServletRequest request) 
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	Set<Groupe> listeGroupes = new HashSet<Groupe>();
    	Groupe groupe = new Groupe();
    	
    	
    	traiterId(id, groupe);
    	groupe.setNom(nom);
    	listeGroupes = groupeDao.rechercher(groupe);
        
    	return listeGroupes;
    }
   
    /**
     * Edite un groupe dans la bsae de données
     * 
     * @param request
     * @return groupe
     */
    public Groupe editerGroupe(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	Administrateur editeur = (Administrateur) getValeurSession(request, SESSION_ADMINISTRATEUR);
    	Groupe groupe = new Groupe();
    	
    	traiterId(id, groupe);
    	traiterNom(nom, groupe);
    	traiterGroupe(groupe);
    	traiterEditeur(editeur, groupe);
           
    	if (erreurs.isEmpty()) groupeDao.editer(groupe);

    	return groupe;
    }
    
    /**
     * Cherche un groupe dans la base de données
     * 
     * @param request
     * @return groupe
     */
    public Groupe trouverGroupe(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Groupe groupe = new Groupe();
    	
    	traiterId(id, groupe);
    	groupe = groupeDao.trouver(groupe);
    	
    	return groupe;
    }
    
    /**
     * Supprime un groupe dans la base de données
     * 
     * @param request
     */
    public void supprimerGroupe(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Administrateur editeur = (Administrateur) getValeurSession(request, SESSION_ADMINISTRATEUR);
    	Groupe groupe = new Groupe();
    	
    	traiterId(id, groupe);
    	traiterEditeur(editeur, groupe);
        groupeDao.supprimer(groupe);
    }
    
    /**
     *  Traite le numéro d'identification d'un groupe
     *  
     * @param id
     * @param groupe
     */
    private void traiterId(String id, Groupe groupe)
    {
    	try
    	{
    		validationId(id);
    		groupe.setId(Long.parseLong(id));
    	}
    	catch (Exception e) 
    	{
            setErreur(CHAMP_ID, e.getMessage());
        }
    }
    
    /**
     *  Traite le nom d'un groupe
     *  
     * @param nom
     * @param groupe
     */
    private void traiterNom(String nom, Groupe groupe) 
    {
    	try 
    	{
            validationNom(nom);
        } 
    	catch (Exception e) 
    	{
            setErreur(CHAMP_NOM, e.getMessage());
        }
    	
    	groupe.setNom(nom.toUpperCase());
    }

    /**
     *  Traite un groupe
     *  
     * @param groupe
     */
    private void traiterGroupe(Groupe groupe) 
    {
    	try 
    	{
    		validationGroupe(groupe);
        } 
    	catch (Exception e) 
    	{
            setErreur("groupe", e.getMessage());
        }
    }
    
    /**
     *  Traite le créateur d'un groupe
     *  
     * @param createur
     * @param matiere
     */
    private void traiterCreateur(Administrateur createur, Groupe groupe) 
    {
    	try 
    	{
    		validationCreateur(createur);
        } 
    	catch (Exception e) 
    	{
            setErreur("administrateur", e.getMessage());
        }
    	
    	groupe.setCreateur(createur);
    }
    
    /**
     *  Traite l'éditeur d'un groupe
     *  
     * @param editeur
     * @param matiere
     */
    private void traiterEditeur(Administrateur editeur, Groupe groupe) 
    {
    	try 
    	{
    		validationCreateur(editeur);
        } 
    	catch (Exception e) 
    	{
            setErreur("administrateur", e.getMessage());
        }
    	
    	groupe.setEditeur(editeur);
    }
    
    /**
     * Valide le numéro d'identification d'un groupe
     * 
     * @param id
     * @throws Exception
     */
    private void validationId(String id) throws Exception 
    {
        if ((id == null)) throw new Exception("Le numéro d'identification est nul");
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
            throw new Exception("Veuillez entrer un nom de 3 à 50 caractères");
        }
    }
    
    /**
     * Valide un groupe
     * 
     * @param groupe
     * @throws Exception
     */
    private void validationGroupe(Groupe groupe) throws Exception 
    {
        if (groupeDao.verifExistance(groupe) != 0) throw new Exception("Ce groupe existe déja");  
    }
    
    /**
     * Valide le créateur ou éditeur d'un groupe
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

