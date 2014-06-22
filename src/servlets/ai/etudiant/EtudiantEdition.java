package servlets.ai.etudiant;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	private static final String VUE_EDITION                 = "/WEB-INF/ai/etudiant/edition.jsp";
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
		Groupe groupe = new Groupe();
		EtudiantForm form = new EtudiantForm(this.etudiantDao);
		Set<Groupe> listeGroupes = this.groupeDao.rechercher(groupe);
		Etudiant etudiant = form.trouverEtudiant(request);
		
		request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_ETUDIANT, etudiant);
        request.setAttribute(ATT_GROUPES, listeGroupes);
        this.getServletContext().getRequestDispatcher(VUE_EDITION).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Groupe groupe = new Groupe();
		EtudiantForm form = new EtudiantForm(this.etudiantDao);
		Set<Groupe> listeGroupes = this.groupeDao.rechercher(groupe);
		Etudiant etudiant = form.editerEtudiant(request);
		
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
