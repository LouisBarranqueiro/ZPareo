package forms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import beans.Administrator;
import beans.Subject;
import dao.SubjectDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SubjectForm {
    private static final String              ADMINISTRATOR_SESSION = "administratorSession";
    private static final String              ID_FIELD              = "id";
    private static final String              NAME_FIELD            = "name";
    private              Map<String, String> errors                = new HashMap<String, String>();
    private SubjectDao subjectDao;

    /**
     * Constructor
     * @param subjectDao
     */
    public SubjectForm(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    /**
     * Creates a subject into database
     * @param request
     * @return subject
     */
    public Subject create(HttpServletRequest request) {
        String        name    = getFieldVar(request, NAME_FIELD);
        Administrator creator = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Subject       subject = new Subject();

        treatName(name, subject);
        treatSubject(subject);
        treatCreator(creator, subject);

        if (errors.isEmpty()) {
            subjectDao.create(subject);
        }

        return subject;
    }

    /**
     * Searches one or more subjects into database
     * @param request
     * @return subjects
     */
    public Set<Subject> search(HttpServletRequest request) {
        String       id       = getFieldVar(request, ID_FIELD);
        String       name     = getFieldVar(request, NAME_FIELD);
        Set<Subject> subjects = new HashSet<Subject>();
        Subject      subject  = new Subject();

        treatId(id, subject);
        subject.setName(name);
        subjects = subjectDao.search(subject);

        return subjects;
    }

    /**
     * Edits a subject into database
     * @param request
     * @return subject
     */
    public Subject edit(HttpServletRequest request) {
        String        id      = getFieldVar(request, ID_FIELD);
        String        name    = getFieldVar(request, NAME_FIELD);
        Administrator editor  = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Subject       subject = new Subject();

        treatId(id, subject);
        treatName(name, subject);
        treatSubject(subject);
        treatEditor(editor, subject);

        if (errors.isEmpty()) {
            subjectDao.edit(subject);
        }

        return subject;
    }

    /**
     * Returns a subject into database
     * @param request
     * @return subject
     */
    public Subject get(HttpServletRequest request) {
        String  id      = getFieldVar(request, ID_FIELD);
        Subject subject = new Subject();

        treatId(id, subject);
        subject = subjectDao.get(subject);

        return subject;
    }

    /**
     * Deletes a subject into database
     * @param request
     */
    public void delete(HttpServletRequest request) {
        String        id      = getFieldVar(request, ID_FIELD);
        Administrator editor  = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Subject       subject = new Subject();

        treatId(id, subject);
        treatEditor(editor, subject);
        subjectDao.delete(subject);
    }

    /**
     * Treats subject's id
     * @param id
     * @param subject
     */
    private void treatId(String id, Subject subject) {
        try {
            validateId(id);
            subject.setId(Long.parseLong(id));
        }
        catch (Exception e) {
            setError(ID_FIELD, e.getMessage());
        }
    }

    /**
     * Treats subject's name
     * @param name
     * @param subject
     */
    private void treatName(String name, Subject subject) {
        try {
            validateName(name);
            subject.setName(name);
        }
        catch (Exception e) {
            setError(NAME_FIELD, e.getMessage());
        }
    }

    /**
     * Treats a subject
     * @param subject
     */
    private void treatSubject(Subject subject) {
        try {
            validateSubject(subject);
        }
        catch (Exception e) {
            setError("subject", e.getMessage());
        }
    }

    /**
     * Treats subject's creator
     * @param creator
     * @param subject
     */
    private void treatCreator(Administrator creator, Subject subject) {
        try {
            validateCreator(creator);
            subject.setCreator(creator);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Treats subject's editor
     * @param editor
     * @param subject
     */
    private void treatEditor(Administrator editor, Subject subject) {
        try {
            validateCreator(editor);
            subject.setEditor(editor);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Validates subject's id
     * @param id
     * @throws Exception
     */
    private void validateId(String id) throws Exception {
        if ((id == null)) {
            throw new Exception("The identification number is null");
        }
    }

    /**
     * Validates subject's name
     * @param name
     * @throws Exception
     */
    private void validateName(String name) throws Exception {
        if ((name == null) || (name.length() < 3) || (name.length() > 55)) {
            throw new Exception("Please enter a name of 3 to 55 characters");
        }
    }

    /**
     * Validates a subject (Check the existance)
     * @param subject
     * @throws Exception
     */
    private void validateSubject(Subject subject) throws Exception {
        if (subjectDao.check(subject) != 0) {
            throw new Exception("This subject already exists");
        }
    }

    /**
     * Validates subject's creator
     * @param creator
     * @throws Exception
     */
    private void validateCreator(Administrator creator) throws Exception {
        if (creator.getId() == null) {
            throw new Exception("Unknown administrator");
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
     * Returns a field var
     * @param request
     * @param fieldVar
     * @return var
     */
    private static String getFieldVar(HttpServletRequest request, String fieldVar) {
        String var = request.getParameter(fieldVar);

        return (((var == null) || (var.trim().length() == 0)) ? null : var.trim());
    }

    /**
     * Returns a sesion var
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