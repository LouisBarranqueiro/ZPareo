package servlets.ai.groupe;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DAOFactory;
import dao.GroupeDao;
import forms.GroupeForm;

@SuppressWarnings("serial")
@WebServlet("/ai/groupe")
public class Groupe extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ATT_GROUPES      = "listeGroupes";
	private static final String ATT_NB_GROUPES   = "nbGroupes";
	private static final String ATT_FORM         = "form";
	private static final String VUE              = "/WEB-INF/ai/groupe/index.jsp";
	private GroupeDao groupeDao;
	
    public Groupe() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupeDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		GroupeForm form = new GroupeForm(this.groupeDao);
        Set<beans.Groupe> listeGroupes = form.rechercherGroupe(request);
		int nbGroupes = listeGroupes.size();
		
        request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_GROUPES, listeGroupes);
        request.setAttribute(ATT_NB_GROUPES, nbGroupes);
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{  
	}
	
}
