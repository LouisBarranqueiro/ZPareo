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

@WebServlet("/ai/student/reset-password")
public class StudentResetPassword extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String STUDENT          = "student";
    private static final String VIEW             = "/WEB-INF/ai/student/resetpassword.xhtml";
    private StudentDao studentDao;

    public StudentResetPassword() {
        super();
    }

    public void init() throws ServletException {
        this.studentDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getStudentDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentForm studentForm = new StudentForm(this.studentDao);
        Student     student     = studentForm.get(request);

        request.setAttribute(STUDENT, student);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentForm studentForm = new StudentForm(this.studentDao);
        studentForm.resetPassword(request);

    }

}
