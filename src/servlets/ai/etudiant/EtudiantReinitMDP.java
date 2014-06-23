package servlets.ai.etudiant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Etudiant;
import dao.DAOFactory;
import dao.EtudiantDao;
import forms.EtudiantForm;

/**
 * Servlet implementation class EtudiantReinitMDP
 */
@SuppressWarnings("serial")
@WebServlet("/ai/etudiant/reinit-mot-de-passe")
public class EtudiantReinitMDP extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ATT_ETUDIANT     = "etudiant";
	private static final String VUE_REINIT_MDP    = "/WEB-INF/ai/etudiant/reinitmdp.jsp";
	private EtudiantDao etudiantDao;
	
    public EtudiantReinitMDP() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
		this.etudiantDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getEtudiantDao();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		EtudiantForm form = new EtudiantForm(this.etudiantDao);
		
		Etudiant etudiant = form.trouverEtudiant(request);
        request.setAttribute(ATT_ETUDIANT, etudiant);
        this.getServletContext().getRequestDispatcher(VUE_REINIT_MDP).forward(request, response);  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		EtudiantForm form = new EtudiantForm(this.etudiantDao);
		form.reinitMDPEtudiant(request);
		
	}

}
