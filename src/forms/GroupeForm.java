package forms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import beans.Etudiant;
import beans.Groupe;
import dao.GroupeDao;
import javax.servlet.http.HttpServletRequest;

public final class GroupeForm 
{
	private static final String CHAMP_ID        = "id";
    private static final String CHAMP_NOM       = "nom";
    private Map<String, String> erreurs         = new HashMap<String, String>();
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
        Groupe groupe = new Groupe();
        
        try 
        {
            traiterNom(nom, groupe);
            traiterGroupe(groupe);
            
            if (erreurs.isEmpty()) 
            {
            	groupeDao.creer(groupe);
            }
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return groupe;
    }
    
    /**
     * Retourne la liste des groupes correspondantes
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
    	
    	if(id != null) 
    	{
    		groupe.setId(Long.parseLong(id));
    	}
    	
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
    	Groupe groupe = new Groupe();
    	
    	groupe.setId(Long.parseLong(id));
    	traiterNom(nom, groupe);
    	traiterGroupe(groupe);
           
    	if (erreurs.isEmpty()) 
       	{
    		groupeDao.editer(groupe);
       	}

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
    	
    	groupe.setId(Long.parseLong(id));
    	groupe = groupeDao.trouver(groupe);
    	
    	return groupe;
    }
    
    /**
     * Supprime un groupe dans la base de données
     * 
     * @param request
     * @return statut
     */
    public int supprimerGroupe(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Groupe groupe = new Groupe();
    	int statut;
    	
    	groupe.setId(Long.parseLong(id));
    	statut = groupeDao.supprimer(groupe);
    	
    	return statut;
    }
    
    /**
     *  Traite l'attribut : nom
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
     *  Traite l'objet : groupe
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
     * Valide l'objet : groupe
     * 
     * @param groupe
     * @throws Exception
     */
    private void validationGroupe(Groupe groupe) throws Exception 
    {
        if (groupeDao.verifExistance(groupe) != 0) 
        {
            throw new Exception("Ce groupe existe déja");
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

