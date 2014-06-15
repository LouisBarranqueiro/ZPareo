package servlets.connexion;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.AdministrateurDao;
import dao.DAOFactory;
import dao.EtudiantDao;
import dao.ProfesseurDao;
import forms.AdministrateurForm;
import forms.EtudiantForm;
import forms.ProfesseurForm;

@SuppressWarnings("serial")
@WebServlet("/connexion")
public class Connexion extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY           = "daofactory";
	private static final String ATT_UTILISATEUR            = "utilisateur";
	private static final String ATT_SESSION_ETUDIANT       = "sessionEtudiant";
	private static final String ATT_SESSION_PROFESSEUR     = "sessionProfesseur";
	private static final String ATT_SESSION_ADMINISTRATEUR = "sessionAdministrateur";
	private static final String ATT_FORM                   = "form";
	private static final String VUE                        = "/WEB-INF/connexion.jsp";
	private AdministrateurDao administrateurDao;
	private EtudiantDao etudiantDao;
	private ProfesseurDao professeurDao;
	
    public Connexion() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
    	this.administrateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getAdministrateurDao();
    	this.etudiantDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getEtudiantDao();
    	this.professeurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProfesseurDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		if (session.getAttribute(ATT_SESSION_ADMINISTRATEUR) != null)
	    {
			response.sendRedirect("http://localhost:8080/ZPareo/ai/administrateur"); 
	    } 
		else if (session.getAttribute(ATT_SESSION_PROFESSEUR) != null)
	    {
			response.sendRedirect("http://localhost:8080/ZPareo/pi/examen");  
	    } 
		else if (session.getAttribute(ATT_SESSION_ETUDIANT) != null)
        {
			response.sendRedirect("http://localhost:8080/ZPareo/ei");
        }
		else
		{
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		EtudiantForm etudiantForm = new EtudiantForm(this.etudiantDao);
		ProfesseurForm professeurForm = new ProfesseurForm(this.professeurDao);
		AdministrateurForm administrateurForm = new AdministrateurForm(this.administrateurDao);
		beans.Etudiant etudiant = etudiantForm.verifIdentifiantEtudiant(request);
		beans.Professeur professeur = professeurForm.verifIdentifiantProfesseur(request);
		beans.Administrateur administrateur = administrateurForm.verifIdentifiantAdmin(request);
		
		if (etudiant.getId() != null)
		{
			session.setAttribute(ATT_SESSION_ETUDIANT, etudiant);
			response.sendRedirect("http://localhost:8080/ZPareo/ei");   
		}
		else if (professeur.getId() != null)
		{
			session.setAttribute(ATT_SESSION_PROFESSEUR, professeur);
			response.sendRedirect("http://localhost:8080/ZPareo/pi/examen");   
		}
		else if (administrateur.getId() != null)
		{
			session.setAttribute(ATT_SESSION_ADMINISTRATEUR, administrateur);
			response.sendRedirect("http://localhost:8080/ZPareo/ai/administrateur"); 
		}
		else
		{
			request.setAttribute(ATT_UTILISATEUR, etudiant);
        	request.setAttribute(ATT_FORM, etudiantForm);
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}
	}
	
}
