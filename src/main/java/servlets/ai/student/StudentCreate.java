package servlets.ai.student;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Student;
import beans.Group;
import dao.DAOFactory;
import dao.StudentDao;
import dao.GroupDao;
import forms.StudentForm;

@WebServlet("/ai/student/create")
public class StudentCreate extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String STUDENTS         = "student";
    private static final String GROUPS           = "groups";
    private static final String STUDENT_FORM     = "studentForm";
    private static final String VIEW             = "/WEB-INF/ai/student/create.xhtml";
    private String     contextPath;
    private StudentDao studentDao;
    private GroupDao   groupDao;

    public StudentCreate() {
        super();
    }

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.studentDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getStudentDao();
        this.groupDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Group      group  = new Group();
        Set<Group> groups = this.groupDao.search(group);

        request.setAttribute(GROUPS, groups);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Group       group       = new Group();
        StudentForm studentForm = new StudentForm(this.studentDao);
        Set<Group>  groups      = this.groupDao.search(group);
        Student     student     = studentForm.create(request);

        if (studentForm.getErrors().isEmpty()) {
            response.sendRedirect(this.contextPath + "/ai/student");
        }
        else {
            request.setAttribute(STUDENT_FORM, studentForm);
            request.setAttribute(STUDENTS, student);
            request.setAttribute(GROUPS, groups);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }

}
