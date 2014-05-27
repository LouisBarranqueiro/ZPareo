package servlets.groupe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Groupe;
import dao.DAOFactory;
import dao.GroupeDao;
import forms.GroupeForm;


@WebServlet("/ai/groupe/creation")
public class GroupeCreation extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_GROUPE      = "groupe";
    public static final String ATT_FORM         = "form";
	private static final String VUE_CREATION    = "/WEB-INF/groupe/creation.jsp";
    private GroupeDao groupeDao;

    public GroupeCreation() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupeDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        this.getServletContext().getRequestDispatcher(VUE_CREATION).forward( request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        GroupeForm form = new GroupeForm(this.groupeDao);
        
        Groupe groupe = form.creerGroupe(request);
		
       if( form.getErreurs().isEmpty() )
       {
           response.sendRedirect("http://localhost:8080/ZPareo/ai/groupe");   
       }
       else
       {
    	   request.setAttribute(ATT_FORM, form);
           request.setAttribute(ATT_GROUPE, groupe);
    	   this.getServletContext().getRequestDispatcher(VUE_CREATION).forward(request, response);   
       }
	}

}
