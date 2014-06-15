package forms;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import beans.Administrateur;
import dao.AdministrateurDao;

public class AdministrateurForm
{
	private static final String CHAMP_ID           = "id";
    private static final String CHAMP_NOM          = "nom";
    private static final String CHAMP_PRENOM       = "prenom";
    private static final String CHAMP_ADRESSE_MAIL = "adresseMail";
    private static final String CHAMP_MOT_DE_PASSE = "motDePasse";
    private static final String CHAMP_CONFIRMATION = "confirmation";
    private Map<String, String> erreurs            = new HashMap<String, String>();
	private AdministrateurDao administrateurDao;
	 
	/**
     * Constructeur
     * 
     * @param administrateurDao
     */
    public AdministrateurForm(AdministrateurDao administrateurDao) 
    {
    	this.administrateurDao = administrateurDao;
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
     * Crée un administrateur dans la base de données
     * 
     * @param sessionAdministrateur
     * @param request
     * @return administrateur
     */
    public Administrateur creerAdministrateur(Administrateur sessionAdministrateur, HttpServletRequest request) 
    {
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String prenom = getValeurChamp(request, CHAMP_PRENOM);
    	String adresseMail = getValeurChamp(request, CHAMP_ADRESSE_MAIL);
    	String motDePasse = getValeurChamp(request, CHAMP_MOT_DE_PASSE);
    	String confirmation = getValeurChamp(request, CHAMP_CONFIRMATION);
    	Administrateur administrateur = new Administrateur();
    	
        try 
        {
            traiterNom(nom, administrateur);
            traiterPrenom(prenom, administrateur);
            traiterAdresseMail(adresseMail, administrateur);
            traiterMotsDePasse(motDePasse, confirmation, administrateur);
            traiterAdministrateur(administrateur);
            
            if (erreurs.isEmpty()) 
            {
            	administrateurDao.creer(sessionAdministrateur, administrateur);
            }
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return administrateur;
    }
    
    /**
     * Recherche un ou des administrateur(s) dans la base de données
     * 
     * @param request
     * @return listeAdministrateurs
     */
    public Set<Administrateur> rechercherAdministrateur(HttpServletRequest request) 
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String prenom = getValeurChamp(request, CHAMP_PRENOM);
    	String adresseMail = getValeurChamp(request, CHAMP_ADRESSE_MAIL);
    	Set<Administrateur> listeAdministrateurs = new HashSet<Administrateur>();
    	Administrateur administrateur = new Administrateur();
    	
    	if (id != null) 
    	{
    		administrateur.setId(Long.parseLong(id));
    	}
    	
    	administrateur.setNom(nom);
    	administrateur.setPrenom(prenom);
    	administrateur.setAdresseMail(adresseMail);
    	listeAdministrateurs = administrateurDao.rechercher(administrateur);
        
    	return listeAdministrateurs;
    }
    
    /**
     * Cherche un administrateur dans la base de données
     * 
     * @param request
     * @return administrateur
     */
    public Administrateur trouverAdministrateur(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Administrateur administrateur = new Administrateur();
    	
    	administrateur.setId( Long.parseLong(id));
    	administrateur = administrateurDao.trouver(administrateur);
    	
    	return administrateur;
    }
    
    /**
     * Edite un administrateur dans la base de données
     * 
     * @param utilisateur
     * @param request
     * @return administrateur
     */
    public Administrateur editerAdministrateur(Administrateur utilisateur, HttpServletRequest request) 
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String prenom = getValeurChamp(request, CHAMP_PRENOM);
    	String adresseMail = getValeurChamp(request, CHAMP_ADRESSE_MAIL);
    	String motDePasse = getValeurChamp(request, CHAMP_MOT_DE_PASSE);
    	String confirmation = getValeurChamp(request, CHAMP_CONFIRMATION);
    	Administrateur administrateur = new Administrateur();
    	
        try 
        {
        	if (id != null) 
        	{
        		administrateur.setId(Long.parseLong(id));
        	}
        	
            traiterNom(nom, administrateur);
            traiterPrenom(prenom, administrateur);
            traiterAdresseMail(adresseMail, administrateur);
            traiterAdministrateur(administrateur);
            
            if ((motDePasse != null) || (confirmation != null))
            {
            	traiterMotsDePasse(motDePasse, confirmation, administrateur);
            }
           
            if (erreurs.isEmpty()) 
            {
            	administrateurDao.editer(utilisateur, administrateur);
            }
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return administrateur;
    }
    
    /**
     * Supprime un administrateur dans la base de données
     * 
     * @param utilisateur
     * @param request
     */
    public void supprimerAdministrateur(Administrateur utilisateur, HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Administrateur administrateur = new Administrateur();
    	
    	administrateur.setId(Long.parseLong(id));
    	administrateurDao.supprimer(utilisateur, administrateur);
    }
    
    /**
     * Vérifie les identifiants d'un administrateur dans la base de données
     * 
     * @param request
     * @return administrateur
     */
    public Administrateur verifIdentifiantAdmin(HttpServletRequest request) 
    {
    	String adresseMail = getValeurChamp(request, CHAMP_ADRESSE_MAIL);
    	String motDePasse = getValeurChamp(request, CHAMP_MOT_DE_PASSE);
    	Administrateur administrateur = new Administrateur();
    	
        try 
        {
        	traiterAdresseMail(adresseMail, administrateur);
        	administrateur.setMotDePasse(crypterMotDePasse(motDePasse));
        	administrateur  = administrateurDao.verifIdentifiant(administrateur);
        	traiterIdentifiant(administrateur);
        	administrateur.setMotDePasse(crypterMotDePasse(motDePasse));
        	administrateur.setAdresseMail(adresseMail);
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return administrateur;
    }
    
    /**
     *  Traite le nom de l'administrateur
     *  
     * @param nom
     * @param administrateur
     */
    private void traiterNom(String nom, Administrateur administrateur)
    {
    	try 
    	{
            validationNom(nom);
        } 
    	catch (Exception e) 
    	{
            setErreur(CHAMP_NOM, e.getMessage());
        }
    	
    	administrateur.setNom(nom.substring(0, 1).toUpperCase() + nom.substring(1).toLowerCase());
    }
    
    /**
     *  Traite le prenom d'un administrateur
     *  
     * @param prenom
     * @param administrateur
     */
    private void traiterPrenom(String prenom, Administrateur administrateur)
    {
    	try 
    	{
            validationPrenom(prenom);
        } 
    	catch (Exception e) 
    	{
            setErreur(CHAMP_PRENOM, e.getMessage());
        }
    	
    	administrateur.setPrenom(prenom.substring(0, 1).toUpperCase() + prenom.substring(1).toLowerCase());
    }
    
    /**
     *  Traite l'adresse mail d'un admiinstrateur
     *  
     * @param adresseMail
     * @param administrateur
     */
    private void traiterAdresseMail(String adresseMail, Administrateur administrateur)  
    {
    	try 
    	{
            validationAdresseMail(adresseMail);
        } 
    	catch (Exception e) 
    	{
            setErreur(CHAMP_ADRESSE_MAIL, e.getMessage());
        }
    	
    	administrateur.setAdresseMail(adresseMail.trim().toLowerCase());
    }
    
    /**
     *  Traite le mot de passe d'un administrateur
     *  
     * @param motDePasse
     * @param confirmation
     * @param administrateur
     */
    private void traiterMotsDePasse(String motDePasse, String confirmation, Administrateur administrateur)  
    {		
    	try 
    	{
    		validationMotsDePasse( motDePasse, confirmation );
            motDePasse = crypterMotDePasse(motDePasse);
    	} 
    	catch (Exception e)
    	{
    		setErreur(CHAMP_MOT_DE_PASSE, e.getMessage());
    		setErreur(CHAMP_CONFIRMATION, null);
    	} 

    	administrateur.setMotDePasse(motDePasse);
    }

    /**
     *  Traite les identifiants d'un administrateur
     *  
     * @param administrateur
     */
    private void traiterIdentifiant(Administrateur administrateur) 
    {
    	try 
    	{
    		validationIdentifiant(administrateur);
        } 
    	catch (Exception e) 
    	{
            setErreur("connexion", e.getMessage());
        }
    }
    
    /**
     *  Traiter un administrateur
     *  
     * @param administrateur
     */
    private void traiterAdministrateur(Administrateur administrateur) 
    {
    	try 
    	{
    		validationAdministrateur(administrateur);
        } 
    	catch (Exception e) 
    	{
            setErreur("administrateur", e.getMessage());
        }
    }
    
    /**
     * Valide le nom d'un administrateur
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
     * Valide le prenom d'un administrateur
     * 
     * @param prenom
     * @throws Exception
     */
    private void validationPrenom(String prenom) throws Exception 
    {
        if ((prenom == null) || (prenom.length() < 2) || (prenom.length() > 50)) 
        {
            throw new Exception( "Veuillez entrer un prenom de 2 � 50 caractères" );
        }
    }
    
    /**
     * Valide l'adrese mail d'un administrateur
     * 
     * @param adresseMail
     * @throws Exception
     */
    private void validationAdresseMail(String adresseMail) throws Exception 
    {
        if ((adresseMail == null) || (adresseMail.length() < 8) || (adresseMail.length() > 100 ) || (!adresseMail.matches("[a-zA-Z0-9@.-_]+@[a-zA-Z]{2,20}.[a-zA-Z]{2,3}"))) 
        {
            throw new Exception("Veuillez entrer une adresse mail correcte");
        }
    }
    
    /**
     * Valide le mot de passe d'un administrateur
     * 
     * @param motDePasse
     * @throws Exception
     */
    private void validationMotsDePasse(String motDePasse, String confirmation) throws Exception 
    {
        if ((motDePasse == null) || (motDePasse.length() < 4) || (confirmation == null) || (confirmation.length() < 4)) 
        {
            throw new Exception("Veuillez entrez un mot de passe plus fort.");
        } 
        else if (!motDePasse.equals(confirmation)) 
        {
            throw new Exception("Les mots de passes sont différents");
        } 
    }
    
    /**
     * Valide l'administrateur
     * 
     * @param administrateur
     * @throws Exception
     */
    private void validationAdministrateur(Administrateur administrateur) throws Exception 
    {
        if (administrateurDao.verifExistance(administrateur) != 0) 
        {
            throw new Exception("Cet administrateur existe déja");
        }
    }
    
    /**
     * Valide les identifiants d'un administrateur
     * 
     * @param administrateur
     * @throws Exception
     */
    private void validationIdentifiant(Administrateur administrateur) throws Exception 
    {
        if (administrateur.getId() == null) 
        {
            throw new Exception("Votre adresse mail ou votre mot de passe est incorrect");
        }
    }
    
    /**
     * Crypte un mot de passe en SHA-256
     * 
     * @param motDePasse
     * @return motDePasseCrypte
     */
    private String crypterMotDePasse(String motDePasse)
    {
    	StringBuffer motDePasseCrypte = new StringBuffer();
    	
    	try 
    	{
    		MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(motDePasse.getBytes());
            byte byteData[] = md.digest();
            
            for (int i = 0; i < byteData.length; i++) 
            {
            	motDePasseCrypte.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
    	} 
    	catch (Exception e)
    	{
    		
    	} 

    	return motDePasseCrypte.toString();
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
     * Retourne la valeur d'un champ du formulaire
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
