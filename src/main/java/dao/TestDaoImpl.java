package dao;

import static dao.DAOUtility.silentClosures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.TreeSet;

import beans.*;
import beans.Subject;

public class TestDaoImpl implements TestDao {
    private static final String INSERT_SCORE                  = "INSERT INTO gnw_examen_note(fk_examen, fk_etudiant, note, fk_utilisateur) VALUES (?, ?, ?, ?)";
    private static final String INSERT_TEST                   = "INSERT INTO gnw_examen(fk_format, nom, date, coefficient, fk_professeur, fk_groupe, fk_matiere, fk_utilisateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_TEST                   = "SELECT gnw_examen.id, gnw_examen.nom, gnw_examen.date, gnw_examen.fk_professeur, gnw_examen.fk_format as formatId, gnw_formatexamen.nom as formatNom, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom, gnw_matiere.nom as matiereNom, gnw_examen.coefficient FROM gnw_examen, gnw_matiere, gnw_groupe, gnw_formatexamen WHERE gnw_examen.date_suppr Is NULL AND gnw_examen.fk_groupe = gnw_groupe.id AND gnw_examen.fk_matiere = gnw_matiere.id AND gnw_examen.fk_format = gnw_formatexamen.id AND gnw_examen.fk_professeur = ?";
    private static final String SELECT_TEST_BY_ID             = "SELECT gnw_examen.id, gnw_examen.nom, gnw_examen.date, gnw_examen.fk_professeur, gnw_examen.fk_format as formatId, gnw_formatexamen.nom as formatNom, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom, gnw_matiere.nom as matiereNom, gnw_examen.coefficient FROM gnw_examen, gnw_matiere, gnw_groupe, gnw_formatexamen WHERE gnw_examen.date_suppr IS NULL AND gnw_examen.fk_groupe = gnw_groupe.id AND gnw_examen.fk_format = gnw_formatexamen.id AND gnw_examen.fk_matiere = gnw_matiere.id AND gnw_examen.id = ?";
    private static final String SELECT_SCORE_BY_TEST          = "SELECT id, note FROM gnw_examen_note WHERE fk_examen = ? AND fk_etudiant = ? AND date_suppr IS NULL";
    private static final String SELECT_COUNT_SCORE_BY_TEST    = "SELECT COUNT(id) FROM gnw_examen_note WHERE fk_examen = ? AND fk_etudiant = ? AND date_suppr IS NULL";
    private static final String SELECT_STUDENT_BY_GROUP       = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail FROM gnw_utilisateur, gnw_etudiant_groupe WHERE profil = 0 AND gnw_utilisateur.date_suppr IS NULL AND gnw_utilisateur.id = gnw_etudiant_groupe.fk_etudiant AND gnw_etudiant_groupe.fk_groupe = ?";
    private static final String SELECT_TEST_BY_SUBJECT_GROUP  = "SELECT gnw_examen.id, gnw_examen.nom, gnw_examen.date, gnw_examen.fk_professeur, gnw_examen.fk_format as formatId, gnw_formatexamen.nom as formatNom, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom, gnw_matiere.nom as matiereNom, AVG(gnw_examen_note.note) as average, gnw_examen.coefficient FROM gnw_examen, gnw_matiere, gnw_groupe, gnw_formatexamen, gnw_examen_note WHERE gnw_examen.date_suppr IS NULL AND gnw_examen.fk_groupe = gnw_groupe.id AND gnw_examen.fk_format = gnw_formatexamen.id AND gnw_examen.fk_matiere = gnw_matiere.id AND gnw_examen.fk_matiere = ? AND gnw_examen.fk_groupe = ? GROUP BY gnw_examen.id";
    private static final String SELECT_SUBJECT_BY_STUDENT     = "SELECT DISTINCT gnw_examen.fk_matiere FROM gnw_examen, gnw_examen_note WHERE gnw_examen.date_suppr IS NULL AND gnw_examen_note.date_suppr IS NULL AND gnw_examen.id = gnw_examen_note.fk_examen AND gnw_examen_note.fk_etudiant = ?";
    private static final String SELECT_TEST_AVERAGE_BY_SUBJECT = "SELECT SUM(gnw_examen_note.note * gnw_examen.coefficient) / SUM(gnw_examen.coefficient) as moyenne FROM gnw_examen, gnw_examen_note WHERE gnw_examen_note.fk_etudiant = ? AND gnw_examen.fk_matiere = ? AND gnw_examen_note.fk_examen = gnw_examen.id";
    private static final String SELECT_TEST_AVERAGE           = "SELECT AVG(gnw_examen_note.note) as moyenne FROM gnw_examen_note WHERE fk_examen = ?";
    private static final String UPDATE_SCORE                  = "UPDATE gnw_examen_note SET note = ?, fk_utilisateur = ? WHERE fk_examen = ? AND fk_etudiant = ?";
    private static final String UPDATE_TEST                   = "UPDATE gnw_examen SET fk_professeur = ?, fk_format = ?, nom = ?, date = ?, coefficient = ?, fk_matiere = ?, fk_utilisateur = ? WHERE id = ?";
    private static final String DELETE_TEST                   = "UPDATE gnw_examen SET date_suppr = now(), fk_utilisateur = ? WHERE id = ? AND fk_professeur = ?";
    private static final String DELETE_SCORES_BY_TEST         = "UPDATE gnw_examen_note SET date_suppr = now(), fk_utilisateur = ? WHERE fk_examen = ?;";
    private DAOFactory daoFactory;

