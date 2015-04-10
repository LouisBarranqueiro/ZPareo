package servlets.ai.administrator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DAOFactory;
import dao.AdministratorDao;
import forms.AdministratorForm;

@SuppressWarnings("serial")
@WebServlet("/ai/administrateur/suppression")
public class AdministratorSuppression extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ADMINISTRATOR    = "administrator";
	private static final String VIEW             = "/WEB-INF/ai/administrator/suppression.jsp";
    private AdministratorDao administrateurDao;

    public AdministratorSuppression() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.administrateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getAdministratorDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		AdministratorForm administratorForm = new AdministratorForm(this.administrateurDao);
		beans.Administrator administrator   = administratorForm.get(request);
		
        request.setAttribute(ADMINISTRATOR, administrator);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		AdministratorForm administratorForm = new AdministratorForm(this.administrateurDao);
		
		administratorForm.delete(request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/administrateur");  
	}

}
