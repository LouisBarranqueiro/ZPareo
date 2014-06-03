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

import beans.Etudiant;
import beans.Groupe;
import beans.Professeur;
import dao.DAOFactory;
import dao.ExamenDao;
import forms.EtudiantForm;
import forms.ExamenForm;

@SuppressWarnings("serial")
@WebServlet("/pi/examen/creation")
public class ExamenCreation extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY         = "daofactory";
	public static final String ATT_SESSION_PROFESSEUR   = "sessionProfesseur";
	public static final String ATT_EXAMEN               = "examen";
	public static final String ATT_EXAMENS              = "listeExamens";
	public static final String ATT_NB_EXAMENS           = "nbExamens";
    public static final String ATT_FORM                 = "form";
	public static final String VUE_CREATION             = "/WEB-INF/examen/creation.jsp";
	private ExamenDao examenDao;
	
    public ExamenCreation() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.examenDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getExamenDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.getServletContext().getRequestDispatcher(VUE_CREATION).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ExamenForm form = new ExamenForm(this.examenDao);
        beans.Examen examen = form.creerExamen(request);
		
        if(form.getErreurs().isEmpty())
        {
        	response.sendRedirect("http://localhost:8080/ZPareo/pi/examen");   
        }
        else
        {
        	request.setAttribute(ATT_FORM, form);
        	request.setAttribute(ATT_EXAMEN, examen);
        	this.getServletContext().getRequestDispatcher(VUE_CREATION).forward(request, response);   
        }
	}

}
