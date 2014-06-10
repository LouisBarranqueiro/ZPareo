package servlets.groupe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Administrateur;
import beans.Groupe;
import dao.DAOFactory;
import dao.GroupeDao;
import forms.GroupeForm;


@SuppressWarnings("serial")
@WebServlet("/ai/groupe/suppression")
public class GroupeSuppression extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY           = "daofactory";
	private static final String ATT_SESSION_ADMINISTRATEUR = "sessionAdministrateur";
	private static final String ATT_GROUPE                 = "groupe";
	private static final String VUE_SUPPRESSION            = "/WEB-INF/groupe/suppression.jsp";
	private GroupeDao groupeDao;
	
	public void init() throws ServletException 
    {
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupeDao();
    }

    public GroupeSuppression() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		GroupeForm form = new GroupeForm( this.groupeDao );
		
        Groupe groupe = form.trouverGroupe(request);
        request.setAttribute(ATT_GROUPE , groupe); 
		this.getServletContext().getRequestDispatcher(VUE_SUPPRESSION).forward( request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Administrateur editeur = (Administrateur) session.getAttribute(ATT_SESSION_ADMINISTRATEUR);
		GroupeForm form = new GroupeForm(this.groupeDao);
		
		form.supprimerGroupe(editeur, request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/groupe");  
	}

}
