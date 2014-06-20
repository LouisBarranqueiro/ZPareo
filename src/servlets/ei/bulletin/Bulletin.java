package servlets.ei.bulletin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DAOFactory;
import dao.EtudiantDao;
import forms.EtudiantForm;

@SuppressWarnings("serial")
@WebServlet("/ei/mon-bulletin")
public class Bulletin extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ETUDIANT         = "etudiant";
	private static final String VUE              = "/WEB-INF/ei/bulletin.jsp";
	private EtudiantDao etudiantDao;
	
    public Bulletin()
    {
    	super();
    }
    
    public void init() throws ServletException 
    {
        this.etudiantDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getEtudiantDao();
    }
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		EtudiantForm form = new EtudiantForm(etudiantDao);
		beans.Etudiant etudiant = form.recupTout(request);
		
		request.setAttribute(ETUDIANT, etudiant);
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

}
