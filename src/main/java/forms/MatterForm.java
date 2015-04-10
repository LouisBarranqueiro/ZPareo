package forms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import beans.Administrator;
import beans.Matter;
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
    public Matter create(HttpServletRequest request) 
    {
        String name           = getFieldVar(request, NAME_FIELD);
        Administrator creator = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
        Matter matter         = new Matter();
        
        try 
        {
            treatName(name, matter);
            treatMatter(matter);
            treatCreator(creator, matter);
            
            if (errors.isEmpty()) matterDao.create(matter);
            
        } 
        catch ( Exception e ) 
        {
        	e.printStackTrace();
        }

        return matter;
    }
    
    /**
     * Searches one or more matters into database
     * 
     * @param request
     * @return matters
     */
    public Set<Matter> search(HttpServletRequest request) 
    {
    	String id           = getFieldVar(request, ID_FIELD);
    	String name         = getFieldVar(request, NAME_FIELD);
    	Set<Matter> matters = new HashSet<Matter>();
    	Matter matter       = new Matter();
    	
    	treatId(id, matter);
    	matter.setName(name);
        matters = matterDao.search(matter);
        
    	return matters;
    }
   
    /**
     * Edits a matter into database
     * 
     * @param request
     * @return matter
     */
    public Matter edit(HttpServletRequest request)
    {
    	String id            = getFieldVar(request, ID_FIELD);
    	String name          = getFieldVar(request, NAME_FIELD);
    	Administrator editor = (Administrator) getSessionVar(request, ADMINISTRATOR_SESSION);
    	Matter matter        = new Matter();
    	
    	treatId(id, matter);
    	treatName(name, matter);
    	treatMatter(matter);
        treatEditor(editor, matter);
        
    	if (errors.isEmpty()) matterDao.edit(matter);
       	

    	return matter;
    }
    
    /**
     * Returns a matter into database
     * 
     * @param request
     * @return matter
     */
    public Matter get(HttpServletRequest request)
    {
    	String id     = getFieldVar(request, ID_FIELD);
    	Matter matter = new Matter();
    	
    	treatId(id, matter);
    	matter = matterDao.get( matter);
    	
    	return matter;
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
    	Matter matter        = new Matter();
    	
    	treatId(id, matter);
    	treatEditor(editor, matter);
    	matterDao.delete(matter);
    }
   
    /**
     *  Treats matter's id
     *  
     * @param id
     * @param matter
     */
    private void treatId(String id, Matter matter)
    {
    	try
    	{
    		validateId(id);
    		matter.setId(Long.parseLong(id));
    	}
    	catch (Exception e) 
    	{
            setError(ID_FIELD, e.getMessage());
        }
    }
    
    /**
     *  Treats matter's name
     *  
     * @param name
     * @param matter
     */
    private void treatName(String name, Matter matter) 
    {
    	try 
    	{
            validateName(name);
        } 
    	catch (Exception e) 
    	{
            setError(NAME_FIELD, e.getMessage());
        }
    	
        matter.setName(name);
    }
    
    /**
     *  Treats a matter
     *  
     * @param matter
     */
    private void treatMatter(Matter matter) 
    {
    	try 
    	{
    		validateMatter(matter);
        } 
    	catch (Exception e) 
    	{
            setError("matter", e.getMessage());
        }
    }
    
    /**
     *  Treats matter's creator
     *  
     * @param creator
     * @param matter
     */
    private void treatCreator(Administrator creator, Matter matter) 
    {
    	try 
    	{
    		validateCreator(creator);
        } 
    	catch (Exception e) 
    	{
            setError("administrateur", e.getMessage());
        }
    	
    	matter.setCreator(creator);
    }
    
    /**
     *  Treats matter's editor
     *  
     * @param editor
     * @param matter
     */
    private void treatEditor(Administrator editor, Matter matter) 
    {
    	try 
    	{
    		validateCreator(editor);
        } 
    	catch (Exception e) 
    	{
            setError("administrateur", e.getMessage());
        }
    	
    	matter.setEditor(editor);
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
     * Validates a matter (Check the existance)
     * 
     * @param matter
     * @throws Exception
     */
    private void validateMatter(Matter matter) throws Exception 
    {
        if (matterDao.check(matter) != 0) throw new Exception("Cette matière existe déja");
        
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

