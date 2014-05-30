package servlets.professeur;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Etudiant;
import beans.Groupe;
import beans.Matiere;
import dao.DAOFactory;
import dao.EtudiantDao;
import dao.GroupeDao;
import dao.MatiereDao;
import dao.ProfesseurDao;
import forms.EtudiantForm;
import forms.ProfesseurForm;

@WebServlet("/ai/professeur/creation")
public class ProfesseurCreation extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY  = "daofactory";
	public static final String ATT_MATIERES      = "listeMatieres";
	public static final String ATT_GROUPES       = "listeGroupes";
	public static final String ATT_PROFESSEUR    = "professeur";
    public static final String ATT_FORM          = "form";
	private static final String VUE_CREATION     = "/WEB-INF/professeur/creation.jsp";
    private MatiereDao matiereDao;
    private GroupeDao groupeDao;
    private ProfesseurDao professeurDao;

    public ProfesseurCreation() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.matiereDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatiereDao();
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupeDao();
        this.professeurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProfesseurDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Set<Groupe> listeGroupes = new TreeSet<Groupe>();
		Groupe groupe = new Groupe();
		Set<Matiere> listeMatieres = new TreeSet<Matiere>();
		Matiere matiere = new Matiere();
		
		listeGroupes = this.groupeDao.rechercher(groupe);
		listeMatieres = this.matiereDao.rechercher(matiere);
        request.setAttribute(ATT_GROUPES, listeGroupes);
        request.setAttribute(ATT_MATIERES, listeMatieres);
        this.getServletContext().getRequestDispatcher(VUE_CREATION).forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ProfesseurForm form = new ProfesseurForm(this.professeurDao);
        beans.Professeur professeur = form.creerProfesseur(request);
		
        if(form.getErreurs().isEmpty())
        {
    	   	response.sendRedirect("http://localhost:8080/ZPareo/ai/professeur");   
        }
        else
        {
    	   	Set<Groupe> listeGroupes = new TreeSet<Groupe>();
   			Groupe groupe = new Groupe();
   			Set<Matiere> listeMatieres = new TreeSet<Matiere>();
   			Matiere matiere = new Matiere();
   			
   			listeGroupes = this.groupeDao.rechercher( groupe );
   			listeMatieres = this.matiereDao.rechercher( matiere );
   			request.setAttribute( ATT_FORM, form );
   			request.setAttribute( ATT_PROFESSEUR, professeur );
   			request.setAttribute( ATT_GROUPES, listeGroupes );
   			request.setAttribute( ATT_MATIERES, listeMatieres );
   			this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward( request, response );   
        }
	}
	
}
