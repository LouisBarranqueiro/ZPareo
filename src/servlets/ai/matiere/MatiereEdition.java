package servlets.ai.matiere;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.DAOFactory;
import dao.MatiereDao;
import forms.MatiereForm;
import beans.Administrateur;
import beans.Matiere;

@SuppressWarnings("serial")
@WebServlet("/ai/matiere/edition")
public class MatiereEdition extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY           = "daofactory";
	private static final String ATT_SESSION_ADMINISTRATEUR = "sessionAdministrateur";
	private static final String ATT_MATIERE                = "matiere";
	private static final String ATT_FORM                   = "form";
	private static final String VUE_EDITION                = "/WEB-INF/matiere/edition.jsp";
	private MatiereDao matiereDao;
	
	public void init() throws ServletException 
    {
        this.matiereDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatiereDao();
    }

    public MatiereEdition() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		MatiereForm form = new MatiereForm(this.matiereDao);
        Matiere matiere = form.trouverMatiere(request);
        
        request.setAttribute(ATT_MATIERE , matiere);
        this.getServletContext().getRequestDispatcher(VUE_EDITION).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Administrateur editeur = (Administrateur) session.getAttribute(ATT_SESSION_ADMINISTRATEUR);
		MatiereForm form = new MatiereForm(this.matiereDao);
		Matiere matiere = form.editerMatiere(editeur, request);
		
		if (form.getErreurs().isEmpty())
	    {
	    	response.sendRedirect("http://localhost:8080/ZPareo/ai/matiere");   
	    }
	    else
	    {
	    	request.setAttribute(ATT_FORM, form);
	        request.setAttribute(ATT_MATIERE, matiere);
	        this.getServletContext().getRequestDispatcher(VUE_EDITION).forward( request, response );   
	    }
	}
}
