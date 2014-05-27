package servlets.groupe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.GroupeDao;
import forms.GroupeForm;


@WebServlet("/ai/groupe/suppression")
public class GroupeSuppression extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY = "daofactory";
	private GroupeDao groupeDao;
	
	public void init() throws ServletException 
    {
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupeDao();
    }

    public GroupeSuppression() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		GroupeForm form = new GroupeForm(this.groupeDao);
		
		form.supprimerGroupe(request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/groupe");  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

}
