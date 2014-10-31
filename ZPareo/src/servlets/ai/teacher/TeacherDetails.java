package servlets.ai.teacher;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Group;
import beans.Matter;
import dao.DAOFactory;
import dao.GroupDao;
import dao.MatterDao;
import dao.TeacherDao;
import forms.TeacherForm;

@SuppressWarnings("serial")
@WebServlet("/ai/professeur/details")
public class TeacherDetails extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String MATTERS          = "matters";
	private static final String GROUPS           = "groups";
	private static final String TEACHER          = "teacher";
	private static final String VIEW             = "/WEB-INF/ai/teacher/details.jsp";
	private TeacherDao teacherDao;
	private GroupDao groupDao;
	private MatterDao matterDao;
	
	public void init() throws ServletException 
    {
		this.matterDao  = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatterDao();
        this.groupDao   = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
        this.teacherDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTeacherDao();
    }

    public TeacherDetails() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Matter matter           = new Matter();
		Group group             = new Group();
		Set<Group> groups       = this.groupDao.search(group);
		Set<Matter> matters     = this.matterDao.search(matter);
		TeacherForm teacherForm = new TeacherForm(this.teacherDao);
		beans.Teacher teacher   = teacherForm.get(request);
		
		request.setAttribute(TEACHER, teacher);
        request.setAttribute(MATTERS, matters);
        request.setAttribute(GROUPS, groups);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
}