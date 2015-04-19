package forms;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Administrator;
import dao.AdministratorDao;

public class AdministratorForm {
    private static final String              ADMINISTRATOR_SESSION = "administratorSession";
    private static final String              ID_FIELD              = "id";
    private static final String              LAST_NAME_FIELD       = "lastName";
    private static final String              FIRST_NAME_FIELD      = "firstName";
    private static final String              EMAIL_ADDRESS_FIELD   = "emailAddress";
    private static final String              PASSWORD_FIELD        = "password";
    private static final String              CONFIRMATION_FIELD    = "confirmation";
    private              Map<String, String> errors                = new HashMap<String, String>();
    private AdministratorDao administratorDao;

    /**
     * Constructor
     * @param administratorDao
     */
    public AdministratorForm(AdministratorDao administratorDao) {
        this.administratorDao = administratorDao;
    }

    /**
     * Creates an administrator into database
     * @param request
     * @return administrator
     */
    public Administrator create(HttpServletRequest request) {
        String        lastName      = getFieldVar(request, LAST_NAME_FIELD);
        String        firstName     = getFieldVar(request, FIRST_NAME_FIELD);
        String        emailAddress  = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String        password      = getFieldVar(request, PASSWORD_FIELD);
        String        confirmation  = getFieldVar(request, CONFIRMATION_FIELD);
        Administrator creator       = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Administrator administrator = new Administrator();

        treatLastName(lastName, administrator);
        treatFirstName(firstName, administrator);
        treatEmailAddress(emailAddress, administrator);
        treatPassword(password, confirmation, administrator);
        treatAdministrator(administrator);
        treatCreator(creator, administrator);

        if (errors.isEmpty()) {
            administratorDao.create(administrator);
        }

        return administrator;
    }

    /**
     * Searches one or more administrators into database
     * @param request
     * @return administrators
     */
    public Set<Administrator> search(HttpServletRequest request) {
        String             id             = getFieldVar(request, ID_FIELD);
        String             lastName       = getFieldVar(request, LAST_NAME_FIELD);
        String             firstName      = getFieldVar(request, FIRST_NAME_FIELD);
        String             emailAddress   = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        Set<Administrator> administrators = new HashSet<Administrator>();
        Administrator      administrator  = new Administrator();

        if (id != null) {
            administrator.setId(Long.parseLong(id));
        }

        administrator.setLastName(lastName);
        administrator.setFirstName(firstName);
        administrator.setEmailAddress(emailAddress);
        administrators = administratorDao.search(administrator);

        return administrators;
    }

    /**
     * Returns an administrator into database
     * @param request
     * @return administrator
     */
    public Administrator get(HttpServletRequest request) {
        String        id            = getFieldVar(request, ID_FIELD);
        Administrator administrator = new Administrator();

        treatId(id, administrator);
        administrator = administratorDao.get(administrator);

        return administrator;
    }

    /**
     * Edits an administrator into database
     * @param request
     * @return administrator
     */
    public Administrator edit(HttpServletRequest request) {
        String        id            = getFieldVar(request, ID_FIELD);
        String        lastName      = getFieldVar(request, LAST_NAME_FIELD);
        String        firstName     = getFieldVar(request, FIRST_NAME_FIELD);
        String        emailAddress  = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String        password      = getFieldVar(request, PASSWORD_FIELD);
        String        confirmation  = getFieldVar(request, CONFIRMATION_FIELD);
        Administrator editor        = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Administrator administrator = new Administrator();

        treatId(id, administrator);
        treatLastName(lastName, administrator);
        treatFirstName(firstName, administrator);
        treatEmailAddress(emailAddress, administrator);
        treatAdministrator(administrator);
        treatEditor(editor, administrator);

        if ((password != null) || (confirmation != null)) {
            treatPassword(password, confirmation, administrator);
        }

        if (errors.isEmpty()) {
            administratorDao.edit(administrator);
        }

        return administrator;
    }

    /**
     * Deletes an administrator into database
     * @param request
     */
    public void delete(HttpServletRequest request) {
        String        id            = getFieldVar(request, ID_FIELD);
        Administrator editor        = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Administrator administrator = new Administrator();

        treatId(id, administrator);
        treatEditor(editor, administrator);

        if (errors.isEmpty()) {
            administratorDao.delete(administrator);
        }
    }

    /**
     * Checks administrator's login into database
     * @param request
     * @return administrator
     */
    public Administrator checkLogin(HttpServletRequest request) {
        String        emailAddress  = getFieldVar(request, EMAIL_ADDRESS_FIELD);
        String        password      = getFieldVar(request, PASSWORD_FIELD);
        Administrator administrator = new Administrator();

        treatEmailAddress(emailAddress, administrator);
        administrator.setPassword(cryptPassword(password));
        administrator = administratorDao.checkLogin(administrator);
        treatLogin(administrator);
        administrator.setPassword(cryptPassword(password));
        administrator.setEmailAddress(emailAddress);

        return administrator;
    }

