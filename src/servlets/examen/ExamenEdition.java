package servlets.examen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DAOFactory;
import dao.ExamenDao;
import forms.ExamenForm;

@SuppressWarnings("serial")
@WebServlet("/pi/examen/edition")
public class ExamenEdition extends HttpServlet 
{  
	public static final String CONF_DAO_FACTORY         = "daofactory";
	public static final String ATT_SESSION_PROFESSEUR   = "sessionProfesseur";
	public static final String ATT_EXAMEN               = "examen";
	public static final String ATT_NB_NOTES             = "nbNotes";
    public static final String ATT_FORM                 = "form";
	public static final String VUE_EDITION              = "/WEB-INF/examen/edition.jsp";
	private ExamenDao examenDao; 

    public ExamenEdition()
    {
        super();
    }

    public void init() throws ServletException 
    {
        this.examenDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getExamenDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ExamenForm form = new ExamenForm(this.examenDao);
		beans.Examen examen = null;
		
		examen = form.trouverExamen(request);
		int nbNotes = examen.getListeNotes().size();
		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_EXAMEN, examen);
		request.setAttribute(ATT_NB_NOTES, nbNotes);
        this.getServletContext().getRequestDispatcher(VUE_EDITION).forward(request, response);   
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

	}

}
