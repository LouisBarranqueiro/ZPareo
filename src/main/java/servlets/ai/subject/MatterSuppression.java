package servlets.ai.matter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Subject;
import dao.DAOFactory;
import dao.MatterDao;
import forms.MatterForm;

@SuppressWarnings("serial")
@WebServlet("/ai/matiere/suppression")
public class MatterSuppression extends HttpServlet
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String MATTER           = "matter";
	private static final String VIEW             = "/WEB-INF/ai/matter/suppression.jsp";
	private MatterDao matterDao;
	
	public void init() throws ServletException 
    {
        this.matterDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatterDao();
    }

    public MatterSuppression() 
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
		
		matterForm.delete(request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/matiere");  
	}

}
