package servlets.etudiant;

import java.io.IOException;
import java.util.TreeSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Etudiant;
import models.Groupe;
import dao.DAOFactory;
import dao.EtudiantDao;
import dao.GroupeDao;
import forms.EtudiantForm;


@WebServlet("/ai/etudiant/creation")
public class EtudiantCreation extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_ETUDIANT     = "etudiant";
	public static final String ATT_GROUPES      = "listeGroupes";
    public static final String ATT_FORM         = "form";
	private static final String VUE_CREATION    = "/WEB-INF/etudiant/creation.jsp";
    private EtudiantDao etudiantDao;
    private GroupeDao groupeDao;

    public EtudiantCreation() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.etudiantDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getEtudiantDao();
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupeDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Set<Groupe> listeGroupes = new TreeSet<Groupe>();
		Groupe groupe = new Groupe();
		
		listeGroupes = this.groupeDao.rechercher(groupe);
        request.setAttribute(ATT_GROUPES, listeGroupes);
        this.getServletContext().getRequestDispatcher(VUE_CREATION).forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        EtudiantForm form = new EtudiantForm( this.etudiantDao );
        Set<Groupe> listeGroupes = new TreeSet<Groupe>();
        Groupe groupe = new Groupe();
        
        Etudiant etudiant = form.creerEtudiant(request);
		listeGroupes = this.groupeDao.rechercher(groupe);
		
        if(form.getErreurs().isEmpty())
        {
        	response.sendRedirect("http://localhost:8080/ZPareo/ai/etudiant");   
        }
        else
        {
        	request.setAttribute(ATT_FORM, form);
        	request.setAttribute(ATT_ETUDIANT, etudiant);
        	request.setAttribute(ATT_GROUPES, listeGroupes);
        	this.getServletContext().getRequestDispatcher(VUE_CREATION).forward(request, response);   
        }
	}

}
