package forms;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Administrator;
import beans.Subject;
import beans.Teacher;
import beans.Group;
import dao.TeacherDao;

public class TeacherForm {
    private static final String              ADMINISTRATOR_SESSION = "administratorSession";
    private static final String              ID_FIELD              = "id";
    private static final String              LAST_NAME_FIELD       = "lastName";
    private static final String              FIRST_NAME_FIELD      = "firstName";
    private static final String              EMAIL_ADDRESS_FIELD   = "emailAddress";
    private static final String              PASSWORD_FIELD        = "password";
    private static final String              CONFIRMATION_FIELD    = "confirmation";
    private static final String              GROUPS_FIELD          = "groups[]";
    private static final String              SUBJECTS_FIELD        = "subjects[]";
    private              Map<String, String> errors                = new HashMap<String, String>();
    private TeacherDao teacherDao;

    /**
     * Constructor
     * @param teacherDao
     */
    public TeacherForm(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    /**
     * Creates a teacher into database
     * @param request
     * @return teacher
     */
    public Teacher create(HttpServletRequest request) {
        String        lastName     = getFieldVar(request, LAST_NAME_FIELD);
        String        firstName    = getFieldVar(request, FIRST_NAME_FIELD);
        String        emailAddress = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String        password     = getFieldVar(request, PASSWORD_FIELD);
        String        confirmation = getFieldVar(request, CONFIRMATION_FIELD);
        String[]      groups       = getFieldVars(request, GROUPS_FIELD);
        String[]      subjects     = getFieldVars(request, SUBJECTS_FIELD);
        Administrator creator      = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Teacher       teacher      = new Teacher();

        treatLastName(lastName, teacher);
        treatFirstName(firstName, teacher);
        treatEmailAddress(emailAddress, teacher);
        treatPassword(password, confirmation, teacher);
        treatTeacher(teacher);
        treatSubjects(subjects, teacher);
        treatGroups(groups, teacher);
        treatCreator(creator, teacher);

        if (errors.isEmpty()) {
            teacherDao.create(teacher);
        }

        return teacher;
    }

    /**
     * Searches one or more teachers into database
     * @param request
     * @return teachers
     */
    public Set<Teacher> search(HttpServletRequest request) {
        String       id           = getFieldVar(request, ID_FIELD);
        String       lastName     = getFieldVar(request, LAST_NAME_FIELD);
        String       firstName    = getFieldVar(request, FIRST_NAME_FIELD);
        String       emailAddress = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        Set<Teacher> teachers     = new HashSet<Teacher>();
        Teacher      teacher      = new Teacher();

        if (id != null) {
            teacher.setId(Long.parseLong(id));
        }

        teacher.setLastName(lastName);
        teacher.setFirstName(firstName);
        teacher.setEmailAddress(emailAddress);
        teachers = teacherDao.search(teacher);

        return teachers;
    }

    /**
     * Returns a teacher into database
     * @param request
     * @return teacher
     */
    public Teacher get(HttpServletRequest request) {
        String  id      = getFieldVar(request, ID_FIELD);
        Teacher teacher = new Teacher();

        treatId(id, teacher);
        teacher = teacherDao.get(teacher);

        return teacher;
    }

    /**
     * Edits a teacher into database
     * @param request
     * @return teacher
     */
    public Teacher edit(HttpServletRequest request) {
        String        id           = getFieldVar(request, ID_FIELD);
        String        lastName     = getFieldVar(request, LAST_NAME_FIELD);
        String        firstName    = getFieldVar(request, FIRST_NAME_FIELD);
        String        emailAddress = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String        password     = getFieldVar(request, PASSWORD_FIELD);
        String        confirmation = getFieldVar(request, CONFIRMATION_FIELD);
        String[]      groups       = getFieldVars(request, GROUPS_FIELD);
        String[]      subjects     = getFieldVars(request, SUBJECTS_FIELD);
        Administrator editor       = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Teacher       teacher      = new Teacher();

        treatId(id, teacher);
        treatLastName(lastName, teacher);
        treatFirstName(firstName, teacher);
        treatEmailAddress(emailAddress, teacher);
        treatTeacher(teacher);
        treatSubjects(subjects, teacher);
        treatGroups(groups, teacher);
        treatEditor(editor, teacher);

        if ((password != null) || (confirmation != null)) {
            treatPassword(password, confirmation, teacher);
        }

        if (errors.isEmpty()) {
            teacherDao.edit(teacher);
        }

        return teacher;
    }

    /**
     * Deletes a teacher into database
     * @param request
     */
    public void delete(HttpServletRequest request) {
        String        id      = getFieldVar(request, ID_FIELD);
        Administrator editor  = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Teacher       teacher = new Teacher();

        treatId(id, teacher);
        treatEditor(editor, teacher);
        teacherDao.delete(teacher);

    }

    /**
     * Checks teacher's login into database
     * @param request
     * @return teacher
     */
    public Teacher checkLogin(HttpServletRequest request) {
        String  emailAddress = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String  password     = getFieldVar(request, PASSWORD_FIELD);
        Teacher teacher      = new Teacher();

        treatEmailAddress(emailAddress, teacher);
        teacher.setPassword(cryptPassword(password));
        teacher = teacherDao.checkLogin(teacher);
        treatLogin(teacher);
        teacher.setPassword(cryptPassword(password));
        teacher.setEmailAddress(emailAddress);

        return teacher;
    }

    /**
     * Treats teacher's id
     * @param id
     * @param teacher
     */
    private void treatId(String id, Teacher teacher) {
        try {
            validateId(id);
            teacher.setId(Long.parseLong(id));
        }
        catch (Exception e) {
            setError(ID_FIELD, e.getMessage());
        }
    }

    /**
     * Treats teacher's last name
     * @param lastName
     * @param teacher
     */
    private void treatLastName(String lastName, Teacher teacher) {
        try {
            validateLastName(lastName);
            teacher.setLastName(lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase());
        }
        catch (Exception e) {
            setError(LAST_NAME_FIELD, e.getMessage());
        }
    }

    /**
     * Treat teacher's first name
     * @param firstName
     * @param teacher
     */
    private void treatFirstName(String firstName, Teacher teacher) {
        try {
            validateFirstName(firstName);
            teacher.setFirstName(firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase());
        }
        catch (Exception e) {
            setError(FIRST_NAME_FIELD, e.getMessage());
        }
    }

    /**
     * Treats teacher's login
     * @param teacher
     */
    private void treatLogin(Teacher teacher) {
        try {
            validateLogin(teacher);
        }
        catch (Exception e) {
            setError("connexion", e.getMessage());
        }
    }

    /**
     * Treats teacher's email address
     * @param emailAddress
     * @param teacher
     */
    private void treatEmailAddress(String emailAddress, Teacher teacher) {
        try {
            validateEmailAddress(emailAddress);
            teacher.setEmailAddress(emailAddress.trim().toLowerCase());
        }
        catch (Exception e) {
            setError(EMAIL_ADDRESS_FIELD, e.getMessage());
        }
    }

    /**
     * Treats teacher's password & confirmation
     * @param password
     * @param confirmation
     * @param teacher
     */
    private void treatPassword(String password, String confirmation, Teacher teacher) {
        try {
            validatePassword(password, confirmation);
            password = cryptPassword(password);
            teacher.setPassword(password);
        }
        catch (Exception e) {
            setError(PASSWORD_FIELD, e.getMessage());
            setError(CONFIRMATION_FIELD, null);
        }
    }

    /**
     * Treats teacher's groups
     * @param groups
     * @param teacher
     */
    private void treatGroups(String[] groups, Teacher teacher) {
        Set<Group> groupList = new HashSet<Group>();

        if (validatesGroups(groups)) {
            for (int i = 0; i < groups.length; i++) {
                Group group = new Group();
                group.setId(Long.parseLong(groups[i]));
                groupList.add(group);
            }

            teacher.setGroups(groupList);
        }
    }

    /**
     * Treats teacher's subjects
     * @param subjects
     * @param teacher
     */
    private void treatSubjects(String[] subjects, Teacher teacher) {
        Set<Subject> subjectList = new HashSet<Subject>();

        if (validateSubjects(subjects)) {
            for (int i = 0; i < subjects.length; i++) {
                Subject matiere = new Subject();
                matiere.setId(Long.parseLong(subjects[i]));
                subjectList.add(matiere);
            }

            teacher.setSubjects(subjectList);
        }
    }

    /**
     * Treats a teacher
     * @param teacher
     */
    private void treatTeacher(Teacher teacher) {
        try {
            validateTeacher(teacher);
        }
        catch (Exception e) {
            setError("teacher", e.getMessage());
        }
    }

    /**
     * Treats teacher's creator
     * @param creator
     */
    private void treatCreator(Administrator creator, Teacher teacher) {
        try {
            validateCreator(creator);
            teacher.setCreator(creator);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Treats teacher's editor
     * @param editor
     */
    private void treatEditor(Administrator editor, Teacher teacher) {
        try {
            validateCreator(editor);
            teacher.setEditor(editor);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Validates teacher's id
     * @param id
     * @throws Exception
     */
    private void validateId(String id) throws Exception {
        if ((id == null)) {
            throw new Exception("The identification number is null");
        }
    }

    /**
     * Validates teacher's last name
     * @param lastName
     * @throws Exception
     */
    private void validateLastName(String lastName) throws Exception {
        if ((lastName == null) || (lastName.length() < 2) || (lastName.length() > 50)) {
            throw new Exception("Please enter a name of 2 to 50 characters");
        }
    }

    /**
     * Validates teacher's first name
     * @param firstName
     * @throws Exception
     */
    private void validateFirstName(String firstName) throws Exception {
        if ((firstName == null) || (firstName.length() < 2) || (firstName.length() > 50)) {
            throw new Exception("Please enter a first name of 2-50 characters");
        }
    }

    /**
     * Validates teacher's email address
     * @param emailAddress
     * @throws Exception
     */
    private void validateEmailAddress(String emailAddress) throws Exception {
        if ((emailAddress == null) || (emailAddress.length() < 8) || (emailAddress.length() > 100) || (!emailAddress.matches("[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}"))) {
            throw new Exception("Please enter a valid email address");
        }
    }

    /**
     * Validates teacher's group
     * @param groups
     */
    private boolean validatesGroups(String[] groups) {
        return (groups != null);
    }

    /**
     * Validates teacher's subjects
     * @param subjects
     */
    private boolean validateSubjects(String[] subjects) {
        return (subjects != null);

    }

    /**
     * Validates teacher's password
     * @param password
     * @param confirmation
     * @throws Exception
     */
    private void validatePassword(String password, String confirmation) throws Exception {
        if ((password == null) || (password.length() < 4) || (confirmation == null) || (confirmation.length() < 4)) {
            throw new Exception("Please enter a stronger password");
        }
        else if (!password.equals(confirmation)) {
            throw new Exception("Passwords are different");
        }
    }

    /**
     * Validates a teacher
     * @param teacher
     * @throws Exception
     */
    private void validateTeacher(Teacher teacher) throws Exception {
        if (teacherDao.check(teacher) != 0) {
            throw new Exception("This teacher already exists");
        }
    }

    /**
     * Validates teacher's login
     * @param teacher
     * @throws Exception
     */
    private void validateLogin(Teacher teacher) throws Exception {
        if (teacher.getId() == null) {
            throw new Exception("Your email address or password is incorrect");
        }
    }

    /**
     * Validates teacher's creator
     * @param creator
     * @throws Exception
     */
    private void validateCreator(Administrator creator) throws Exception {
        if (creator.getId() == null) {
            throw new Exception("Unknown administrator");
        }
    }

    /**
     * Cryptes a password
     * @param password
     * @return cryptedPassword
     */
    private String cryptPassword(String password) {
        StringBuffer cryptedPassword = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();

            for (int i = 0; i < byteData.length; i++) {
                cryptedPassword.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        }
        catch (Exception e) {

        }

        return cryptedPassword.toString();
    }

    /**
     * Returns errors
     * @return errors
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Set an error
     * @param field
     * @param message
     */
    private void setError(String field, String message) {
        errors.put(field, message);
    }

    /**
     * Returns a field variable
     * @param request
     * @param fieldVar
     * @return var
     */
    private static String getFieldVar(HttpServletRequest request, String fieldVar) {
        String var = request.getParameter(fieldVar);

        return (((var == null) || (var.trim().length() == 0)) ? null : var.trim());
    }

    /**
     * Returns a session variable
     * @param request
     * @param sessionVar
     * @return object
     */
    private static Object getSessionVar(HttpServletRequest request, String sessionVar) {
        HttpSession session = request.getSession();
        Object      object  = session.getAttribute(sessionVar);

        return ((object == null) ? null : object);
    }

    /**
     * Returns field variables
     * @param request
     * @param fieldVar
     * @return var
     */
    private static String[] getFieldVars(HttpServletRequest request, String fieldVar) {
        return request.getParameterValues(fieldVar);
    }
}