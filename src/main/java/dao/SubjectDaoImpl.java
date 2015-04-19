package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dao.DAOUtility.*;

import java.util.Set;
import java.util.TreeSet;

import beans.Administrator;
import beans.Subject;

public class SubjectDaoImpl implements SubjectDao {
    private DAOFactory daoFactory;
    private static final String SELECT_COUNT_BY_NAME = "SELECT COUNT(id) FROM gnw_matiere WHERE date_suppr IS NULL AND nom = ?";
    private static final String SELECT_ALL           = "SELECT id, nom FROM gnw_matiere WHERE date_suppr IS NULL";
    private static final String SELECT_BY_ID         = "SELECT id, nom FROM gnw_matiere WHERE id = ? AND date_suppr IS NULL ORDER BY id ASC";
    private static final String INSERT               = "INSERT INTO gnw_matiere (nom, fk_utilisateur) VALUES (?, ?)";
    private static final String UPDATE               = "UPDATE gnw_matiere SET nom = ?, fk_utilisateur = ? WHERE id = ?";
    private static final String DELETE               = "UPDATE gnw_matiere SET date_suppr = now(), fk_utilisateur = ? WHERE id = ?";

    /**
     * Returns daoFactory
     * @param daoFactory
     */
    SubjectDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Creates a subject into database
     * @param subject
     * @throws DAOException
     */
    public void create(Subject subject) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     creator           = new Administrator(subject.getCreator());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, INSERT, true, subject.getName(), creator.getId());
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
     * Searches one or more subject into database
     * @param subject
     * @return subjects
     * @throws DAOException
     */
    public Set<Subject> search(Subject subject) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Set<Subject>      subjects          = new TreeSet<Subject>();
        String            SQLQuery          = SELECT_ALL;

        try {
            connexion = daoFactory.getConnection();

            if (subject.getId() != null) {
                SQLQuery += " AND gnw_matiere.id = ?";
            }
            else {
                SQLQuery += " AND gnw_matiere.id IS NOT ?";
            }

            if (subject.getName() != null) {
                SQLQuery += " AND gnw_matiere.nom LIKE ?";
                subject.setName("%" + subject.getName() + "%");
            }
            else {
                SQLQuery += " AND gnw_matiere.nom IS NOT ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, subject.getId(), subject.getName());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                subject = map(resultSet);
                subjects.add(subject);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return subjects;
    }

    /**
     * Edits a subject into database
     * @param subject
     * @throws DAOException
     */
    public void edit(Subject subject) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(subject.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE, true, subject.getName(), editor.getId(), subject.getId());
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
     * Checks the existance of a subject into database
     * @param subject
     * @return status
     */
    public int check(Subject subject) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        String            SQLQuery          = SELECT_COUNT_BY_NAME;
        int               status            = 0;

        try {
            connexion = daoFactory.getConnection();

            if (subject.getId() == null) {
                SQLQuery += " AND id IS NOT ?";
            }
            else {
                SQLQuery += " AND id != ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, subject.getName(), subject.getId());
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
     * Returns a subject into database
     * @param subject
     * @return subject
     * @throws DAOException
     */
    public Subject get(Subject subject) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_BY_ID, true, subject.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                subject = map(resultSet);
            }

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return subject;
    }

    /**
     * Deletes a subject into database
     * @param subject
     * @return status
     * @throws DAOException
     */
    public void delete(Subject subject) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(subject.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, DELETE, true, editor.getId(), subject.getId());
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
     * Maps a Subject
     * @param resultSet
     * @return matter
     * @throws SQLException
     */
    private static Subject map(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();

        subject.setId(resultSet.getLong("id"));
        subject.setName(resultSet.getString("nom"));

        return subject;
    }
}
