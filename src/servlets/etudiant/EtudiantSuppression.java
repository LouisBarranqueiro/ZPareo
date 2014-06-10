package servlets.etudiant;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.Administrateur;
import beans.Etudiant;
import dao.DAOFactory;
import dao.EtudiantDao;
import forms.EtudiantForm;

@SuppressWarnings("serial")
@WebServlet("/ai/etudiant/suppression")
public class EtudiantSuppression extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY            = "daofactory";
	private static final String ATT_SESSION_ADMINISTRATEUR  = "sessionAdministrateur";
	private static final String ATT_ETUDIANT                = "etudiant";
	private static final String VUE_SUPPRESSION             = "/WEB-INF/etudiant/suppression.jsp";
	private EtudiantDao etudiantDao;
	
	public void init() throws ServletException 
    {
        this.etudiantDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getEtudiantDao();
    }

    public EtudiantSuppression() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		EtudiantForm form = new EtudiantForm(this.etudiantDao);
		
		Etudiant etudiant = form.trouverEtudiant(request);
        request.setAttribute(ATT_ETUDIANT, etudiant);
        this.getServletContext().getRequestDispatcher(VUE_SUPPRESSION).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Administrateur editeur = (Administrateur) session.getAttribute(ATT_SESSION_ADMINISTRATEUR);
		EtudiantForm form = new EtudiantForm(this.etudiantDao);
		
		form.supprimerEtudiant(editeur, request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/etudiant");  
	}

}
