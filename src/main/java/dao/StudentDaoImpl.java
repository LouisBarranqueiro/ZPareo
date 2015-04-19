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
import beans.Student;
import beans.Group;

public class StudentDaoImpl implements StudentDao {
    private DAOFactory daoFactory;
    private static final String SELECT_COUNT_BY_EMAIL   = "SELECT COUNT(id) FROM gnw_utilisateur WHERE profil = 0 AND gnw_utilisateur.adresse_mail = ?";
    private static final String SELECT_ALL              = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom FROM gnw_utilisateur, gnw_etudiant_groupe, gnw_groupe WHERE profil = 0 AND gnw_utilisateur.date_suppr IS NULL AND gnw_utilisateur.id = gnw_etudiant_groupe.fk_etudiant AND gnw_groupe.id = gnw_etudiant_groupe.fk_groupe";
    private static final String SELECT_BY_ID            = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom FROM gnw_utilisateur, gnw_etudiant_groupe, gnw_groupe WHERE profil = 0 AND gnw_utilisateur.date_suppr IS NULL AND gnw_utilisateur.id = gnw_etudiant_groupe.fk_etudiant AND gnw_groupe.id = gnw_etudiant_groupe.fk_groupe AND gnw_utilisateur.id = ?";
    private static final String SELECT_LOGIN            = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom FROM gnw_utilisateur, gnw_etudiant_groupe, gnw_groupe WHERE profil = 0 AND gnw_utilisateur.date_suppr IS NULL AND gnw_utilisateur.id = gnw_etudiant_groupe.fk_etudiant AND gnw_groupe.id = gnw_etudiant_groupe.fk_groupe AND gnw_utilisateur.adresse_mail = ? AND gnw_utilisateur.mot_de_passe = ?";
    private static final String INSERT_STUDENT          = "INSERT INTO gnw_utilisateur (nom, prenom, adresse_mail, mot_de_passe, profil, fk_utilisateur) VALUES (?, ?, ?, ?, 0, ?)";
    private static final String INSERT_GROUP            = "INSERT INTO gnw_etudiant_groupe (fk_etudiant, fk_groupe, fk_utilisateur) VALUES ( ?, ?, ? );";
    private static final String UPDATE_STUDENT          = "UPDATE gnw_utilisateur SET nom = ?, prenom = ?, adresse_mail = ?, fk_utilisateur = ? WHERE id = ?";
    private static final String UPDATE_STUDENT_PASSWORD = "UPDATE gnw_utilisateur SET mot_de_passe = ?, fk_utilisateur = ? WHERE id = ?";
    private static final String UPDATE_STUDENT_GROUP    = "UPDATE gnw_etudiant_groupe SET fk_groupe = ?, fk_utilisateur = ? WHERE fk_etudiant = ?";
    private static final String DELETE_STUDENT          = "UPDATE gnw_utilisateur SET date_suppr = now(), fk_utilisateur = ? WHERE id = ?";

    /**
     * Return daoFactory
     * @param daoFactory
     */
    StudentDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Creates a student into database
     * @param student
     * @throws DAOException
     */
    public void create(Student student) {
        createStudent(student);
        addToGroup(student);
    }

