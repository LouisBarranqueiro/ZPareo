package dao;

import static dao.DAOUtility.silentClosures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import beans.Administrator;
import beans.Subject;
import beans.Teacher;
import beans.Group;

public class TeacherDaoImpl implements TeacherDao {
    private DAOFactory daoFactory;
    private static final String SELECT_COUNT_BY_EMAIL = "SELECT COUNT(id) FROM gnw_utilisateur WHERE profil = 1 AND adresse_mail = ?";
    private static final String SELECT_ALL            = "SELECT id, nom, prenom, adresse_mail  FROM gnw_utilisateur WHERE profil = 1 AND date_suppr IS NULL";
    private static final String SELECT_SUBJECTS        = "SELECT gnw_professeur_matiere.fk_matiere as matiereId, gnw_matiere.nom as matiereNom FROM gnw_professeur_matiere, gnw_matiere WHERE gnw_professeur_matiere.date_suppr IS NULL AND gnw_professeur_matiere.fk_professeur = ? AND gnw_professeur_matiere.fk_matiere = gnw_matiere.id";
    private static final String SELECT_GROUPS         = "SELECT gnw_professeur_groupe.fk_groupe as groupeId, gnw_groupe.nom as groupeNom FROM gnw_professeur_groupe, gnw_groupe WHERE gnw_professeur_groupe.date_suppr IS NULL AND gnw_professeur_groupe.fk_professeur = ? AND gnw_professeur_groupe.fk_groupe = gnw_groupe.id";
    private static final String SELECT_BY_ID          = "SELECT id, nom, prenom, adresse_mail FROM gnw_utilisateur WHERE id = ? AND date_suppr IS NULL";
    private static final String SELECT_LOGIN          = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail FROM gnw_utilisateur WHERE gnw_utilisateur.profil = 1 AND gnw_utilisateur.adresse_mail = ? AND gnw_utilisateur.mot_de_passe = ?";
    private static final String SELECT_TEACHER        = "INSERT INTO gnw_utilisateur ( nom, prenom, adresse_mail, mot_de_passe, profil, fk_utilisateur ) VALUES (?, ?, ?, ?, 1, ?)";
    private static final String INSERT_SUBJECT         = "INSERT INTO gnw_professeur_matiere ( fk_professeur, fk_matiere, fk_utilisateur ) VALUES (?, ?, ?)";
    private static final String INSERT_GROUP          = "INSERT INTO gnw_professeur_groupe ( fk_professeur, fk_groupe, fk_utilisateur ) VALUES (?, ?, ?)";
    private static final String UPDATE_TEACHER        = "UPDATE gnw_utilisateur SET nom = ?, prenom = ?, adresse_mail = ?, fk_utilisateur = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD       = "UPDATE gnw_utilisateur SET mot_de_passe = ?, fk_utilisateur = ? WHERE id = ?";
    private static final String UPDATE_SUBJECT         = "UPDATE gnw_professeur_matiere SET date_suppr = now(), fk_utilisateur = ? WHERE fk_professeur = ?";
    private static final String UPDATE_GROUP          = "UPDATE gnw_professeur_groupe SET date_suppr = now(), fk_utilisateur = ? WHERE fk_professeur = ?";
    private static final String DELETE_TEACHER        = "UPDATE gnw_utilisateur SET date_suppr = now(), fk_utilisateur = ? WHERE id = ?";

    /**
     * Returns daoFactory
     * @param daoFactory
     */
    TeacherDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Creates a teacher into database
     * @param teacher
     */
    public void create(Teacher teacher) {
        createTeacher(teacher);
        addSubjects(teacher, true);
        addGroups(teacher, true);
    }

    /**
     * Creates a teacher into database
     * @param teacher
     * @throws DAOException
     */
    private void createTeacher(Teacher teacher) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Administrator     creator           = new Administrator(teacher.getCreator());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_TEACHER, true, teacher.getLastName(), teacher.getFirstName(), teacher.getEmailAddress(), teacher.getPassword(), creator.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                teacher.setId(resultSet.getLong(1));
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
     * Adds subjects to a teacher into database
     * @param teacher
     * @throws DAOException
     */
    private void addSubjects(Teacher teacher, boolean creation) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     administrator     = null;
        Object[]          subjects           = null;

