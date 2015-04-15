package forms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import beans.Administrator;
import beans.Group;
import dao.GroupDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GroupForm {
    private static final String              ADMINISTRATOR_SESSION = "administratorSession";
    private static final String              ID_FIELD              = "id";
    private static final String              NAME_FIELD            = "name";
    private              Map<String, String> errors                = new HashMap<String, String>();
    private GroupDao groupDao;

    /**
     * Constructor
     * @param groupDao
     */
    public GroupForm(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    /**
     * Creates a group into database
     * @param request
     * @return group
     */
    public Group create(HttpServletRequest request) {
        String        name    = getFieldVar(request, NAME_FIELD);
        Administrator creator = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Group         group   = new Group();

        treatName(name, group);
        treatGroup(group);
        treatCreator(creator, group);

        if (errors.isEmpty()) {
            groupDao.create(group);
        }

        return group;
    }

    /**
     * Searches one or more groups into database
     * @param request
     * @return groups
     */
    public Set<Group> search(HttpServletRequest request) {
        String     id     = getFieldVar(request, ID_FIELD);
        String     name   = getFieldVar(request, NAME_FIELD);
        Set<Group> groups = new HashSet<Group>();
        Group      group  = new Group();

        treatId(id, group);
        group.setName(name);
        groups = groupDao.search(group);

        return groups;
    }

    /**
     * Edits a group into database
     * @param request
     * @return group
     */
    public Group edit(HttpServletRequest request) {
        String        id     = getFieldVar(request, ID_FIELD);
        String        name   = getFieldVar(request, NAME_FIELD);
        Administrator editor = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Group         group  = new Group();

        treatId(id, group);
        treatName(name, group);
        treatGroup(group);
        treatEditor(editor, group);

        if (errors.isEmpty()) {
            groupDao.edit(group);
        }

        return group;
    }

    /**
     * Returns a group into database
     * @param request
     * @return group
     */
    public Group get(HttpServletRequest request) {
        String id    = getFieldVar(request, ID_FIELD);
        Group  group = new Group();

        treatId(id, group);
        group = groupDao.get(group);

        return group;
    }

    /**
     * Deletes a group into database
     * @param request
     */
    public void delete(HttpServletRequest request) {
        String        id     = getFieldVar(request, ID_FIELD);
        Administrator editor = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Group         group  = new Group();

        treatId(id, group);
        treatEditor(editor, group);
        groupDao.delete(group);
    }

    /**
     * Treats group's id
     * @param id
     * @param group
     */
    private void treatId(String id, Group group) {
        try {
            validateId(id);
            group.setId(Long.parseLong(id));
        }
        catch (Exception e) {
            setError(ID_FIELD, e.getMessage());
        }
    }

    /**
     * Treats group's name
     * @param name
     * @param group
     */
    private void treatName(String name, Group group) {
        try {
            validateName(name);
            group.setName(name.toUpperCase());
        }
        catch (Exception e) {
            setError(NAME_FIELD, e.getMessage());
        }
    }

    /**
     * Treats a group
     * @param group
     */
    private void treatGroup(Group group) {
        try {
            validateGroup(group);
        }
        catch (Exception e) {
            setError("group", e.getMessage());
        }
    }

    /**
     * Treats group's creator
     * @param creator
     * @param group
     */
    private void treatCreator(Administrator creator, Group group) {
        try {
            validateCreator(creator);
            group.setCreator(creator);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Treats group's editor
     * @param editor
     * @param matiere
     */
    private void treatEditor(Administrator editor, Group group) {
        try {
            validateCreator(editor);
            group.setEditor(editor);
        }
        catch (Exception e) {
            setError("administrator", e.getMessage());
        }
    }

    /**
     * Validates group's id
     * @param id
     * @throws Exception
     */
    private void validateId(String id) throws Exception {
        if ((id == null)) {
            throw new Exception("The identification number is null");
        }
    }

    /**
     * Validates a group's name
     * @param name
     * @throws Exception
     */
    private void validateName(String name) throws Exception {
        if ((name == null) || (name.length() < 2) || (name.length() > 50)) {
            throw new Exception("Please enter a name of 3 to 50 characters");
        }
    }

    /**
     * Validates a group
     * @param group
     * @throws Exception
     */
    private void validateGroup(Group group) throws Exception {
        if (groupDao.check(group) != 0) {
            throw new Exception("This group already exists");
        }
    }

    /**
     * Validates a group's creator
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
     * Returns a session var
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