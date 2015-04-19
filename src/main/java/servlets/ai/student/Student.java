package servlets.ai.student;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Group;
import dao.DAOFactory;
import dao.StudentDao;
import dao.GroupDao;
import forms.StudentForm;

@WebServlet("/ai/student")
public class Student extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String STUDENTS         = "students";
    private static final String GROUPS           = "groups";
    private static final String NUMB_STUDENTS    = "numbStudents";
    private static final String STUDENT_FORM     = "studentForm";
    private static final String VIEW             = "/WEB-INF/ai/student/index.xhtml";
    private StudentDao studentDao;
    private GroupDao   groupDao;

    public Student() {
        super();
    }

    public void init() throws ServletException {
        this.studentDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getStudentDao();
        this.groupDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentForm        studentForm = new StudentForm(this.studentDao);
        Set<beans.Student> students    = studentForm.search(request);
        Group              groupe      = new Group();
        Set<beans.Group>   groups      = this.groupDao.search(groupe);

        request.setAttribute(STUDENT_FORM, studentForm);
        request.setAttribute(STUDENTS, students);
        request.setAttribute(GROUPS, groups);
        request.setAttribute(NUMB_STUDENTS, students.size());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
