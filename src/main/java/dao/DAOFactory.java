package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
    private static final String PROPERTIES_FILE   = "dao/dao.properties";
    private static final String PROPERTY_URL      = "url";
    private static final String PROPERTY_DRIVER   = "driver";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";
    private String url;
    private String username;
    private String password;

    /**
     * Constructor
     * @param url
     * @param username
     * @param password
     */
    DAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Method in chage of get connexion to the database, charge the driver and returns an daoFactory instance
     * @return instance
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
        String      url;
        String      driver;
        String      username;
        String      password;
        Properties  properties     = new Properties();
        ClassLoader classLoader    = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new DAOConfigurationException("Le fichier properties " + PROPERTIES_FILE + " est introuvable.");
        }

        try {
            properties.load(propertiesFile);
            url = properties.getProperty(PROPERTY_URL);
            driver = properties.getProperty(PROPERTY_DRIVER);
            username = properties.getProperty(PROPERTY_USERNAME);
            password = properties.getProperty(PROPERTY_PASSWORD);
        }
        catch (IOException e) {
            throw new DAOConfigurationException("Impossible de charger le fichier properties " + PROPERTIES_FILE, e);
        }

        try {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e) {
            throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
        }

        DAOFactory instance = new DAOFactory(url, username, password);

        return instance;
    }

    /**
     * Returns an instance of DriverManager
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Returns an instance of SubjectDaoImpl
     * @return SubjectDaoImpl
     */
    public SubjectDao getSubjectDao() {
        return new SubjectDaoImpl(this);
    }

    /**
     * Returns an instance of GroupDaoImpl
     * @return GroupDaoImpl
     */
    public GroupDao getGroupDao() {
        return new GroupDaoImpl(this);
    }

    /**
     * Returns an instance of StudentDaoImpl
     * @return StudentDaoImpl
     */
    public StudentDao getStudentDao() {
        return new StudentDaoImpl(this);
    }

    /**
     * Returns an instance of TeacherDaoImpl
     * @return TeacherDaoImpl
     */
    public TeacherDao getTeacherDao() {
        return new TeacherDaoImpl(this);
    }

    /**
     * Returns an instance of AdministratorDaoImpl
     * @return AdministratorDaoImpl
     */
    public AdministratorDao getAdministratorDao() {
        return new AdministratorDaoImpl(this);
    }

    /**
     * Returns an instance of TestDaoImpl
     * @return TestDaoImpl
     */
    public TestDao getTestDao() {
        return new TestDaoImpl(this);
    }
}