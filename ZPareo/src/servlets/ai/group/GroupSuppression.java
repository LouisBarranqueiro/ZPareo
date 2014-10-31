package servlets.ai.group;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Group;
import dao.DAOFactory;
import dao.GroupDao;
import forms.GroupForm;

@SuppressWarnings("serial")
@WebServlet("/ai/groupe/suppression")
public class GroupSuppression extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String GROUP            = "group";
	private static final String GROUP_FORM       = "groupForm";
	private static final String VIEW             = "/WEB-INF/ai/group/suppression.jsp";
	private GroupDao groupDao;
	
	public void init() throws ServletException 
    {
        this.groupDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
    }

    public GroupSuppression() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		GroupForm groupeForm = new GroupForm( this.groupDao );
        Group group          = groupeForm.get(request);
        
        request.setAttribute(GROUP_FORM , groupeForm); 
        request.setAttribute(GROUP , group); 
		this.getServletContext().getRequestDispatcher(VIEW).forward( request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		GroupForm groupeForm = new GroupForm(this.groupDao);
		
		groupeForm.delete(request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/groupe");  
	}

}
