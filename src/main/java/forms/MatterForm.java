package forms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import beans.Administrator;
import beans.Subject;
import dao.MatterDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class MatterForm 
{
	private static final String ADMINISTRATOR_SESSION = "administratorSession";
	private static final String ID_FIELD              = "id";
    private static final String NAME_FIELD            = "name";
    private Map<String, String> errors                = new HashMap<String, String>();
    private MatterDao matterDao;

    /**
     * Constructor
     * 
     * @param matterDao
     */
    public MatterForm(MatterDao matterDao) 
    {
    	this.matterDao = matterDao;
    }
    
    /**
     * Creates a matter into database
     * 
     * @param request
     * @return matter
     */
    public Subject create(HttpServletRequest request)
    {
        String name           = getFieldVar(request, NAME_FIELD);
        Administrator creator = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Subject subject = new Subject();
        
        try 
        {
            treatName(name, subject);
            treatMatter(subject);
            treatCreator(creator, subject);
            
            if (errors.isEmpty()) matterDao.create(subject);
            
        } 
        catch ( Exception e ) 
        {
        	e.printStackTrace();
        }

        return subject;
    }
    
    /**
     * Searches one or more matters into database
     * 
     * @param request
     * @return matters
     */
    public Set<Subject> search(HttpServletRequest request)
    {
    	String id           = getFieldVar(request, ID_FIELD);
    	String name         = getFieldVar(request, NAME_FIELD);
    	Set<Subject> subjects = new HashSet<Subject>();
    	Subject      subject  = new Subject();
    	
    	treatId(id, subject);
    	subject.setName(name);
        subjects = matterDao.search(subject);
        
    	return subjects;
    }
   
    /**
     * Edits a matter into database
     * 
     * @param request
     * @return matter
     */
    public Subject edit(HttpServletRequest request)
    {
    	String id            = getFieldVar(request, ID_FIELD);
    	String name          = getFieldVar(request, NAME_FIELD);
    	Administrator editor = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
    	Subject subject = new Subject();
    	
    	treatId(id, subject);
    	treatName(name, subject);
    	treatMatter(subject);
        treatEditor(editor, subject);
        
    	if (errors.isEmpty()) matterDao.edit(subject);
       	

    	return subject;
    }
    
    /**
     * Returns a matter into database
     * 
     * @param request
     * @return matter
     */
    public Subject get(HttpServletRequest request)
    {
    	String id     = getFieldVar(request, ID_FIELD);
    	Subject subject = new Subject();
    	
    	treatId(id, subject);
    	subject = matterDao.get(subject);
    	
    	return subject;
    }
    
    /**
     * Deletes a matter into database
     * 
     * @param request
     */
    public void delete(HttpServletRequest request)
    {
    	String id            = getFieldVar(request, ID_FIELD);
    	Administrator editor = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
    	Subject subject = new Subject();
    	
    	treatId(id, subject);
    	treatEditor(editor, subject);
    	matterDao.delete(subject);
    }
   
    /**
     *  Treats subject's id
     *  
     * @param id
     * @param subject
     */
    private void treatId(String id, Subject subject)
    {
    	try
    	{
    		validateId(id);
    		subject.setId(Long.parseLong(id));
    	}
    	catch (Exception e) 
    	{
            setError(ID_FIELD, e.getMessage());
        }
    }
    
    /**
     *  Treats subject's name
     *  
     * @param name
     * @param subject
     */
    private void treatName(String name, Subject subject)
    {
    	try 
    	{
            validateName(name);
        } 
    	catch (Exception e) 
    	{
            setError(NAME_FIELD, e.getMessage());
        }
    	
        subject.setName(name);
    }
    
    /**
     *  Treats a subject
     *  
     * @param subject
     */
    private void treatMatter(Subject subject)
    {
    	try 
    	{
    		validateMatter(subject);
        } 
    	catch (Exception e) 
    	{
            setError("subject", e.getMessage());
        }
    }
    
    /**
     *  Treats subject's creator
     *  
     * @param creator
     * @param subject
     */
    private void treatCreator(Administrator creator, Subject subject)
    {
    	try 
    	{
    		validateCreator(creator);
        } 
    	catch (Exception e) 
    	{
            setError("administrateur", e.getMessage());
        }
    	
    	subject.setCreator(creator);
    }
    
    /**
     *  Treats subject's editor
     *  
     * @param editor
     * @param subject
     */
    private void treatEditor(Administrator editor, Subject subject)
    {
    	try 
    	{
    		validateCreator(editor);
        } 
    	catch (Exception e) 
    	{
            setError("administrateur", e.getMessage());
        }
    	
    	subject.setEditor(editor);
    }
    
    /**
     * Validates matter's id
     * 
     * @param id
     * @throws Exception
     */
    private void validateId(String id) throws Exception 
    {
        if ((id == null)) throw new Exception("Le numéro d'identification est nul");
    }
    
    /**
     * Validates matter's name
     * 
     * @param name
     * @throws Exception
     */
    private void validateName(String name) throws Exception 
    {
        if ((name == null) || (name.length() < 3) || (name.length() > 55)) throw new Exception("Veuillez entrer un name de 3 à 55 caractères");
        
    }
    
    /**
     * Validates a subject (Check the existance)
     * 
     * @param subject
     * @throws Exception
     */
    private void validateMatter(Subject subject) throws Exception
    {
        if (matterDao.check(subject) != 0) throw new Exception("Cette matière existe déja");
        
    }
    
    /**
     * Validates matter's creator
     * 
     * @param creator
     * @throws Exception
     */
    private void validateCreator(Administrator creator) throws Exception 
    {
        if (creator.getId() == null) throw new Exception("Administrateurr inconnu");
        
    }
 
    /**
     * Returns errors
     * 
     * @return errors
     */
    public Map<String, String> getErrors() 
    {
        return errors;
    }
    
    /**
     * Saves an error
     * 
     * @param field
     * @param message
     */
    private void setError(String field, String message)
    {
        errors.put(field, message);
    }

    /**
     * Returns a field var
     * 
     * @param request
     * @param fieldVar
     * @return var
     */
    private static String getFieldVar(HttpServletRequest request, String fieldVar) 
    {
        String var = request.getParameter(fieldVar);
   
        return (((var == null) || (var.trim().length() == 0)) ? null : var.trim());
    }
    
    /**
     * Returns a sesion var
     * 
     * @param request
     * @param sessionVar
     * @return object
     */
    private static Object getSessionVar(HttpServletRequest request, String sessionVar) 
    {
    	HttpSession session = request.getSession();
    	Object object = session.getAttribute(sessionVar);
    	
        return ((object == null) ? null : object);
    }
}