    /**
     * Returns daoFactory
     * @param daoFactory
     */
    TestDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Creates a test into database
     * @param test
     * @throws DAOException
     */
    public void create(Test test) {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Group             group             = new Group(test.getGroup());
        Subject           subject           = new Subject(test.getSubject());
        Teacher           teacher           = new Teacher(test.getTeacher());
        TestFormat        format            = new TestFormat(test.getFormat());
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();

            preparedStatement = initPreparedQuery(connexion, INSERT_TEST, true, format.getId(), test.getTitle(), test.getDate(), test.getCoefficient(), teacher.getId(), group.getId(), subject.getId(), teacher.getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                test.setId(resultSet.getLong(1));
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
     * Add a score to a student into database
     * @param test
     * @param score
     * @throws DAOException
     */
    private void addScore(Test test, Score score) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Student           student           = new Student(score.getStudent());
        Teacher           teacher           = new Teacher(test.getTeacher());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, INSERT_SCORE, true, test.getId(), student.getId(), score.getScore(), teacher.getId());
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
     * Returns test informations and scores into database
     * @param test
     * @throws DAOException
     */
    public Test get(Test test) {
        test = getTest(test);
        getStudents(test);
        checkScores(test);
        getScores(test);
        getAverage(test);

        return test;
    }

    /**
     * Returns test student into database
     * @param test
     * @throws DAOException
     */
    private void getStudents(Test test) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Student           student           = new Student();
        Group             group             = new Group(test.getGroup());
        Set<Score>        scores            = new TreeSet<Score>();

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_STUDENT_BY_GROUP, true, group.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Score score = new Score();
                student = mapEtudiant(resultSet);
                score.setStudent(student);
                scores.add(score);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        test.setScores(scores);
    }

    /**
     * Returns test scores into database
     * @param test
     * @throws DAOException
     */
    private void getScores(Test test) throws DAOException {
        Set<Score> scores = new TreeSet<Score>(test.getScores());
        Object[]   ss     = scores.toArray();

        for (Object s : ss) {
            Score score = (Score) s;
            getScore(test, score);
            scores.add(score);
        }

        test.setScores(scores);
    }

    /**
     * Returns a test average into database
     * @param test
     * @throws DAOException
     */
    private void getAverage(Test test) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_TEST_AVERAGE, true, test.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                test.setAverage(resultSet.getFloat("moyenne"));
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
     * Returns a test student score into database
     * @param test
     * @param score
     * @throws DAOException
     */
    private void getScore(Test test, Score score) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Student           student           = new Student(score.getStudent());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_SCORE_BY_TEST, true, test.getId(), student.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                score.setId(resultSet.getLong("id"));
                score.setScore(resultSet.getFloat("note"));
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
     * Returns a test student score into database
     * @param test
     * @param student
     * @throws DAOException
     */
    private Test getScore(Test test, Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Set<Score>        scores            = new TreeSet<Score>();
        Score             score             = new Score(student);

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_SCORE_BY_TEST, true, test.getId(), student.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                score.setId(resultSet.getLong("id"));
                score.setScore(resultSet.getFloat("note"));
                scores.add(score);
                test.setScores(scores);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return test;
    }

