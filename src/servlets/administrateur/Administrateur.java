package servlets.administrateur;

import java.io.IOException;
import java.util.TreeSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.AdministrateurDao;
import forms.AdministrateurForm;

@SuppressWarnings("serial")
@WebServlet("/ai/administrateur")
public class Administrateur extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY        = "daofactory";
	public static final String ATT_ADMINISTRATEURS     = "listeAdministrateurs";
	public static final String ATT_NB_ADMINISTRATEURS  = "nbAdministrateurs";
    public static final String ATT_FORM                = "form";
	private static final String VUE                    = "/WEB-INF/administrateur/index.jsp";
	private AdministrateurDao administrateurDao;
	
    public Administrateur() 
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
        Set<beans.Administrateur> listeAdministrateurs = new TreeSet<beans.Administrateur>();
        
        listeAdministrateurs = form.rechercherAdministrateur(request);
		int nbAdministrateurs = listeAdministrateurs.size();
        request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_ADMINISTRATEURS, listeAdministrateurs);
        request.setAttribute(ATT_NB_ADMINISTRATEURS, nbAdministrateurs);
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
}
