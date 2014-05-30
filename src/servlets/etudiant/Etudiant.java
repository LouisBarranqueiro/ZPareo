package servlets.etudiant;

import java.io.IOException;
import java.util.TreeSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Groupe;
import dao.DAOFactory;
import dao.EtudiantDao;
import dao.GroupeDao;
import forms.EtudiantForm;

@WebServlet("/ai/etudiant")
public class Etudiant extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY  = "daofactory";
	public static final String ATT_ETUDIANTS     = "listeEtudiants";
	public static final String ATT_GROUPES       = "listeGroupes";
	public static final String ATT_NB_ETUDIANTS  = "nbEtudiants";
    public static final String ATT_FORM          = "form";
	private static final String VUE              = "/WEB-INF/etudiant/index.jsp";
	private EtudiantDao etudiantDao;
	private GroupeDao groupeDao;
	
    public Etudiant() 
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
        EtudiantForm form = new EtudiantForm(this.etudiantDao);
        Set<beans.Etudiant> listeEtudiants = new TreeSet<beans.Etudiant>();
        Groupe groupe = new Groupe();
        Set<beans.Groupe> listeGroupes = new TreeSet<beans.Groupe>();
        
        listeEtudiants = form.rechercherEtudiant(request);
        listeGroupes = this.groupeDao.rechercher(groupe);
		int nbEtudiants = listeEtudiants.size();
        request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_ETUDIANTS, listeEtudiants);
        request.setAttribute(ATT_GROUPES, listeGroupes);
        request.setAttribute(ATT_NB_ETUDIANTS, nbEtudiants);
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
}
