package servlets.matiere;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forms.MatiereForm;
import dao.DAOFactory;
import dao.MatiereDao;
import dao.MatiereDaoImpl;

@WebServlet("/ai/matiere")
public class Matiere extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_MATIERES     = "listeMatieres";
	public static final String ATT_NB_MATIERES  = "nbMatieres";
    public static final String ATT_FORM         = "form";
	private static final String VUE             = "/WEB-INF/matiere/index.jsp";
	private MatiereDao matiereDao;
	
    public Matiere() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.matiereDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatiereDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        MatiereForm form = new MatiereForm(this.matiereDao);
        Set<models.Matiere> listeMatieres = new TreeSet<models.Matiere>();
        
        listeMatieres = form.rechercherMatiere(request);
        System.out.println( listeMatieres.toString() );
		int nbMatieres = listeMatieres.size();
        request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_MATIERES, listeMatieres);
        request.setAttribute(ATT_NB_MATIERES, nbMatieres);
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        
	}
}
