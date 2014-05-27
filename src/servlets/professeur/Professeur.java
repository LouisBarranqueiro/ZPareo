package servlets.professeur;

import java.io.IOException;
import java.util.TreeSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Groupe;
import models.Matiere;
import dao.GroupeDao;
import dao.DAOFactory;
import dao.MatiereDao;
import dao.ProfesseurDao;
import forms.ProfesseurForm;

@WebServlet("/ai/professeur")
public class Professeur extends HttpServlet 
{   
	public static final String CONF_DAO_FACTORY    = "daofactory";
	public static final String ATT_PROFESSEURS     = "listeProfesseurs";
	public static final String ATT_GROUPES         = "listeGroupes";
	public static final String ATT_MATIERES        = "listeMatieres";
	public static final String ATT_NB_PROFESSEURS  = "nbProfesseurs";
    public static final String ATT_FORM            = "form";
	private static final String VUE                = "/WEB-INF/professeur/index.jsp";
	private GroupeDao groupeDao;
	private MatiereDao matiereDao;
	private ProfesseurDao professeurDao;
	
    public Professeur() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
    	this.professeurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProfesseurDao();
    	this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupeDao();
    	this.matiereDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatiereDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        ProfesseurForm form = new ProfesseurForm(this.professeurDao);
        Set<models.Professeur> listeProfesseurs = new TreeSet<models.Professeur>();
        
        listeProfesseurs = form.rechercherProfesseur( request );   
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
