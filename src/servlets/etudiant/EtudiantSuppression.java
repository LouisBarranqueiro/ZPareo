package servlets.etudiant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.EtudiantDao;
import forms.EtudiantForm;

@WebServlet("/ai/etudiant/suppression")
public class EtudiantSuppression extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY = "daofactory";
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
		
		form.supprimerEtudiant(request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/etudiant");  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}


}