        if (creation) {
            administrator = new Administrator(teacher.getCreator());
        }
        else {
            administrator = new Administrator(teacher.getEditor());
        }

        try {
            connexion = daoFactory.getConnection();
            subjects = teacher.getSubjects().toArray();

            for (Object m : subjects) {
                Subject subject = (Subject) m;
                preparedStatement = initPreparedQuery(connexion, INSERT_SUBJECT, true, teacher.getId(), subject.getId(), administrator.getId());
                preparedStatement.executeUpdate();
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
     * Adds groups to a teacher into database
     * @param teacher
     * @throws DAOException
     */
    private void addGroups(Teacher teacher, boolean creation) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     administrator     = null;
        Object[]          groups            = null;

        if (creation) {
            administrator = new Administrator(teacher.getCreator());
        }
        else {
            administrator = new Administrator(teacher.getEditor());
        }

        try {
            connexion = daoFactory.getConnection();
            groups = teacher.getGroups().toArray();

            for (Object g : groups) {
                Group group = (Group) g;
                preparedStatement = initPreparedQuery(connexion, INSERT_GROUP, true, teacher.getId(), group.getId(), administrator.getId());
                preparedStatement.executeUpdate();
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
     * Searches one or more teachers into database
     * @param teacher
     * @return teachers
     * @throws DAOException
     */
    public Set<Teacher> search(Teacher teacher) throws DAOException {
        Set<Teacher>      teachers          = new TreeSet<Teacher>();
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        String            SQLQuery          = SELECT_ALL;

        try {
            connexion = daoFactory.getConnection();

            if (teacher.getId() != null) {
                SQLQuery += " AND gnw_utilisateur.id = ?";
            }
            else {
                SQLQuery += " AND gnw_utilisateur.id IS NOT ?";
            }

            if (teacher.getLastName() != null) {
                SQLQuery += " AND gnw_utilisateur.nom LIKE ?";
                teacher.setLastName("%" + teacher.getLastName() + "%");
            }
            else {
                SQLQuery += " AND gnw_utilisateur.nom IS NOT ?";
            }

            if (teacher.getFirstName() != null) {
                SQLQuery += " AND gnw_utilisateur.prenom LIKE ?";
                teacher.setFirstName("%" + teacher.getFirstName() + "%");
            }
            else {
                SQLQuery += " AND gnw_utilisateur.prenom IS NOT ?";
            }

            if (teacher.getEmailAddress() != null) {
                SQLQuery += " AND gnw_utilisateur.adresse_mail LIKE ?";
                teacher.setEmailAddress("%" + teacher.getEmailAddress() + "%");
            }
            else {
                SQLQuery += " AND gnw_utilisateur.adresse_mail IS NOT ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, teacher.getId(), teacher.getLastName(), teacher.getFirstName(), teacher.getEmailAddress());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                teacher = mapProfesseur(resultSet);
                teachers.add(teacher);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return teachers;
    }

    /**
     * Edits a teacher into database
     * @param teacher
     * @return teacher
     * @throws DAOException
     */
    public void edit(Teacher teacher) {
        // Edits teacher profile
        editProfile(teacher);

        // Edite teacher password
        if (teacher.getPassword() != null) {
            editPassword(teacher);
        }

        //  Delete teacher subjects and groups
        deleteSubjects(teacher);
        deleteGroups(teacher);

        // Add teacher subject and groups
        if (teacher.getSubjects() != null) {
            addSubjects(teacher, false);
        }

        if (teacher.getGroups() != null) {
            addGroups(teacher, false);
        }
    }

    /**
     * Edits teacher profile into database
     * @param teacher
     * @return teacher
     * @throws DAOException
     */
    private Teacher editProfile(Teacher teacher) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(teacher.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_TEACHER, true, teacher.getLastName(), teacher.getFirstName(), teacher.getEmailAddress(), editor.getId(), teacher.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return teacher;
    }

    /**
     * Edits teacher password into database
     * @param teacher
     * @return teacher
     * @throws DAOException
     */
    private Teacher editPassword(Teacher teacher) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(teacher.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_PASSWORD, true, teacher.getPassword(), editor.getId(), teacher.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return teacher;
    }

    /**
     * Deletes teacher groups into database
     * @param teacher
     * @return teacher
     * @throws DAOException
     */
    private void deleteGroups(Teacher teacher) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(teacher.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_GROUP, true, editor.getId(), teacher.getId());
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
     * Deletes teacher subjects into database
     * @param teacher
     * @return teacher
     * @throws DAOException
     */
    private Teacher deleteSubjects(Teacher teacher) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(teacher.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_SUBJECT, true, editor.getId(), teacher.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return teacher;
    }

    /**
     * Checks teacher login into database
     * @param teacher
     * @return teacher2
     * @throws DAOException
     */
    public Teacher checkLogin(Teacher teacher) throws DAOException {
        Teacher           teacher2          = new Teacher();
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_LOGIN, true, teacher.getEmailAddress(), teacher.getPassword());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                teacher2 = mapProfesseur(resultSet);
                teacher2.setPassword(teacher.getPassword());
            }

            teacher2.setGroups(getGroups(teacher2));
            teacher2.setSubjects(getSubjects(teacher2));
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return teacher2;
    }

    /**
     * Checks the existance of a teacher into database
     * @param teacher
     * @return status
     * @throws DAOException
     */
    public int check(Teacher teacher) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        String            SQLQuery          = SELECT_COUNT_BY_EMAIL;
        int               status            = 0;

        try {
            connexion = daoFactory.getConnection();

            if (teacher.getId() == null) {
                SQLQuery += " AND gnw_utilisateur.id IS NOT ?";
            }
            else {
                SQLQuery += " AND gnw_utilisateur.id != ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, teacher.getEmailAddress(), teacher.getId());
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
     * Returns a teacher into database
     * @param teacher
     * @return teacher
     * @throws DAOException
     */
    public Teacher get(Teacher teacher) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_BY_ID, true, teacher.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                teacher = mapProfesseur(resultSet);
            }

            teacher.setGroups(getGroups(teacher));
            teacher.setSubjects(getSubjects(teacher));
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return teacher;
    }

    /**
     * Returns teachers subjects into database
     * @param teacher
     * @return subjects
     * @throws DAOException
     */
    private Set<Subject> getSubjects(Teacher teacher) throws DAOException {
        Set<Subject>      subjects          = new TreeSet<Subject>();
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_SUBJECTS, true, teacher.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject = mapSubject(resultSet);
                subjects.add(subject);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return subjects;
    }

    /**
     * Returns teacher groups into database
     * @param teacher
     * @return groups
     * @throws DAOException
     */
    private Set<Group> getGroups(Teacher teacher) throws DAOException {
        Set<Group>        groups            = new TreeSet<Group>();
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_GROUPS, true, teacher.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Group group = new Group();
                group = mapGroupe(resultSet);
                groups.add(group);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return groups;
    }

    /**
     * Deletes a teacher into database
     * @param teacher
     * @throws DAOException
     */
    public void delete(Teacher teacher) {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Administrator     editor            = new Administrator(teacher.getEditor());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, DELETE_TEACHER, true, editor.getId(), teacher.getId());
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
     * Maps a Teacher
     * @param resultSet
     * @return teacher
     * @throws SQLException
     */
    private static Teacher mapProfesseur(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();

        teacher.setId(resultSet.getLong("id"));
        teacher.setLastName(resultSet.getString("nom"));
        teacher.setFirstName(resultSet.getString("prenom"));
        teacher.setEmailAddress(resultSet.getString("adresse_mail"));

        return teacher;
    }

    /**
     * Maps a Subject
     * @param resultSet
     * @return subject
     * @throws SQLException
     */
    private static Subject mapSubject(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();

        subject.setId(resultSet.getLong("matiereId"));
        subject.setName(resultSet.getString("matiereNom"));

        return subject;
    }

    /**
     * Maps a Group
     * @param resultSet
     * @return group
     * @throws SQLException
     */
    private static Group mapGroupe(ResultSet resultSet) throws SQLException {
        Group group = new Group();

        group.setId(resultSet.getLong("groupeId"));
        group.setName(resultSet.getString("groupeNom"));

        return group;
    }

}