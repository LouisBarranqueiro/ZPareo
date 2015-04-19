package dao;

import static dao.DAOUtility.silentClosures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.TreeSet;
import java.util.Set;

import beans.Administrator;

public class AdministratorDaoImpl implements AdministratorDao {
    private DAOFactory daoFactory;
    private static final String SELECT_COUNT_BY_EMAIL = "SELECT COUNT(id) FROM gnw_utilisateur WHERE adresse_mail = ?";
    private static final String SELECT_ALL            = "SELECT id, nom, prenom, adresse_mail  FROM gnw_utilisateur WHERE profil = 2 AND date_suppr IS NULL";
    private static final String SELECT_BY_ID          = "SELECT id, nom, prenom, adresse_mail FROM gnw_utilisateur WHERE id = ? AND date_suppr IS NULL";
    private static final String SELECT_LOGIN          = "SELECT id, nom, prenom, adresse_mail  FROM gnw_utilisateur WHERE profil = 2 AND date_suppr IS NULL AND adresse_mail = ? AND mot_de_passe = ?";
    private static final String INSERT_ADMIN          = "INSERT INTO gnw_utilisateur ( nom, prenom, adresse_mail, mot_de_passe, profil, fk_utilisateur) VALUES ( ?, ?, ?, ?, 2, ? )";
    private static final String UPDATE_ADMIN          = "UPDATE gnw_utilisateur SET nom = ?, prenom = ?, adresse_mail = ?, fk_utilisateur = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD       = "UPDATE gnw_utilisateur SET mot_de_passe = ?, fk_utilisateur = ? WHERE id = ?";
    private static final String DELETE_ADMIN          = "UPDATE gnw_utilisateur SET date_suppr = now(), fk_utilisateur = ? WHERE id = ?";

    /**
     * Returns daoFactory
     * @param daoFactory
     */
    AdministratorDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Creates an administrator into database
     * @param administrator
     * @throws DAOException
     */
    public void create(Administrator administrator) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     creator           = new Administrator(administrator.getCreator());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, INSERT_ADMIN, true, administrator.getLastName(), administrator.getFirstName(), administrator.getEmailAddress(), administrator.getPassword(), creator.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }
    }

    /**
     * Searches one or more administrators into database
     * @param administrator
     * @return administrators
     * @throws DAOException
     */
    public Set<Administrator> search(Administrator administrator) throws DAOException {
        Set<Administrator> administrators    = new TreeSet<Administrator>();
        Connection         connexion         = null;
        PreparedStatement  preparedStatement = null;
        ResultSet          resultSet         = null;
        String             SQLQuery          = SELECT_ALL;

        try {
            connexion = daoFactory.getConnection();

            if (administrator.getId() != null) {
                SQLQuery += " AND gnw_utilisateur.id = ?";
            }
            else {
                SQLQuery += " AND gnw_utilisateur.id IS NOT ?";
            }

            if (administrator.getLastName() != null) {
                SQLQuery += " AND gnw_utilisateur.nom LIKE ?";
                administrator.setLastName("%" + administrator.getLastName() + "%");
            }
            else {
                SQLQuery += " AND gnw_utilisateur.nom IS NOT ?";
            }

            if (administrator.getFirstName() != null) {
                SQLQuery += " AND gnw_utilisateur.prenom LIKE ?";
                administrator.setFirstName("%" + administrator.getFirstName() + "%");
            }
            else {
                SQLQuery += " AND gnw_utilisateur.prenom IS NOT ?";
            }

            if (administrator.getEmailAddress() != null) {
                SQLQuery += " AND gnw_utilisateur.adresse_mail LIKE ?";
                administrator.setEmailAddress("%" + administrator.getEmailAddress() + "%");
            }
            else {
                SQLQuery += " AND gnw_utilisateur.adresse_mail IS NOT ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, administrator.getId(), administrator.getLastName(), administrator.getFirstName(), administrator.getEmailAddress());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                administrator = map(resultSet);
                administrators.add(administrator);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return administrators;
    }

    /**
     * Edits an administrator into database
     * @param administrator
     * @throws DAOException
     */
    public void edit(Administrator administrator) {
        editProfile(administrator);

        if (administrator.getPassword() != null) {
            editPassword(administrator);
        }

    }

    /**
     * Edits administrator profile into database
     * @param administrator
     * @throws DAOException
     */
    public void editProfile(Administrator administrator) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(administrator.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_ADMIN, true, administrator.getLastName(), administrator.getFirstName(), administrator.getEmailAddress(), editor.getId(), administrator.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }
    }

    /**
     * Edits administrator password
     * @param administrator
     * @return administrator
     * @throws DAOException
     */
    public void editPassword(Administrator administrator) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(administrator.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_PASSWORD, true, administrator.getPassword(), editor.getId(), administrator.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

    }

    /**
     * Checks the existance of an administrator into database
     * @param administrator
     * @return status
     * @throws DAOException
     */
    public int check(Administrator administrator) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        String            SQLQuery          = SELECT_COUNT_BY_EMAIL;
        int               status            = 0;

        try {
            connexion = daoFactory.getConnection();

            if (administrator.getId() == null) {
                SQLQuery += " AND gnw_utilisateur.id IS NOT ?";
            }
            else {
                SQLQuery += " AND gnw_utilisateur.id != ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, administrator.getEmailAddress(), administrator.getId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            status = resultSet.getInt(1);
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return status;
    }

    /**
     * Returns an administrator into database
     * @param administrator
     * @return administrator
     * @throws DAOException
     */
    public Administrator get(Administrator administrator) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_BY_ID, true, administrator.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                administrator = map(resultSet);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return administrator;
    }

    /**
     * Deletes an administrator into database
     * @param administrator
     * @throws DAOException
     */
    public void delete(Administrator administrator) {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(administrator.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, DELETE_ADMIN, true, editor.getId(), administrator.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }
    }

    /**
     * Checks administrator login into database
     * @param administrator
     * @throws DAOException
     */
    public Administrator checkLogin(Administrator administrator) throws DAOException {
        Administrator     administrator2    = new Administrator();
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_LOGIN, true, administrator.getEmailAddress(), administrator.getPassword());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                administrator2 = map(resultSet);
                administrator2.setPassword(administrator.getPassword());
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return administrator2;
    }

    /**
     * Prepares a SQL query
     * @param connexion
     * @param sql
     * @param returnGeneratedKeys
     * @param objets
     * @return preparedStatement
     * @throws SQLException
     */
    public static PreparedStatement initPreparedQuery(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

        for (int i = 0; i < objets.length; i++) {
            preparedStatement.setObject(i + 1, objets[i]);
        }

        return preparedStatement;
    }

    /**
     * Maps an administrator
     * @param resultSet
     * @return adminsitrateur
     * @throws SQLException
     */
    private static Administrator map(ResultSet resultSet) throws SQLException {
        Administrator administrator = new Administrator();

        administrator.setId(resultSet.getLong("id"));
        administrator.setLastName(resultSet.getString("nom"));
        administrator.setFirstName(resultSet.getString("prenom"));
        administrator.setEmailAddress(resultSet.getString("adresse_mail"));

        return administrator;
    }

}
