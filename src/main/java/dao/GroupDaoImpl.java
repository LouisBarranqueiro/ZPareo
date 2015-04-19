package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dao.DAOUtility.*;

import java.util.TreeSet;
import java.util.Set;

import beans.Administrator;
import beans.Group;

public class GroupDaoImpl implements GroupDao {
    private DAOFactory daoFactory;
    private static final String SELECT_COUNT_BY_NAME = "SELECT COUNT(id) FROM gnw_groupe WHERE date_suppr IS NULL AND nom = ?";
    private static final String SELECT_ALL           = "SELECT id, nom FROM gnw_groupe WHERE date_suppr IS NULL";
    private static final String SELECT_BY_ID         = "SELECT id, nom FROM gnw_groupe WHERE id = ? AND date_suppr IS NULL";
    private static final String INSERT               = "INSERT INTO gnw_groupe (nom, fk_utilisateur) VALUES (?, ?)";
    private static final String UPDATE               = "UPDATE gnw_groupe SET nom = ?, date_modif = now(), fk_utilisateur = ? WHERE id = ?";
    private static final String DELETE               = "UPDATE gnw_groupe SET date_suppr = now(), fk_utilisateur = ? WHERE id = ?";

    /**
     * Returns daoFactory
     * @param daoFactory
     */
    GroupDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Creates a group into database
     * @param group
     * @throws DAOException
     */
    public void create(Group group) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     creator           = new Administrator(group.getCreator());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, INSERT, true, group.getName(), creator.getId());
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
     * Searches one or more groups into database
     * @param group
     * @return groupes
     * @throws DAOException
     */
    public Set<Group> search(Group group) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Set<Group>        groupes           = new TreeSet<Group>();
        String            SQLQuery          = SELECT_ALL;

        try {
            connexion = daoFactory.getConnection();

            if (group.getId() != null) {
                SQLQuery += " AND gnw_groupe.id = ?";
            }
            else {
                SQLQuery += " AND gnw_groupe.id IS NOT ?";
            }

            if (group.getName() != null) {
                SQLQuery += " AND gnw_groupe.nom LIKE ?";
                group.setName("%" + group.getName() + "%");
            }
            else {
                SQLQuery += " AND gnw_groupe.nom IS NOT ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, group.getId(), group.getName());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                group = map(resultSet);
                groupes.add(group);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return groupes;
    }

    /**
     * Edits a group into database
     * @param group
     * @throws DAOException
     */
    public void edit(Group group) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(group.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE, true, group.getName(), editor.getId(), group.getId());
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
     * Checks the existance of a group into database
     * @param group
     * @return status
     * @throws DAOException
     */
    public int check(Group group) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        String            SQLQuery          = SELECT_COUNT_BY_NAME;
        int               status            = 0;

        try {
            connexion = daoFactory.getConnection();

            if (group.getId() == null) {
                SQLQuery += " AND id IS NOT ?";
            }
            else {
                SQLQuery += " AND id != ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, group.getName(), group.getId());
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
     * Returns a group into database
     * @param group
     * @return group
     * @throws DAOException
     */
    public Group get(Group group) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_BY_ID, true, group.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                group = map(resultSet);
            }

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return group;
    }

    /**
     * Deletes a group into database
     * @param group
     * @throws DAOException
     */
    public void delete(Group group) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(group.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, DELETE, true, editor.getId(), group.getId());
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
     * Maps a Group
     * @param resultSet
     * @return group
     * @throws SQLException
     */
    private static Group map(ResultSet resultSet) throws SQLException {
        Group group = new Group();

        group.setId(resultSet.getLong("id"));
        group.setName(resultSet.getString("nom"));

        return group;
    }
}
