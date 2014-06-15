package servlets.matiere;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.Administrateur;
import beans.Matiere;
import dao.DAOFactory;
import dao.MatiereDao;
import forms.MatiereForm;

@SuppressWarnings("serial")
@WebServlet("/ai/matiere/suppression")
public class MatiereSuppression extends HttpServlet
{
	private static final String CONF_DAO_FACTORY           = "daofactory";
	private static final String ATT_SESSION_ADMINISTRATEUR = "sessionAdministrateur";
	private static final String ATT_MATIERE                = "matiere";
	private static final String VUE_SUPPRESSION            = "/WEB-INF/matiere/suppression.jsp";
	private MatiereDao matiereDao;
	
	public void init() throws ServletException 
    {
        this.matiereDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatiereDao();
    }

    public MatiereSuppression() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		MatiereForm form = new MatiereForm(this.matiereDao);
        Matiere matiere = form.trouverMatiere(request);
        
        request.setAttribute(ATT_MATIERE , matiere);
        this.getServletContext().getRequestDispatcher(VUE_SUPPRESSION).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Administrateur editeur = (Administrateur) session.getAttribute(ATT_SESSION_ADMINISTRATEUR);
		MatiereForm form = new MatiereForm(this.matiereDao);
		
		form.supprimerMatiere(editeur, request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/matiere");  
	}

}