    /**
     * Creates a student into databse
     * @param student
     * @throws DAOException
     */
    public void createStudent(Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Administrator     creator           = new Administrator(student.getCreator());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, INSERT_STUDENT, true, student.getLastName(), student.getFirstName(), student.getEmailAddress(), student.getPassword(), creator.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                student.setId(resultSet.getLong(1));
            }

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }
    }

    /**
     * Add the user to a group into database
     * @param student
     * @throws DAOException
     */
    public void addToGroup(Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     creator           = new Administrator(student.getCreator());
        Group             group             = new Group(student.getGroup());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, INSERT_GROUP, true, student.getId(), group.getId(), creator.getId());
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
     * Check student login into database
     * @return student
     * @throws DAOException
     */
    public Student checkLogin(Student student) throws DAOException {
        Student           student2          = new Student();
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_LOGIN, true, student.getEmailAddress(), student.getPassword());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                student2 = map(resultSet);
                student2.setPassword(student.getPassword());
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return student2;
    }

    /**
     * Searches on or more students into database
     * @param student
     * @return students
     */
    public Set<Student> search(Student student) throws DAOException {
        Set<Student>      students          = new TreeSet<Student>();
        Group             group             = new Group(student.getGroup());
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        String            SQLQuery          = SELECT_ALL;

        try {
            connexion = daoFactory.getConnection();

            if (student.getId() != null) {
                SQLQuery += " AND gnw_utilisateur.id = ?";
            }
            else {
                SQLQuery += " AND gnw_utilisateur.id IS NOT ?";
            }

            if (student.getLastName() != null) {
                SQLQuery += " AND gnw_utilisateur.nom LIKE ?";
                student.setLastName("%" + student.getLastName() + "%");
            }
            else {
                SQLQuery += " AND gnw_utilisateur.nom IS NOT ?";
            }

            if (student.getFirstName() != null) {
                SQLQuery += " AND gnw_utilisateur.prenom LIKE ?";
                student.setFirstName("%" + student.getFirstName() + "%");
            }
            else {
                SQLQuery += " AND gnw_utilisateur.prenom IS NOT ?";
            }

            if (student.getEmailAddress() != null) {
                SQLQuery += " AND gnw_utilisateur.adresse_mail LIKE ?";
                student.setEmailAddress("%" + student.getEmailAddress() + "%");
            }
            else {
                SQLQuery += " AND gnw_utilisateur.adresse_mail IS NOT ?";
            }

            if (group.getId() != null) {
                SQLQuery += " AND gnw_groupe.id = ?";
            }
            else {
                SQLQuery += " AND gnw_groupe.id IS NOT ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, student.getId(), student.getLastName(), student.getFirstName(), student.getEmailAddress(), group.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                student = map(resultSet);
                students.add(student);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return students;
    }

    /**
     * Edits a student into database
     * @param student
     */
    public void edit(Student student) {
        editProfile(student);
    }

    /**
     * Edits a student profile into database
     * @param student
     * @throws DAOException
     */
    private void editProfile(Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(student.getEditor());
        Group             group             = new Group(student.getGroup());
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_STUDENT, true, student.getLastName(), student.getFirstName(), student.getEmailAddress(), editor.getId(), student.getId());
            preparedStatement.executeUpdate();
            preparedStatement = initPreparedQuery(connexion, UPDATE_STUDENT_GROUP, true, group.getId(), editor.getId(), student.getId());
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
     * Edits student password into database
     * @param student
     * @throws DAOException
     */
    public void editPassword(Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(student.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_STUDENT_PASSWORD, true, student.getPassword(), editor.getId(), student.getId());
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
     * Returns a student profile into database
     * @param student
     * @return student
     * @throws DAOException
     */
    public Student getProfile(Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_BY_ID, true, student.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                student = map(resultSet);
            }

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return student;
    }

    /**
     * Checks the existance of a student into database
     * @param student
     * @return status
     * @throws DAOException
     */
    public int check(Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        String            SQLQuery          = SELECT_COUNT_BY_EMAIL;
        int               status;

        try {
            connexion = daoFactory.getConnection();

            if (student.getId() == null) {
                SQLQuery += " AND gnw_utilisateur.id IS NOT ?";
            }
            else {
                SQLQuery += " AND gnw_utilisateur.id != ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, student.getEmailAddress(), student.getId());
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
     * Returns all student informations
     * @param student
     * @return student
     */
    public Student getAll(Student student) {
        TestDaoImpl testDao = new TestDaoImpl(daoFactory);

        student.setGradebook(testDao.getGradebook(student));

        return student;
    }

    /**
     * Deletes a student into database
     * @param student
     * @return status
     * @throws DAOException
     */
    public void delete(Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(student.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, DELETE_STUDENT, true, editor.getId(), student.getId());
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
     * Prepare a sql query
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
     * Maps a Student
     * @param resultSet
     * @return student
     * @throws SQLException
     */
    private static Student map(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        Group   group   = new Group();

        student.setId(resultSet.getLong("id"));
        student.setLastName(resultSet.getString("nom"));
        student.setFirstName(resultSet.getString("prenom"));
        student.setEmailAddress(resultSet.getString("adresse_mail"));
        group.setId(resultSet.getLong("groupeId"));
        group.setName(resultSet.getString("groupeNom"));
        student.setGroup(group);

        return student;
    }

}
