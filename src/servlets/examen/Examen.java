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
@WebServlet("/pi/examen")
public class Examen extends HttpServlet 
{	
	public static final String CONF_DAO_FACTORY         = "daofactory";
	public static final String ATT_SESSION_PROFESSEUR   = "sessionProfesseur";
	public static final String ATT_EXAMENS              = "listeExamens";
	public static final String ATT_NB_EXAMENS           = "nbExamens";
    public static final String ATT_FORM                 = "form";
	public static final String VUE                      = "/WEB-INF/examen/index.jsp";
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
	
		HttpSession session = request.getSession();
		//ExamenForm form = new ExamenForm();
		TreeSet<Examen> listeExamens = new TreeSet<Examen>();
		Professeur professeur = (Professeur) session.getAttribute(ATT_SESSION_PROFESSEUR);
		long nbExamens;
		
		//listeExamens = form.rechercherExamen(professeur, request);
		nbExamens = listeExamens.size();
		
        //request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_EXAMENS, listeExamens);
        request.setAttribute(ATT_NB_EXAMENS, nbExamens);
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
	}

}