    /**
     * Returns a test into database
     * @param test
     * @return test
     * @throws DAOException
     */
    private Test getTest(Test test) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_TEST_BY_ID, true, test.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                test = mapExamen(resultSet);
            }

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return test;
    }

    /**
     * Returns student scores in a subject into database
     * @param subjectScore
     * @param student
     * @return subjectScore
     * @throws DAOException
     */
    private void getTests(SubjectScore subjectScore, Student student) {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Subject           subject           = new Subject(subjectScore.getSubject());
        Group             group             = new Group(student.getGroup());
        Set<Test>         tests             = new TreeSet<Test>();

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_TEST_BY_SUBJECT_GROUP, true, subject.getId(), group.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Test test = new Test();
                test = mapExamen(resultSet);
                test = getScore(test, student);
                tests.add(test);
            }

            subjectScore.setTests(tests);

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }
    }

    /**
     * Returns student subject informations into database
     * @param student
     * @return subjectScores
     * @throws DAOException
     */
    public Set<SubjectScore> getSubjectScores(Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Set<SubjectScore> subjectScores     = new TreeSet<SubjectScore>();
        SubjectDaoImpl    subjectDao        = new SubjectDaoImpl(daoFactory);

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_SUBJECT_BY_STUDENT, true, student.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SubjectScore subjectScore = new SubjectScore();
                subjectScore.setId(resultSet.getLong("fk_matiere"));
                Subject subject = new Subject(subjectScore.getId());
                subject = subjectDao.get(subject);
                subjectScore.setSubject(subject);
                getSubjectAverage(subjectScore, student);
                getTests(subjectScore, student);
                subjectScores.add(subjectScore);
            }

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return subjectScores;
    }

    /**
     * Returns a student subject average into database
     * @param subjectScore
     * @param student
     * @return subjectScore
     * @throws DAOException
     */
    public void getSubjectAverage(SubjectScore subjectScore, Student student) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_TEST_AVERAGE_BY_SUBJECT, true, student.getId(), subjectScore.getId());
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            subjectScore.setAverage(resultSet.getFloat("moyenne"));

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

    }

    /**
     * Calculates gradebook average
     * @param subjectScores
     * @return average
     */
    public float calcGradebookAverage(Set<SubjectScore> subjectScores) {
        float    average = 0;
        Object[] mss     = subjectScores.toArray();
        int      i       = 0;

        for (Object ms : mss) {
            SubjectScore subjectScore = (SubjectScore) ms;
            average += subjectScore.getAverage();
            i++;
        }

        return (average / i);
    }

    /**
     * Returns student gradebook into database
     * @param student
     * @return gradebook
     */
    public Gradebook getGradebook(Student student) {
        Gradebook gradebook = new Gradebook();

        gradebook.setSubjectScores(getSubjectScores(student));
        gradebook.setAverage(calcGradebookAverage(gradebook.getSubjectScores()));

        return gradebook;
    }

    /**
     * Searchs one or more tests into database
     * @param test
     * @throws DAOException
     */
    public Set<Test> search(Test test) throws DAOException {
        Set<Test>         tests             = new TreeSet<Test>();
        Group             group             = new Group(test.getGroup());
        Subject           subject           = new Subject(test.getSubject());
        Teacher           teacher           = new Teacher(test.getTeacher());
        TestFormat        format            = new TestFormat(test.getFormat());
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        String            SQLQuery          = SELECT_TEST;

        try {
            connexion = daoFactory.getConnection();

            if (test.getId() != null) {
                SQLQuery += " AND gnw_examen.id = ?";
            }
            else {
                SQLQuery += " AND gnw_examen.id IS NOT ?";
            }

            if (test.getTitle() != null) {
                SQLQuery += " AND gnw_examen.nom LIKE ?";
                test.setTitle("%" + test.getTitle() + "%");
            }
            else {
                SQLQuery += " AND gnw_examen.nom IS NOT ?";
            }

            if (test.getDate() != null) {
                SQLQuery += " AND gnw_examen.date = ?";
            }
            else {
                SQLQuery += " AND gnw_examen.date IS NOT ?";
            }

            if (format.getId() != null) {
                SQLQuery += " AND gnw_examen.fk_format = ?";
            }
            else {
                SQLQuery += " AND gnw_examen.fk_format IS NOT ?";
            }

            if (group.getId() != null) {
                SQLQuery += " AND gnw_examen.fk_groupe = ?";
            }
            else {
                SQLQuery += " AND gnw_examen.fk_groupe IS NOT ?";
            }

            if (subject.getId() != null) {
                SQLQuery += " AND gnw_examen.fk_matiere = ?";
            }
            else {
                SQLQuery += " AND gnw_examen.fk_matiere IS NOT ?";
            }

            preparedStatement = initPreparedQuery(connexion, SQLQuery, true, teacher.getId(), test.getId(), test.getTitle(), test.getDate(), format.getId(), group.getId(), subject.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                test = mapExamen(resultSet);
                getAverage(test);
                tests.add(test);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return tests;
    }

    /**
     * Edits a test
     * @param test
     */
    public void edit(Test test) {
        editTest(test);
        editScores(test);
    }

    /**
     * Edits test informations
     * @param test
     * @throws DAOException
     */
    private void editTest(Test test) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Teacher           teacher           = new Teacher(test.getTeacher());
        TestFormat        format            = new TestFormat(test.getFormat());
        Subject           subject           = new Subject(test.getSubject());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_TEST, true, teacher.getId(), format.getId(), test.getTitle(), test.getDate(), test.getCoefficient(), subject.getId(), teacher.getId(), test.getId());
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
     * Edits test scores
     * @param test
     * @throws DAOException
     */
    private void editScores(Test test) throws DAOException {
        Object[] scores = test.getScores().toArray();

        for (Object s : scores) {
            Score score = (Score) s;
            editScore(test, score);
        }
    }

    /**
     * Updates test scores
     * @param test
     */
    private void checkScores(Test test) {
        Object[] scores = test.getScores().toArray();

        for (Object s : scores) {
            Score score = (Score) s;

            if (checkScore(test, score)) {
                addScore(test, score);
            }
        }
    }

    /**
     * Check the existence of a score into database
     * @param test
     * @param score
     * @return boolean
     * @throws DAOException
     */
    private boolean checkScore(Test test, Score score) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        Student           student           = new Student(score.getStudent());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, SELECT_COUNT_SCORE_BY_TEST, true, test.getId(), student.getId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if (resultSet.getInt(1) == 1) {
                return false;
            }

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            silentClosures(preparedStatement, connexion);
        }

        return true;
    }

    /**
     * Edit a student score into database
     * @param test
     * @param score
     * @throws DAOException
     */
    private void editScore(Test test, Score score) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Student           student           = new Student(score.getStudent());
        Teacher           teacher           = new Teacher(test.getTeacher());

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedQuery(connexion, UPDATE_SCORE, true, score.getScore(), teacher.getId(), test.getId(), student.getId());
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
     * Deletes a test into database
     * @param test
     * @throws DAOException
     */
    public void delete(Test test) throws DAOException {
        Connection        connexion         = null;
        PreparedStatement preparedStatement = null;
        Teacher           teacher           = new Teacher(test.getTeacher());

        try {
            connexion = daoFactory.getConnection();

            // Deletes the test
            preparedStatement = initPreparedQuery(connexion, DELETE_TEST, true, teacher.getId(), test.getId(), teacher.getId());
            preparedStatement.executeUpdate();

            // Deletes test scores
            preparedStatement = initPreparedQuery(connexion, DELETE_SCORES_BY_TEST, true, teacher.getId(), test.getId());
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
     * Maps a test
     * @param resultSet
     * @return test
     * @throws SQLException
     */
    private static Test mapExamen(ResultSet resultSet) throws SQLException {
        Test       test    = new Test();
        Subject    subject = new Subject();
        Group      group   = new Group();
        TestFormat format  = new TestFormat();
        Teacher    teacher = new Teacher();

        test.setId(resultSet.getLong("id"));
        test.setTitle(resultSet.getString("nom"));
        test.setCoefficient(resultSet.getFloat("coefficient"));
        test.setDate(convertDateToString(resultSet.getDate("date")));
        teacher.setId(resultSet.getLong("fk_professeur"));
        test.setTeacher(teacher);
        format.setId(resultSet.getLong("formatId"));
        format.setName(resultSet.getString("formatNom"));
        test.setFormat(format);
        group.setId(resultSet.getLong("groupeId"));
        group.setName(resultSet.getString("groupeNom"));
        subject.setName(resultSet.getString("matiereNom"));
        test.setGroup(group);
        test.setSubject(subject);

        return test;
    }

    /**
     * Maps a student
     * @param resultSet
     * @return student
     * @throws SQLException
     */
    private static Student mapEtudiant(ResultSet resultSet) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getLong("id"));
        student.setLastName(resultSet.getString("nom"));
        student.setFirstName(resultSet.getString("prenom"));
        student.setEmailAddress(resultSet.getString("adresse_mail"));

        return student;
    }

    /**
     * Converts a SQL Date in a String
     * @param date
     * @return
     */
    private static String convertDateToString(java.sql.Date date) {
        DateFormat APP_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

        return APP_FORMAT.format(date);
    }
}
