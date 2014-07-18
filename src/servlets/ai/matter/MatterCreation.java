package servlets.ai.matter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import forms.MatterForm;
import beans.Matter;
import dao.DAOFactory;
import dao.MatterDao;

@SuppressWarnings("serial")
@WebServlet("/ai/matiere/creation")
public class MatterCreation extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String MATTER           = "matter";
	private static final String MATTER_FORM      = "matterForm";
	private static final String VIEW             = "/WEB-INF/ai/matter/creation.jsp";
    private MatterDao matterDao;

    public MatterCreation() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.matterDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatterDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        MatterForm matterForm = new MatterForm(this.matterDao);
        Matter matter         = matterForm.create(request);
		
       if (matterForm.getErrors().isEmpty()) response.sendRedirect("http://localhost:8080/ZPareo/ai/matiere");   
       else
       {
    	   request.setAttribute(MATTER_FORM, matterForm);
           request.setAttribute(MATTER, matter);
    	   this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
       }
	}
}
