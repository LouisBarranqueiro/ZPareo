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
@WebServlet("/ai/administrateur/creation")
public class AdministratorCreation extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY   = "daofactory";
	private static final String ADMINISTRATOR      = "administrator";
	private static final String ADMINISTRATOR_FORM = "administratorForm";
	private static final String VIEW               = "/WEB-INF/ai/administrator/creation.jsp";
    private AdministratorDao administratorDao;

    public AdministratorCreation() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.administratorDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getAdministratorDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		AdministratorForm administratorForm = new AdministratorForm(this.administratorDao);
		System.out.println("etst");
		beans.Administrator administrator   = administratorForm.create(request);
		System.out.println("etst");
        if (administratorForm.getErrors().isEmpty())
        {
        	System.out.println("erreur");
        	response.sendRedirect("http://localhost:8080/ZPareo/ai/administrateur");   
        }
        else
        {
        	request.setAttribute(ADMINISTRATOR_FORM, administratorForm);
        	request.setAttribute(ADMINISTRATOR, administrator);
        	this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
        }
	}

}
