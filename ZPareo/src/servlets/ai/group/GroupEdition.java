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
@WebServlet("/ai/groupe/edition")
public class GroupEdition extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String GROUP            = "group";
	private static final String GROUP_FORM       = "groupForm";
	private static final String VIEW             = "/WEB-INF/ai/group/edition.jsp";
	private GroupDao groupeDao;
	
	public void init() throws ServletException 
    {
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
    }

    public GroupEdition() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		GroupForm groupForm = new GroupForm( this.groupeDao );
        Group group         = groupForm.get(request);
        
        request.setAttribute(GROUP , group);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		GroupForm groupForm = new GroupForm(this.groupeDao);
		Group group         = groupForm.edit(request);
		
		if (groupForm.getErrors().isEmpty()) response.sendRedirect("http://localhost:8080/ZPareo/ai/groupe");   
	    else
	    {
	    	request.setAttribute(GROUP_FORM, groupForm);
	        request.setAttribute(GROUP, group);
	        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
	    }
	}

}
