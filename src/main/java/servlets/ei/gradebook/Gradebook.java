package servlets.ei.gradebook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.StudentDao;
import forms.StudentForm;

@WebServlet("/si/gradebook")
public class Gradebook extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String STUDENT          = "student";
    private static final String VIEW             = "/WEB-INF/si/gradebook.xhtml";
    private StudentDao studentDao;

    public Gradebook() {
        super();
    }

    public void init() throws ServletException {
        this.studentDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getStudentDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentForm   studentForm = new StudentForm(studentDao);
        beans.Student student     = studentForm.getAllInfos(request);

        request.setAttribute(STUDENT, student);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
