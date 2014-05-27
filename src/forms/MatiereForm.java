package forms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import models.Groupe;
import models.Matiere;
import dao.MatiereDao;
import javax.servlet.http.HttpServletRequest;

public final class MatiereForm 
{
	private static final String CHAMP_ID     = "id";
    private static final String CHAMP_NOM    = "nom";
    private Map<String, String> erreurs      = new HashMap<String, String>();
    private MatiereDao matiereDao;

    /**
     * R�cup�re l'objet : matiereDao
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
     * Ajoute une mati�re dans la base de donn�es
     * 
     * @param request
     * @return matiere
     */
    public Matiere creerMatiere(HttpServletRequest request) 
    {
        String nom = getValeurChamp(request, CHAMP_NOM);
        Matiere matiere = new Matiere();
        
        try 
        {
            traiterNom(nom, matiere);
            traiterMatiere(matiere);
            
            if (erreurs.isEmpty()) 
            {
            	matiereDao.creer(matiere);
            }
        } 
        catch ( Exception e ) 
        {
        	e.printStackTrace();
        }

        return matiere;
    }
    
    /**
     * Chercher une ou des mati�re(s) dans la base de donn�es
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
    	
    	if(id != null) 
    	{
    		matiere.setId(Long.parseLong(id));
    	}
    	
    	matiere.setNom(nom);
        listeMatiere = matiereDao.rechercher(matiere);
        
    	return listeMatiere;
    }
   
    /**
     * Edite une matiere dans la base de donn�es
     * 
     * @param request
     * @return matiere
     */
    public Matiere editerMatiere(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	Matiere matiere = new Matiere();
    	
    	matiere.setId(Long.parseLong(id));
    	traiterNom(nom, matiere);
    	traiterMatiere(matiere);
           
    	if (erreurs.isEmpty()) 
       	{
           matiereDao.editer(matiere);
       	}

    	return matiere;
    }
    
    /**
     * Cherche une mati�re dans la base de donn�es
     * 
     * @param request
     * @return matiere
     */
    public Matiere trouverMatiere(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Matiere matiere = new Matiere();
    	
    	matiere.setId(Long.parseLong(id));
    	matiere = matiereDao.trouver( matiere);
    	
    	return matiere;
    }
    
    /**
     * Supprime une matiere dans la base de donn�es
     * 
     * @param request
     * @return statut
     */
    public int supprimerMatiere(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Matiere matiere = new Matiere();
    	int statut;
    	
    	matiere.setId(Long.parseLong(id));
    	statut = matiereDao.supprimer(matiere);
    	
    	return statut;
    }
   
    /**
     *  Traite l'attribut : nom
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
     *  Traite l'objet : matiere
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
     * Valide l'attribut : nom
     * 
     * @param nom
     * @throws Exception
     */
    private void validationNom(String nom) throws Exception 
    {
        if ((nom == null) || (nom.length() < 3) || (nom.length() > 55)) 
        {
            throw new Exception("Veuillez entrer un nom de 3 � 55 caract�res");
        }
    }
    
    /**
     * Valide l'objet : matiere
     * 
     * @param matiere
     * @throws Exception
     */
    private void validationMatiere(Matiere matiere) throws Exception 
    {
        if (matiereDao.verifExistance(matiere) != 0) 
        {
            throw new Exception("Cette mati�re existe d�ja");
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

