package servlets.pi.examen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.Professeur;
import dao.DAOFactory;
import dao.ExamenDao;
import forms.ExamenForm;

@SuppressWarnings("serial")
@WebServlet("/pi/examen/suppression")
public class ExamenSuppression extends HttpServlet 
{
	private static final String ATT_SESSION_PROFESSEUR = "sessionProfesseur";
	private static final String CONF_DAO_FACTORY       = "daofactory";
	private static final String ATT_EXAMEN             = "examen";
	private static final String VUE_SUPPRESSION        = "/WEB-INF/pi/examen/suppression.jsp";
	private ExamenDao examenDao;
	
	public void init() throws ServletException 
    {
        this.examenDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getExamenDao();
    }

    public ExamenSuppression() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ExamenForm form = new ExamenForm(this.examenDao);
		beans.Examen examen = form.trouverExamen(request);
		
		request.setAttribute(ATT_EXAMEN, examen);
        this.getServletContext().getRequestDispatcher(VUE_SUPPRESSION).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Professeur editeur = (Professeur) session.getAttribute(ATT_SESSION_PROFESSEUR);
		ExamenForm form = new ExamenForm(this.examenDao);
		
		form.supprimerExamen(editeur, request);
		response.sendRedirect("http://localhost:8080/ZPareo/pi/examen");  
	}

}
