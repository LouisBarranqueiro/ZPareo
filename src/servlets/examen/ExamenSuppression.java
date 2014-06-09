package servlets.examen;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

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
	public static final String ATT_SESSION_PROFESSEUR   = "sessionProfesseur";
	public static final String CONF_DAO_FACTORY = "daofactory";
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
		HttpSession session = request.getSession();
		Professeur professeur = (Professeur) session.getAttribute(ATT_SESSION_PROFESSEUR);
		ExamenForm form = new ExamenForm(this.examenDao);
		
		form.supprimerExamen(professeur, request);
		response.sendRedirect("http://localhost:8080/ZPareo/pi/examen");  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

}
