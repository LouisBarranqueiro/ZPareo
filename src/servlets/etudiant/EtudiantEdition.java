package servlets.etudiant;

import java.io.IOException;
import java.util.TreeSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Administrateur;
import beans.Etudiant;
import beans.Groupe;
import dao.DAOFactory;
import dao.EtudiantDao;
import dao.GroupeDao;
import forms.EtudiantForm;

@SuppressWarnings("serial")
@WebServlet("/ai/etudiant/edition")
public class EtudiantEdition extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY            = "daofactory";
	private static final String ATT_ETUDIANT                = "etudiant";
	private static final String ATT_GROUPES                 = "listeGroupes";
    private static final String ATT_FORM                    = "form";
    private static final String ATT_SESSION_ADMINISTRATEUR  = "sessionAdministrateur";
	private static final String VUE_EDITION                 = "/WEB-INF/etudiant/edition.jsp";
	private EtudiantDao etudiantDao;
	private GroupeDao groupeDao;
	
	public void init() throws ServletException 
    {
		this.etudiantDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getEtudiantDao();
		this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupeDao();
    }

    public EtudiantEdition() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		EtudiantForm form = new EtudiantForm(this.etudiantDao);
		Set<Groupe> listeGroupes = new TreeSet<Groupe>();
		Groupe groupe = new Groupe();
		
		Etudiant etudiant = form.trouverEtudiant(request);
		listeGroupes = this.groupeDao.rechercher(groupe);
		request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_ETUDIANT, etudiant);
        request.setAttribute(ATT_GROUPES, listeGroupes);
        this.getServletContext().getRequestDispatcher(VUE_EDITION).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Administrateur sessionAdministrateur = (Administrateur) session.getAttribute(ATT_SESSION_ADMINISTRATEUR);
		EtudiantForm form = new EtudiantForm(this.etudiantDao);
		Set<Groupe> listeGroupes = new TreeSet<Groupe>();
		Groupe groupe = new Groupe();
		
		Etudiant etudiant = form.editerEtudiant(sessionAdministrateur, request);
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
	        this.getServletContext().getRequestDispatcher(VUE_EDITION).forward(request, response);   
	    }
	}

}
