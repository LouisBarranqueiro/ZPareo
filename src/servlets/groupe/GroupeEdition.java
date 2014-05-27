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

@WebServlet("/ai/groupe/edition")
public class GroupeEdition extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY   = "daofactory";
	public static final String ATT_GROUPE         = "groupe";
    public static final String ATT_FORM           = "form";
	private static final String VUE_EDITION       = "/WEB-INF/groupe/edition.jsp";
	private GroupeDao groupeDao;
	
	public void init() throws ServletException 
    {
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupeDao();
    }

    public GroupeEdition() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		GroupeForm form = new GroupeForm( this.groupeDao );
		
        Groupe groupe = form.trouverGroupe( request );
        request.setAttribute(ATT_GROUPE , groupe);
        this.getServletContext().getRequestDispatcher(VUE_EDITION).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		GroupeForm form = new GroupeForm(this.groupeDao);
		Groupe groupe = form.editerGroupe(request);
		
		if(form.getErreurs().isEmpty())
	    {
	    	response.sendRedirect("http://localhost:8080/ZPareo/ai/groupe");   
	    }
	    else
	    {
	    	request.setAttribute(ATT_FORM, form);
	        request.setAttribute(ATT_GROUPE, groupe);
	        this.getServletContext().getRequestDispatcher(VUE_EDITION).forward(request, response);   
	    }
	}

}
