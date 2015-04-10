package servlets.ai.matter;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import forms.MatterForm;
import dao.DAOFactory;
import dao.MatterDao;

@SuppressWarnings("serial")
@WebServlet("/ai/matiere")
public class Matter extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String MATTERS          = "matters";
	private static final String NUMB_MATTERS     = "numbMatters";
	private static final String MATTER_FORM      = "matterForm";
	private static final String VIEW             = "/WEB-INF/ai/matter/index.jsp";
	private MatterDao matterDao;
	
    public Matter() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.matterDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatterDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        MatterForm matterForm     = new MatterForm(this.matterDao);
        Set<beans.Matter> matters = matterForm.search(request);
		
        request.setAttribute(MATTER_FORM, matterForm);
        request.setAttribute(MATTERS, matters);
        request.setAttribute(NUMB_MATTERS, matters.size());
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
	
}
