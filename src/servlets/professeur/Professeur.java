package servlets.professeur;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DAOFactory;
import dao.ProfesseurDao;
import forms.ProfesseurForm;

@SuppressWarnings("serial")
@WebServlet("/ai/professeur")
public class Professeur extends HttpServlet 
{   
	private static final String CONF_DAO_FACTORY   = "daofactory";
	private static final String ATT_PROFESSEURS    = "listeProfesseurs";
	private static final String ATT_NB_PROFESSEURS = "nbProfesseurs";
	private static final String ATT_FORM           = "form";
	private static final String VUE                = "/WEB-INF/professeur/index.jsp";
	private ProfesseurDao professeurDao;
	
    public Professeur() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
    	this.professeurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProfesseurDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        ProfesseurForm form = new ProfesseurForm(this.professeurDao);
        Set<beans.Professeur> listeProfesseurs = form.rechercherProfesseur( request );   
		int nbProfesseurs = listeProfesseurs.size();
		
        request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_PROFESSEURS, listeProfesseurs);
        request.setAttribute(ATT_NB_PROFESSEURS, nbProfesseurs);
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        
	}

}
