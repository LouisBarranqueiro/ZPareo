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

@WebServlet("/ai/student/update")
public class StudentUpdate extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String STUDENT          = "student";
    private static final String GROUPS           = "groups";
    private static final String STUDENT_FORM     = "studentForm";
    private static final String VIEW             = "/WEB-INF/ai/student/update.xhtml";
    private String     contextPath;
    private StudentDao studentDao;
    private GroupDao   groupeDao;

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.studentDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getStudentDao();
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
    }

    public StudentUpdate() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Group       group       = new Group();
        StudentForm studentForm = new StudentForm(this.studentDao);
        Set<Group>  groups      = this.groupeDao.search(group);
        Student     student     = studentForm.get(request);

        request.setAttribute(STUDENT_FORM, studentForm);
        request.setAttribute(STUDENT, student);
        request.setAttribute(GROUPS, groups);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Group       group       = new Group();
        StudentForm studentForm = new StudentForm(this.studentDao);
        Set<Group>  groups      = this.groupeDao.search(group);
        Student     student     = studentForm.edit(request);

        if (studentForm.getErrors().isEmpty()) {
            response.sendRedirect(this.contextPath + "/ai/student");
        }
        else {
            request.setAttribute(STUDENT_FORM, studentForm);
            request.setAttribute(STUDENT, student);
            request.setAttribute(GROUPS, groups);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }

}
