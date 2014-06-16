package servlets.ai.administrateur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.DAOFactory;
import dao.AdministrateurDao;
import forms.AdministrateurForm;

@SuppressWarnings("serial")
@WebServlet("/ai/administrateur/suppression")
public class AdministrateurSuppression extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY           = "daofactory";
	private static final String ATT_SESSION_ADMINISTRATEUR = "sessionAdministrateur";
	private static final String ATT_ADMINISTRATEUR         = "administrateur";
	private static final String VUE_SUPPRESSION            = "/WEB-INF/ai/administrateur/suppression.jsp";
    private AdministrateurDao administrateurDao;

    public AdministrateurSuppression() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.administrateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getAdministrateurDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		AdministrateurForm form = new AdministrateurForm(this.administrateurDao);
		beans.Administrateur administrateur = form.trouverAdministrateur(request);
		
        request.setAttribute(ATT_ADMINISTRATEUR, administrateur);
        this.getServletContext().getRequestDispatcher(VUE_SUPPRESSION).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		AdministrateurForm form = new AdministrateurForm(this.administrateurDao);
		beans.Administrateur sessionAdministrateur = (beans.Administrateur) session.getAttribute(ATT_SESSION_ADMINISTRATEUR);
		
		form.supprimerAdministrateur(sessionAdministrateur, request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/administrateur");  
	}

}
