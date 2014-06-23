package forms;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Administrateur;
import beans.Professeur;
import beans.Groupe;
import beans.Matiere;
import dao.ProfesseurDao;

public class ProfesseurForm
{
	private static final String SESSION_ADMINISTRATEUR = "sessionAdministrateur";
	private static final String CHAMP_ID               = "id";
    private static final String CHAMP_NOM              = "nom";
    private static final String CHAMP_PRENOM           = "prenom";
    private static final String CHAMP_ADRESSE_MAIL     = "adresseMail";
    private static final String CHAMP_MOT_DE_PASSE     = "motDePasse";
    private static final String CHAMP_CONFIRMATION     = "confirmation";
    private static final String CHAMP_GROUPES          = "groupes[]";
    private static final String CHAMP_MATIERES         = "matieres[]";
    private Map<String, String> erreurs                = new HashMap<String, String>();
	private ProfesseurDao professeurDao;
	 
	/**
     * Récupère l'objet : professeurDao
     * 
     * @param professeurDao
     */
    public ProfesseurForm(ProfesseurDao professeurDao) 
    {
    	this.professeurDao = professeurDao;
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
     * Crée un professeur dans la base de données
     * 
     * @param request
     * @return professeur
     */
    public Professeur creerProfesseur(HttpServletRequest request) 
    {
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String prenom = getValeurChamp(request, CHAMP_PRENOM);
    	String adresseMail = getValeurChamp(request, CHAMP_ADRESSE_MAIL);
    	String motDePasse = getValeurChamp(request, CHAMP_MOT_DE_PASSE);
    	String confirmation = getValeurChamp(request, CHAMP_CONFIRMATION);
    	String[] groupes = getValeursChamp(request, CHAMP_GROUPES);
    	String[] matieres = getValeursChamp(request, CHAMP_MATIERES);
    	Administrateur createur = (Administrateur) getValeurSession(request, SESSION_ADMINISTRATEUR);
    	Professeur professeur = new Professeur();
    	
        try 
        {
            traiterNom(nom, professeur);
            traiterPrenom(prenom, professeur);
            traiterAdresseMail(adresseMail, professeur);
            traiterMotsDePasse(motDePasse, confirmation, professeur);
            traiterProfesseur(professeur);
            traiterMatieres(matieres, professeur);
        	traiterGroupes(groupes, professeur);
        	traiterCreateur(createur, professeur);
        	
            if (erreurs.isEmpty()) professeurDao.creer(professeur);
            
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return professeur;
    }
    
    /**
     * Recherche un ou des professeur(s) dans la base de données
     * 
     * @param request
     * @return listeProfesseurs
     */
    public Set<Professeur> rechercherProfesseur(HttpServletRequest request) 
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String prenom = getValeurChamp(request, CHAMP_PRENOM);
    	String adresseMail = getValeurChamp(request, CHAMP_ADRESSE_MAIL);
    	Set<Professeur> listeProfesseurs = new HashSet<Professeur>();
    	Professeur professeur = new Professeur();
    	
    	if (id != null) professeur.setId(Long.parseLong(id));
    
    	professeur.setNom(nom);
    	professeur.setPrenom(prenom);
    	professeur.setAdresseMail(adresseMail);
    	listeProfesseurs = professeurDao.rechercher(professeur);
        
    	return listeProfesseurs;
    }
    
    /**
     * Cherche un professeur dans la base de données
     * 
     * @param request
     * @return professeur
     */
    public Professeur trouverProfesseur(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Professeur professeur = new Professeur();
    	
    	traiterId(id, professeur);
    	professeur = professeurDao.trouver(professeur);
    	
    	return professeur;
    }
    
    /**
     * Edite un professeur dans la base de données
     * 
     * @param request
     * @return professeur
     */
    public Professeur editerProfesseur(HttpServletRequest request) 
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String prenom = getValeurChamp(request, CHAMP_PRENOM);
    	String adresseMail = getValeurChamp(request, CHAMP_ADRESSE_MAIL);
    	String motDePasse = getValeurChamp(request, CHAMP_MOT_DE_PASSE);
    	String confirmation = getValeurChamp(request, CHAMP_CONFIRMATION);
    	String[] groupes = getValeursChamp(request, CHAMP_GROUPES);
    	String[] matieres = getValeursChamp(request, CHAMP_MATIERES);
    	Administrateur editeur = (Administrateur) getValeurSession(request, SESSION_ADMINISTRATEUR);
    	Professeur professeur = new Professeur();
    	
        try 
        {
        	traiterId(id, professeur);
            traiterNom(nom, professeur);
            traiterPrenom(prenom, professeur);
            traiterAdresseMail(adresseMail, professeur);
            traiterProfesseur(professeur);
            traiterMatieres(matieres, professeur);
            traiterGroupes(groupes, professeur);
            traiterEditeur(editeur, professeur);
            
            if ((motDePasse != null) || (confirmation != null)) traiterMotsDePasse(motDePasse, confirmation, professeur);
            
            if (erreurs.isEmpty()) professeurDao.editer(professeur);
            
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return professeur;
    }
    
    /**
     * Supprime un professeur dans la base de données
     * 
     * @param request
     */
    public void supprimerProfesseur(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Administrateur editeur = (Administrateur) getValeurSession(request, SESSION_ADMINISTRATEUR);
    	Professeur professeur = new Professeur();
    	
    	traiterId(id, professeur);
    	traiterEditeur(editeur, professeur);
    	professeurDao.supprimer(professeur);

    }
    
    /**
     * Vérifie les identifiants d'un professeur dans la base de données
     * 
     * @param request
     * @return professeur
     */
    public Professeur verifIdentifiantProfesseur(HttpServletRequest request) 
    {
    	String adresseMail = getValeurChamp(request, CHAMP_ADRESSE_MAIL);
    	String motDePasse = getValeurChamp(request, CHAMP_MOT_DE_PASSE);
    	Professeur professeur = new Professeur();
    	
        try 
        {
        	traiterAdresseMail(adresseMail, professeur);
        	professeur.setMotDePasse(crypterMotDePasse(motDePasse));
        	professeur  = professeurDao.verifIdentifiant(professeur);
        	traiterIdentifiant(professeur);
        	professeur.setMotDePasse(crypterMotDePasse(motDePasse));
        	professeur.setAdresseMail(adresseMail);
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return professeur;
    }
    
    /**
     *  Traite le numéro d'identification d'un professeur
     *  
     * @param id
     * @param professeur
     */
    private void traiterId(String id, Professeur professeur)
    {
    	try
    	{
    		validationId(id);
    		professeur.setId(Long.parseLong(id));
    	}
    	catch (Exception e) 
    	{
            setErreur(CHAMP_ID, e.getMessage());
        }
    	
    }
    /**
     *  Traite le nom d'un professeur
     *  
     * @param nom
     * @param professeur
     */
    private void traiterNom(String nom, Professeur professeur)
    {
    	try 
    	{
            validationNom(nom);
        } 
    	catch (Exception e) 
    	{
            setErreur(CHAMP_NOM, e.getMessage());
        }
    	
    	professeur.setNom(nom.substring(0, 1).toUpperCase() + nom.substring(1).toLowerCase());
    }
    
    /**
     *  Traite le prenom d'un professeur
     *  
     * @param prenom
     * @param professeur
     */
    private void traiterPrenom(String prenom, Professeur professeur)
    {
    	try 
    	{
            validationPrenom(prenom);
        } 
    	catch (Exception e) 
    	{
            setErreur(CHAMP_PRENOM, e.getMessage());
        }
    	
    	professeur.setPrenom(prenom.substring(0, 1).toUpperCase() + prenom.substring(1).toLowerCase());
    }
    
    /**
     *  Traite les identifiants d'un professeur
     *  
     * @param etudiant
     */
    private void traiterIdentifiant(Professeur professeur) 
    {
    	try 
    	{
    		validationIdentifiant(professeur);
        } 
    	catch (Exception e) 
    	{
            setErreur("connexion", e.getMessage());
        }
    }
    
    /**
     *  Traite l'adresse mail d'un professeur
     *  
     * @param identifiant
     * @param professeur
     */
    private void traiterAdresseMail(String adresseMail, Professeur professeur)  
    {
    	try 
    	{
            validationAdresseMail(adresseMail);
        } 
    	catch (Exception e) 
    	{
            setErreur(CHAMP_ADRESSE_MAIL, e.getMessage());
        }
    	
    	professeur.setAdresseMail(adresseMail.trim().toLowerCase());
    }
    
    /** Traite le mot de passe d'un professeur
     *  
     * @param motDePasse
     * @param confirmation
     * @param professeur
     */
    private void traiterMotsDePasse(String motDePasse, String confirmation, Professeur professeur)  
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

    	professeur.setMotDePasse(motDePasse);
    }

    /**
     *  Traite les groupes d'un professeur
     *  
     * @param groupes
     * @param professeur 
     */
    private void traiterGroupes(String[] groupes, Professeur professeur) 
    {
    	Set<Groupe> listeGroupes = new HashSet<Groupe>();
    	
    	if (validationGroupes(groupes))
    	{
    		for(int i=0;i<groupes.length;i++) 
        	{
        		Groupe groupe = new Groupe();
        		groupe.setId(Long.parseLong(groupes[i]));
        		listeGroupes.add(groupe);
        	}
        	
        	professeur.setListeGroupes(listeGroupes);
    	}
    }
    
    /**
     *  Traite les matières d'un professeur
     *  
     * @param matieres
     * @param professeur
     */
    private void traiterMatieres(String[] matieres, Professeur professeur) 
    {
    	Set<Matiere> listeMatieres = new HashSet<Matiere>();
    	
    	if (validationMatieres(matieres))
    	{
    		for (int i = 0;i < matieres.length;i++) 
            {
            	Matiere matiere = new Matiere();
            	matiere.setId(Long.parseLong(matieres[i]));
            	listeMatieres.add(matiere);
            }
    		
    		professeur.setListeMatieres(listeMatieres);
    	}
    }
    
    /**
     *  Traite un professeur
     *  
     * @param professeur
     */
    private void traiterProfesseur(Professeur professeur) 
    {
    	try 
    	{
    		validationProfesseur(professeur);
        } 
    	catch (Exception e) 
    	{
            setErreur("professeur", e.getMessage());
        }
    }
    
    /**
     *  Traite le créateur d'un administrateur
     *  
     * @param createur
     */
    private void traiterCreateur(Administrateur createur, Professeur professeur) 
    {
    	try 
    	{
    		validationCreateur(createur);
        } 
    	catch (Exception e) 
    	{
            setErreur("administrateur", e.getMessage());
        }
    	
    	professeur.setCreateur(createur);	
    }
    
    /**
     *  Traite le créateur d'un administrateur
     *  
     * @param createur
     */
    private void traiterEditeur(Administrateur editeur, Professeur professeur) 
    {
    	try 
    	{
    		validationCreateur(editeur);
        } 
    	catch (Exception e) 
    	{
            setErreur("administrateur", e.getMessage());
        }
    	
    	professeur.setEditeur(editeur);
    }
    
    /**
     * Valide le numéro d'identification d'un professeur
     * 
     * @param id
     * @throws Exception
     */
    private void validationId(String id) throws Exception 
    {
        if ((id == null)) throw new Exception("Le numéro d'identification est nul");  
    }
    
    /**
     * Valide le nom d'un professeur
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
     * Valide le prenom d'un prenom
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
     * Valide les identifiants d'un professeur
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
     * Valide les groupes d'un professeur
     * 
     * @param groupes
     */
    private boolean validationGroupes(String[] groupes) 
    {
        return (groupes != null);    
    }
    
    /**
     * Valide les matières d'un professeur
     * 
     * @param matieres
     */
    private boolean validationMatieres(String[] matieres) 
    {
        return (matieres != null); 
     
    }
    
    /**
     * Valide l'attribut : motDePasse
     * 
     * @param motDePasse
     * @param confirmation
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
     * Valide un professeur
     * 
     * @param professeur
     * @throws Exception
     */
    private void validationProfesseur(Professeur professeur) throws Exception 
    {
        if (professeurDao.verifExistance(professeur) != 0) throw new Exception("Ce professeur existe déja"); 
    }
    
    /**
     * Valide les identifiants d'un professeur
     * 
     * @param professeur
     * @throws Exception
     */
    private void validationIdentifiant(Professeur professeur) throws Exception 
    {
        if (professeur.getId() == null) throw new Exception("Votre adresse mail ou votre mot de passe est incorrect"); 
    }
    
    /**
     * Valide le créateur d'un professeur
     * 
     * @param createur
     * @throws Exception
     */
    private void validationCreateur(Administrateur createur) throws Exception 
    {
        if (createur.getId() == null) 
        {
            throw new Exception("Administrateur inconnu");
        }
    }
    
    /**
     * Crypte un mot de passe
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
    
    /**
     * Retourne les valeurs d'un paramètre de la requête
     * 
     * @param request
     * @param nomChamp
     * @return valeur
     */
    private static String[] getValeursChamp(HttpServletRequest request, String nomChamp) 
    {
    	return request.getParameterValues(nomChamp);
    }
    
}
