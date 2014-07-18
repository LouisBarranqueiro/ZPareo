package servlets.ai.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Student;
import dao.DAOFactory;
import dao.StudentDao;
import forms.StudentForm;

@SuppressWarnings("serial")
@WebServlet("/ai/etudiant/suppression")
public class StudentSuppression extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String STUDENT          = "student";
	private static final String VIEW             = "/WEB-INF/ai/student/suppression.jsp";
	private StudentDao studentDao;
	
	public void init() throws ServletException 
    {
        this.studentDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getStudentDao();
    }

    public StudentSuppression() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		StudentForm studentForm = new StudentForm(this.studentDao);
		Student student         = studentForm.get(request);
		
        request.setAttribute(STUDENT, student);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		StudentForm studentForm = new StudentForm(this.studentDao);
		
		studentForm.delete(request);
		response.sendRedirect("http://localhost:8080/ZPareo/ai/etudiant");  
	}

}
