package forms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import models.Etudiant;
import models.Groupe;
import models.Matiere;
import models.Professeur;
import dao.MatiereDao;
import dao.ProfesseurDao;

public class ProfesseurForm
{
	private static final String CHAMP_ID           = "id";
    private static final String CHAMP_NOM          = "nom";
    private static final String CHAMP_PRENOM       = "prenom";
    private static final String CHAMP_ADRESSE_MAIL = "adresseMail";
    private static final String CHAMP_MOT_DE_PASSE = "motDePasse";
    private static final String CHAMP_CONFIRMATION = "confirmation";
    private static final String CHAMP_GROUPES      = "groupes[]";
    private static final String CHAMP_MATIERES     = "matieres[]";
    private Map<String, String> erreurs            = new HashMap<String, String>();
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
    	Professeur professeur = new Professeur();
    	
        try 
        {
            traiterNom(nom, professeur);
            traiterPrenom(prenom, professeur);
            traiterAdresseMail(adresseMail, professeur);
            traiterMotsDePasse(motDePasse, confirmation, professeur);
            traiterProfesseur(professeur);
            
            if (matieres != null) 
        	{
            	traiterMatieres(matieres, professeur);
        	}
            if (groupes != null) 
        	{
            	traiterGroupes(groupes, professeur);
        	}
            
            if (erreurs.isEmpty()) 
            {
            	professeurDao.creer(professeur);
            }
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        return professeur;
    }
    
    /**
     * Recherche un ou des étudiant(s) dans la base de données
     * 
     * @param request
     * @return listeEtudiants
     */
    public Set<Professeur> rechercherProfesseur(HttpServletRequest request) 
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	String nom = getValeurChamp(request, CHAMP_NOM);
    	String prenom = getValeurChamp(request, CHAMP_PRENOM);
    	String adresseMail = getValeurChamp(request, CHAMP_ADRESSE_MAIL);
    	Set<Professeur> listeProfesseurs = new HashSet<Professeur>();
    	Professeur professeur = new Professeur();
    	
    	if (id != null) 
    	{
    		professeur.setId(Long.parseLong(id));
    	}
    	
    	professeur.setNom( nom );
    	professeur.setPrenom( prenom );
    	professeur.setAdresseMail( adresseMail );
    	listeProfesseurs = professeurDao.rechercher( professeur );
        
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
    	
    	professeur.setId( Long.parseLong(id));
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
    	Professeur professeur = new Professeur();
    	
        try 
        {
        	if (id != null) 
        	{
        		professeur.setId(Long.parseLong(id));
        	}
        	
            traiterNom(nom, professeur);
            traiterPrenom(prenom, professeur);
            traiterAdresseMail(adresseMail, professeur);
            traiterProfesseur(professeur);
            
            if ((motDePasse != null) || (confirmation != null))
            {
            	traiterMotsDePasse(motDePasse, confirmation, professeur);
            }
           
            if (matieres != null) 
        	{
            	traiterMatieres( matieres, professeur );
        	}
            if (groupes != null) 
        	{
            	traiterGroupes(groupes, professeur);
        	}
            
            if (erreurs.isEmpty()) 
            {
            	professeurDao.editer(professeur);
            }
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
     * @return statut
     */
    public int supprimerProfesseur(HttpServletRequest request)
    {
    	String id = getValeurChamp(request, CHAMP_ID);
    	Professeur professeur = new Professeur();
    	int statut;
    	
    	professeur.setId(Long.parseLong(id));
    	statut = professeurDao.supprimer(professeur);
    	
    	return statut;
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
     *  Traite l'attribut : nom
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
     *  Traite l'attribut : prenom
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
     *  Traite les identifiants
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
     *  Traite l'attribut : adresseMail
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
    
    /**
     *  Traite l'attribut : motDePasse
     *  
     * @param motDePasse
     * @param confirmation
     * @param professeur
     * @throws Exception 
     */
    private void traiterMotsDePasse(String motDePasse, String confirmation, Professeur professeur)  
    {		
    	try 
    	{
    		validationMotsDePasse( motDePasse, confirmation );
    		MessageDigest md = MessageDigest.getInstance("SHA-256");
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
     *  Traite les attributs : groupes
     *  
     * @param groupes
     * @param professeur
     * 
     * @throws Exception 
     */
    private void traiterGroupes(String[] groupes, Professeur professeur) 
    {
    	Set<Groupe> listeGroupes = new HashSet<Groupe>();
    	
    	for(int i=0;i<groupes.length;i++) 
    	{
    		Groupe groupe = new Groupe();
    		groupe.setId(Long.parseLong(groupes[i]));
    		listeGroupes.add(groupe);
    	}
    	
    	professeur.setListeGroupes(listeGroupes);
    }
    
    /**
     *  Traite les attributs : matieres
     *  
     * @param matieres
     * @param professeur
     * @throws Exception 
     */
    private void traiterMatieres(String[] matieres, Professeur professeur) 
    {
    	Set<Matiere> listeMatieres = new HashSet<Matiere>();
    	
    	for(int i = 0;i < matieres.length;i++) 
    	{
    		Matiere matiere = new Matiere();
    		matiere.setId(Long.parseLong(matieres[i]));
    		listeMatieres.add(matiere);
    	}
    	
    	professeur.setListeMatieres(listeMatieres);
    }
    
    /**
     *  Traiter l'objet : professeur
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
     * Valide l'attribut : prenom
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
     * Valide l'attribut : identifiant
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
     * Valide l'attribut : motDePasse
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
     * Valide l'objet : professeur
     * 
     * @param professeur
     * @throws Exception
     */
    private void validationProfesseur(Professeur professeur) throws Exception 
    {
        if (professeurDao.verifExistance(professeur) != 0) 
        {
            throw new Exception("Ce professeur existe déja");
        }
    }
    
    /**
     * Valide l'objet : identifiant
     * 
     * @param professeur
     * @throws Exception
     */
    private void validationIdentifiant(Professeur professeur) throws Exception 
    {
        if (professeur.getId() == null) 
        {
            throw new Exception("Votre adresse mail ou votre mot de passe est incorrect");
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
     * Retourne les valeurs des champs du formulaire de type multiple
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
