package servlets.matiere;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forms.MatiereForm;
import models.Matiere;
import dao.DAOFactory;
import dao.MatiereDao;

@WebServlet("/ai/matiere/creation")
public class MatiereCreation extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_MATIERE      = "matiere";
    public static final String ATT_FORM         = "form";
	private static final String VUE_CREATION    = "/WEB-INF/matiere/creation.jsp";
    private MatiereDao matiereDao;

    public MatiereCreation() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.matiereDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatiereDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        this.getServletContext().getRequestDispatcher(VUE_CREATION).forward(request, response);   
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        MatiereForm form = new MatiereForm(this.matiereDao);
        Matiere matiere = form.creerMatiere(request);
		
       if(form.getErreurs().isEmpty())
       {
           response.sendRedirect("http://localhost:8080/ZPareo/ai/matiere");   
       }
       else
       {
    	   request.setAttribute(ATT_FORM, form);
           request.setAttribute(ATT_MATIERE, matiere);
    	   this.getServletContext().getRequestDispatcher(VUE_CREATION).forward(request, response);   
       }
	}
}
