package forms;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dao.StudentDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import utilities.Mail;
import beans.Administrator;
import beans.Student;
import beans.Group;

public class StudentForm {
    private static final String              STUDENT_SESSION       = "studentSession";
    private static final String              ADMINISTRATOR_SESSION = "administratorSession";
    private static final String              ID_FIELD              = "id";
    private static final String              LAST_NAME_FIELD       = "lastName";
    private static final String              FIRST_NAME_FIELD      = "firstName";
    private static final String              EMAIL_ADDRESS_FIELD   = "emailAddress";
    private static final String              GROUP_FIELD           = "group";
    private static final String              PASSWORD_FIELD        = "password";
    private              Map<String, String> errors                = new HashMap<String, String>();
    private StudentDao studentDao;

    /**
     * Constructor
     * @param studentDao
     */
    public StudentForm(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    /**
     * Creates a student into database
     * @param request
     * @return student
     */
    public Student create(HttpServletRequest request) {
        String        lastName     = getFieldVar(request, LAST_NAME_FIELD);
        String        firstName    = getFieldVar(request, FIRST_NAME_FIELD);
        String        emailAddress = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String        groupId      = getFieldVar(request, GROUP_FIELD);
        Administrator creator      = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Student       student      = new Student();

        treatLastName(lastName, student);
        treatFirstName(firstName, student);
        treatEmailAddress(emailAddress, student);
        treatPassword(student, false);
        treatGroup(groupId, student);
        treatStudent(student);
        treatCreator(creator, student);

        if (errors.isEmpty()) {
            studentDao.create(student);
        }

        return student;
    }

    /**
     * Searches one or more students into database
     * @param request
     * @return students
     */
    public Set<Student> search(HttpServletRequest request) {
        String       id           = getFieldVar(request, ID_FIELD);
        String       lastName     = getFieldVar(request, LAST_NAME_FIELD);
        String       firstName    = getFieldVar(request, FIRST_NAME_FIELD);
        String       emailAddress = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String       groupId      = getFieldVar(request, GROUP_FIELD);
        Set<Student> students     = new HashSet<Student>();
        Student      student      = new Student();
        Group        group        = new Group();

        if (id != null) {
            student.setId(Long.parseLong(id));
        }

        if (groupId != null) {
            group.setId(Long.parseLong(groupId));
        }

        student.setLastName(lastName);
        student.setFirstName(firstName);
        student.setEmailAddress(emailAddress);
        student.setGroup(group);
        students = studentDao.search(student);

        return students;
    }

    /**
     * Edits a student into database
     * @param request
     * @return student
     */
    public Student edit(HttpServletRequest request) {
        String        id           = getFieldVar(request, ID_FIELD);
        String        lastName     = getFieldVar(request, LAST_NAME_FIELD);
        String        firstName    = getFieldVar(request, FIRST_NAME_FIELD);
        String        emailAddress = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String        groupId      = getFieldVar(request, GROUP_FIELD);
        Administrator editor       = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Student       student      = new Student();

        treatId(id, student);
        treatLastName(lastName, student);
        treatFirstName(firstName, student);
        treatEmailAddress(emailAddress, student);
        treatGroup(groupId, student);
        treatStudent(student);
        treatEditor(editor, student);

        if (errors.isEmpty()) {
            studentDao.edit(student);
        }

        return student;
    }

    /**
     * Checks student's login into database
     * @param request
     * @return student
     */
    public Student checkLogin(HttpServletRequest request) {
        String  emailAddress = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String  password     = getFieldVar(request, PASSWORD_FIELD);
        Student student      = new Student();

        treatEmailAddress(emailAddress, student);
        student.setPassword(cryptPassword(password));
        student = studentDao.checkLogin(student);
        treatLogin(student);
        student.setPassword(cryptPassword(password));
        student.setEmailAddress(emailAddress);

        return student;
    }

    /**
     * Returns a student into database
     * @param request
     * @return student
     */
    public Student get(HttpServletRequest request) {
        String  id      = getFieldVar(request, ID_FIELD);
        Student student = new Student();

        student.setId(Long.parseLong(id));
        student = studentDao.getProfile(student);

        return student;
    }

    /**
     * Deletes a student into database
     * @param request
     * @return status
     */
    public void delete(HttpServletRequest request) {
        String        id      = getFieldVar(request, ID_FIELD);
        Administrator editor  = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Student       student = new Student();

        treatId(id, student);
        treatEditor(editor, student);
        studentDao.delete(student);
    }

    /**
     * Returns all student's informations
     * @param request
     * @return student
     */
    public Student getAllInfos(HttpServletRequest request) {
        Student student = (Student) getSessionVar(request, STUDENT_SESSION);

        return studentDao.getAll(student);
    }

    /**
     * Resets student's password and sends it by mail
     * @param request
     */
    public void resetPassword(HttpServletRequest request) {
        String        id      = getFieldVar(request, ID_FIELD);
        Administrator editor  = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Student       student = new Student();

        treatId(id, student);
        student = studentDao.getProfile(student);
        treatPassword(student, true);
        treatEditor(editor, student);
        studentDao.editPassword(student);
    }

    /**
     * Treats student's id
     * @param id
     * @param student
     */
    private void treatId(String id, Student student) {
        try {
            validateId(id);
            student.setId(Long.parseLong(id));
        }
        catch (Exception e) {
            setError(ID_FIELD, e.getMessage());
        }
    }

    /**
     * Treats student's last name
     * @param lastName
     * @param student
     */
    private void treatLastName(String lastName, Student student) {
        try {
            validateLastName(lastName);
            student.setLastName(lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase());
        }
        catch (Exception e) {
            setError(LAST_NAME_FIELD, e.getMessage());
        }
    }

    /**
     * Treats student's first name
     * @param firstName
     * @param student
     */
    private void treatFirstName(String firstName, Student student) {
        try {
            validateFirstName(firstName);
            student.setFirstName(firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase());
        }
        catch (Exception e) {
            setError(FIRST_NAME_FIELD, e.getMessage());
        }
    }

    /**
     * Treats student's group
     * @param groupId
     * @param student
     */
    private void treatGroup(String groupId, Student student) {
        Group group = new Group();

        try {
            validateGroup(groupId);
            group.setId(Long.parseLong(groupId));
            student.setGroup(group);
        }
        catch (Exception e) {
            setError(GROUP_FIELD, e.getMessage());
        }
    }

    /**
     * Treats student's email address
     * @param emailAddress
     * @param student
     */
    private void treatEmailAddress(String emailAddress, Student student) {
        try {
            validateEmailAddress(emailAddress);
            student.setEmailAddress(emailAddress.trim().toLowerCase());
        }
        catch (Exception e) {
            setError(EMAIL_ADDRESS_FIELD, e.getMessage());
        }
    }

    /**
     * Treats student's password
     * @param student
     */
    private void treatPassword(Student student, boolean reset) {
        String password = generatePassword(8);

        try {
            validatePassword(password);
            sendPassword(student, password, reset);
            password = cryptPassword(password);
            student.setPassword(password);
        }
        catch (Exception e) {

        }
    }

    /**
     * Treats a student
     * @param student
     */
    private void treatStudent(Student student) {
        try {
            validateStudent(student);
        }
        catch (Exception e) {
            setError("student", e.getMessage());
        }
    }

    /**
     * Treats student's login
     * @param student
     */
    private void treatLogin(Student student) {
        try {
            validateLogin(student);
        }
        catch (Exception e) {
            setError("login", e.getMessage());
        }
    }

    /**
     * Treats student's creator
     * @param creator
     * @param student
     */
    private void treatCreator(Administrator creator, Student student) {
        try {
            validateCreator(creator);
            student.setCreator(creator);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Treats student's editor
     * @param editor
     * @param student
     */
    private void treatEditor(Administrator editor, Student student) {
        try {
            validateCreator(editor);
            student.setEditor(editor);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Validates student's id
     * @param id
     * @throws Exception
     */
    private void validateId(String id) throws Exception {
        if ((id == null)) {
            throw new Exception("The identification number is zero");
        }
    }

    /**
     * Validates student's last name
     * @param lastName
     * @throws Exception
     */
    private void validateLastName(String lastName) throws Exception {
        if ((lastName == null) || (lastName.length() < 2) || (lastName.length() > 50)) {
            throw new Exception("Please enter a name of 2 to 50 characters");
        }
    }

    /**
     * Validates student's first name
     * @param firstName
     * @throws Exception
     */
    private void validateFirstName(String firstName) throws Exception {
        if ((firstName == null) || (firstName.length() < 2) || (firstName.length() > 50)) {
            throw new Exception("Veuillez entrer un firstName de 2 à 50 caractères");
        }
    }

    /**
     * Validates student's group
     * @param group
     * @throws Exception
     */
    private void validateGroup(String group) throws Exception {
        if (group == null) {
            throw new Exception("Please select a group");
        }
    }

    /**
     * Validates student's email address
     * @param emailAddress
     * @throws Exception
     */
    private void validateEmailAddress(String emailAddress) throws Exception {
        if ((emailAddress == null) || (emailAddress.length() < 8) || (emailAddress.length() > 100) || (!emailAddress.matches("[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}"))) {
            throw new Exception("Please enter a valid email address");
        }
    }

    /**
     * Validates student's password
     * @param password
     * @throws Exception
     */
    private void validatePassword(String password) throws Exception {
        if ((password == null) || (password.length() != 8)) {
            throw new Exception("Please enter a stronger password");
        }
    }

    /**
     * Validates a student
     * @param student
     * @throws Exception
     */
    private void validateStudent(Student student) throws Exception {
        if (studentDao.check(student) != 0) {
            throw new Exception("This student already exists");
        }
    }

    /**
     * Validates student's login
     * @param student
     * @throws Exception
     */
    private void validateLogin(Student student) throws Exception {
        if (student.getId() == null) {
            throw new Exception("Your email address or password is incorrect");
        }
    }

    /**
     * Validates student's creator
     * @param creator
     * @throws Exception
     */
    private void validateCreator(Administrator creator) throws Exception {
        if (creator.getId() == null) {
            throw new Exception("Unknown administrator");
        }
    }

    /**
     * Generates a random password
     * @param length
     * @return password
     */
    private String generatePassword(int length) {
        String        alphabet       = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int           alphabetLength = alphabet.length();
        StringBuilder password       = new StringBuilder(alphabetLength);

        for (int x = 0; x < length; x++) {
            int y = (int) (Math.random() * alphabetLength);
            password.append(alphabet.charAt(y));
        }

        System.out.println(password.toString());

        return password.toString();
    }

    /**
     * Cryptes student's password
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
     * Sends student's login by mail
     * @param student
     * @param password
     */
    private static void sendPassword(Student student, String password, boolean reset) {
        if (reset) {
            Mail.sendBackStudentPassword(student, password);
        }
        else {
            Mail.sendStudentPassword(student, password);
        }
    }

    /**
     * Returns errors
     * @return errors
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Saves an error
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

        return ((var == null) || (var.trim().length() == 0) ? null : var.trim());
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
}