    /**
     * Treats administrator's id
     * @param id
     * @param administrator
     */
    private void treatId(String id, Administrator administrator) {
        try {
            validateId(id);
            administrator.setId(Long.parseLong(id));
        }
        catch (Exception e) {
            setError(ID_FIELD, e.getMessage());
        }
    }

    /**
     * Treats administrator's last name
     * @param lastName
     * @param administrator
     */
    private void treatLastName(String lastName, Administrator administrator) {
        try {
            validateLastName(lastName);
            administrator.setLastName(lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase());
        }
        catch (Exception e) {
            setError(LAST_NAME_FIELD, e.getMessage());
        }
    }

    /**
     * Treats administrator's first name
     * @param firstName
     * @param administrator
     */
    private void treatFirstName(String firstName, Administrator administrator) {
        try {
            validateFirstName(firstName);
            administrator.setFirstName(firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase());
        }
        catch (Exception e) {
            setError(FIRST_NAME_FIELD, e.getMessage());
        }
    }

    /**
     * Treats administrator's email address
     * @param emailAddress
     * @param administrator
     */
    private void treatEmailAddress(String emailAddress, Administrator administrator) {
        try {
            validateEmailAddress(emailAddress);
            administrator.setEmailAddress(emailAddress.trim().toLowerCase());
        }
        catch (Exception e) {
            setError(EMAIL_ADDRESS_FIELD, e.getMessage());
        }
    }

    /**
     * Treats administrator's password
     * @param password
     * @param confirmation
     * @param administrator
     */
    private void treatPassword(String password, String confirmation, Administrator administrator) {
        try {
            validatePassword(password, confirmation);
            password = cryptPassword(password);
            administrator.setPassword(password);
        }
        catch (Exception e) {
            setError(PASSWORD_FIELD, e.getMessage());
            setError(CONFIRMATION_FIELD, null);
        }
    }

    /**
     * Treats administrator's login
     * @param administrator
     */
    private void treatLogin(Administrator administrator) {
        try {
            validateLogin(administrator);
        }
        catch (Exception e) {
            setError("login", e.getMessage());
        }
    }

    /**
     * Treats an administrator
     * @param administrator
     */
    private void treatAdministrator(Administrator administrator) {
        try {
            validateAdministrator(administrator);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Treats administrator's creator
     * @param creator
     * @param administrator
     */
    private void treatCreator(Administrator creator, Administrator administrator) {
        try {
            validateCreator(creator);
            administrator.setCreator(creator);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Treats administrator's editor
     * @param creator
     * @param administrator
     */
    private void treatEditor(Administrator editor, Administrator administrator) {
        try {
            validateCreator(editor);
            administrator.setEditor(editor);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Validates administrator's id
     * @param id
     * @throws Exception
     */
    private void validateId(String id) throws Exception {
        if ((id == null)) {
            throw new Exception("The identification number is null");
        }
    }

    /**
     * Validates administrator's last name
     * @param lastName
     * @throws Exception
     */
    private void validateLastName(String lastName) throws Exception {
        if ((lastName == null) || (lastName.length() < 2) || (lastName.length() > 50)) {
            throw new Exception("Please enter a last name of 2 to 50 characters");
        }
    }

    /**
     * Validates administrator's first name
     * @param firstName
     * @throws Exception
     */
    private void validateFirstName(String firstName) throws Exception {
        if ((firstName == null) || (firstName.length() < 2) || (firstName.length() > 50)) {
            throw new Exception("Please enter a first name of 2-50 characters");
        }
    }

    /**
     * Validates administrator's email address
     * @param emailAddress
     * @throws Exception
     */
    private void validateEmailAddress(String emailAddress) throws Exception {
        if ((emailAddress == null) || (emailAddress.length() < 8) || (emailAddress.length() > 100) || (!emailAddress.matches("[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}"))) {
            throw new Exception("Please enter a valid email address");
        }
    }

    /**
     * Validate administrator's password
     * @param password
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
     * Validates an administrator (Checks the existance)
     * @param administrator
     * @throws Exception
     */
    private void validateAdministrator(Administrator administrator) throws Exception {
        if (administratorDao.check(administrator) != 0) {
            throw new Exception("This administrator already exists");
        }
    }

    /**
     * Validates administrator's login
     * @param administrator
     * @throws Exception
     */
    private void validateLogin(Administrator administrator) throws Exception {
        if (administrator.getId() == null) {
            throw new Exception("Your email address or password is incorrect");
        }
    }

    /**
     * Validates administrator's creator
     * @param creator
     * @throws Exception
     */
    private void validateCreator(Administrator creator) throws Exception {
        if (creator.getId() == null) {
            throw new Exception("Unknown administrator");
        }
    }

    /**
     * Cryptes a password (SHA-256)
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
     * Save an error
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

        if ((var == null) || (var.trim().length() == 0)) {
            return null;
        }
        else {
            return var.trim();
        }
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