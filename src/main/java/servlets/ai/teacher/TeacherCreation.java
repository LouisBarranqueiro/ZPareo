package servlets.ai.teacher;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Group;
import beans.Subject;
import dao.DAOFactory;
import dao.GroupDao;
import dao.SubjectDao;
import dao.TeacherDao;
import forms.TeacherForm;

@SuppressWarnings("serial")
@WebServlet("/ai/professeur/creation")
public class TeacherCreation extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String MATTERS          = "matters";
	private static final String GROUPS           = "groups";
	private static final String TEACHER          = "teacher";
	private static final String TEACHER_FORM     = "teacherForm";
	private static final String VIEW             = "/WEB-INF/ai/teacher/creation.jsp";
	private SubjectDao subjectDao;
	private GroupDao   groupDao;
	private TeacherDao teacherDao;

	public TeacherCreation() {
		super();
	}

	public void init() throws ServletException {
		this.subjectDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatterDao();
		this.groupDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
		this.teacherDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTeacherDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Subject      subject  = new Subject();
		Group group         = new Group();
		Set<Group> groups   = this.groupDao.search(group);
		Set<Subject> subjects = this.subjectDao.search(subject);
		
        request.setAttribute(GROUPS, groups);
        request.setAttribute(MATTERS, subjects);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        TeacherForm teacherForm = new TeacherForm(this.teacherDao);
        beans.Teacher teacher   = teacherForm.create(request);
		
        if (teacherForm.getErrors().isEmpty()) response.sendRedirect("http://localhost:8080/ZPareo/ai/professeur");   
        else
        {
        	Subject subject = new Subject();
        	Group group         = new Group();
    	   	Set<Group> groups   = this.groupDao.search(group);
   			Set<Subject> subjects = this.subjectDao.search(subject);
   			
   			request.setAttribute(TEACHER_FORM, teacherForm);
   			request.setAttribute(TEACHER, teacher);
   			request.setAttribute(GROUPS, groups);
   			request.setAttribute(MATTERS, subjects);
   			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
        }
	}
	
}
