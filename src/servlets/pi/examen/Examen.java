package servlets.pi.examen;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DAOFactory;
import dao.ExamenDao;
import forms.ExamenForm;

@SuppressWarnings("serial")
@WebServlet("/pi/examen")
public class Examen extends HttpServlet 
{	
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ATT_EXAMENS      = "listeExamens";
	private static final String ATT_NB_EXAMENS   = "nbExamens";
	private static final String ATT_FORM         = "form";
    private static final String VUE              = "/WEB-INF/pi/examen/index.jsp";
	private ExamenDao examenDao;
	
	public Examen() 
    {
        super();
    }
	
	public void init() throws ServletException 
    {
        this.examenDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getExamenDao();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ExamenForm form = new ExamenForm(examenDao);
		Set<beans.Examen> listeExamens = form.rechercherExamen(request);
		long nbExamens = listeExamens.size();
		
        request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_EXAMENS, listeExamens);
        request.setAttribute(ATT_NB_EXAMENS, nbExamens);
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

}