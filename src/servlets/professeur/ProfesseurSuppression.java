package servlets.professeur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Administrateur;
import dao.DAOFactory;
import dao.ProfesseurDao;
import forms.ProfesseurForm;

@SuppressWarnings("serial")
@WebServlet("/ai/professeur/suppression")
public class ProfesseurSuppression extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY            = "daofactory";
	private static final String ATT_SESSION_ADMINISTRATEUR = "sessionAdministrateur";
	private ProfesseurDao professeurDao;
	
    public ProfesseurSuppression() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
    	this.professeurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProfesseurDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Administrateur editeur = (Administrateur) session.getAttribute(ATT_SESSION_ADMINISTRATEUR);
		ProfesseurForm form = new ProfesseurForm(this.professeurDao);
		
		form.supprimerProfesseur(editeur, request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/professeur");  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

}
