package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory 
{
    private static final String FICHIER_PROPERTIES       = "/dao/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE    = "motdepasse";
    private String url;
    private String username;
    private String password;

    /**
     * @param url
     * @param username
     * @param password
     */
    DAOFactory(String url, String username, String password) 
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Méthode chargée de récupérer les informations de connexion à la base de données, charger le driver JDBC et retourner une instance de la Factory
     * 
     * @return instance
     */
    public static DAOFactory getInstance() throws DAOConfigurationException 
    {
        Properties properties = new Properties();
        String url;
        String driver;
        String nomUtilisateur;
        String motDePasse;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);

        if (fichierProperties == null) 
        {
            throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
        }

        try 
        {
            properties.load(fichierProperties);
            url = properties.getProperty(PROPERTY_URL);
            driver = properties.getProperty(PROPERTY_DRIVER);
            nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
            motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSE);  
        } 
        catch (IOException e) 
        {
            throw new DAOConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
        }

        try 
        {
            Class.forName(driver);
        } 
        catch (ClassNotFoundException e) 
        {
            throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
        }

        DAOFactory instance = new DAOFactory( url, nomUtilisateur, motDePasse );
        
        return instance;
    }

    /**
     * Retourne une instance de l'objet DriverManager
     * 
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Retourne une instance de l'objet MaterielDaoImpl
     * 
     * @return MatiereDaoImpl
     */
    public MatiereDao getMatiereDao() 
    {
        return new MatiereDaoImpl(this);
    }

   /**
    * Retourne une instance de l'objet GroupeDaoImpl
    * 
    * @return GroupeDaoImpl
    */
   public GroupeDao getGroupeDao() 
   {
	   return new GroupeDaoImpl(this);
   }
   
   /**
    * Retourne une instance de l'objet EtudiantDaoImpl
    * 
    * @return EtudiantDaoImpl
    */
   public EtudiantDao getEtudiantDao() 
   {
	   return new EtudiantDaoImpl(this);
   }
   
   /**
    * Retourne une instance de l'objet ProfesseurDaoImpl
    * 
    * @return ProfesseurDaoImpl
    */
   public ProfesseurDao getProfesseurDao() 
   {
	   return new ProfesseurDaoImpl(this);
   }
   
   /**
    * Retourne une instance de l'objet AdministrateurDao
    * 
    * @return AdministrateurDaoImpl
    */
   public AdministrateurDao getAdministrateurDao() 
   {
	   return new AdministrateurDaoImpl(this);
   }
   
   /**
    * Retourne une instance de l'objet ExamenDao
    * 
    * @return ExamenDaoImpl
    */
   public ExamenDao getExamenDao() 
   {
	   return new ExamenDaoImpl(this);
   }
}