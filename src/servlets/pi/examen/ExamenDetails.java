package servlets.pi.examen;

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
@WebServlet("/pi/examen/details")
public class ExamenDetails extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ATT_EXAMEN       = "examen";
    private static final String VUE_DETAILS      = "/WEB-INF/pi/examen/details.jsp";
	private ExamenDao examenDao; 

    public ExamenDetails()
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
		beans.Examen examen = form.trouverExamen(request);
		
		request.setAttribute(ATT_EXAMEN, examen);
        this.getServletContext().getRequestDispatcher(VUE_DETAILS).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
	
}
