package servlets.administrateur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.AdministrateurDao;

import forms.AdministrateurForm;

@SuppressWarnings("serial")
@WebServlet("/ai/administrateur/suppression")
public class AdministrateurSuppression extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY   = "daofactory";
	public static final String ATT_ADMINISTRATEUR = "administrateur";
    public static final String ATT_FORM           = "form";
    private AdministrateurDao administrateurDao;

    public AdministrateurSuppression() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.administrateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getAdministrateurDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		AdministrateurForm form = new AdministrateurForm(this.administrateurDao);
		
		form.supprimerAdministrateur(request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/administrateur");  	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

}
