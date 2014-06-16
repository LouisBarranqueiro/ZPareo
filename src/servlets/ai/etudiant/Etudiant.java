package servlets.ai.etudiant;

import java.io.IOException;
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

@SuppressWarnings("serial")
@WebServlet("/ai/etudiant")
public class Etudiant extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY  = "daofactory";
	private static final String ATT_ETUDIANTS     = "listeEtudiants";
	private static final String ATT_GROUPES       = "listeGroupes";
	private static final String ATT_NB_ETUDIANTS  = "nbEtudiants";
	private static final String ATT_FORM          = "form";
	private static final String VUE               = "/WEB-INF/ai/etudiant/index.jsp";
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
        Set<beans.Etudiant> listeEtudiants = form.rechercherEtudiant(request);
        Groupe groupe = new Groupe();
        Set<beans.Groupe> listeGroupes = this.groupeDao.rechercher(groupe);
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
