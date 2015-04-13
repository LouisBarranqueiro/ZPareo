package servlets.ai.matter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DAOFactory;
import dao.MatterDao;
import forms.MatterForm;
import beans.Subject;

@SuppressWarnings("serial")
@WebServlet("/ai/matiere/edition")
public class MatterEdition extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String MATTER           = "matter";
	private static final String MATTER_FORM      = "matterForm";
	private static final String VIEW             = "/WEB-INF/ai/matter/edition.jsp";
	private MatterDao matterDao;
	
	public void init() throws ServletException 
    {
        this.matterDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatterDao();
    }

    public MatterEdition() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		MatterForm matterForm = new MatterForm(this.matterDao);
        Subject subject = matterForm.get(request);
        
        request.setAttribute(MATTER , subject);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		MatterForm matterForm = new MatterForm(this.matterDao);
		Subject subject = matterForm.edit(request);
		
		if (matterForm.getErrors().isEmpty()) response.sendRedirect("http://localhost:8080/ZPareo/ai/matiere");  
	    else
	    {
	    	request.setAttribute(MATTER_FORM, matterForm);
	        request.setAttribute(MATTER, subject);
	        this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );   
	    }
	}
}
