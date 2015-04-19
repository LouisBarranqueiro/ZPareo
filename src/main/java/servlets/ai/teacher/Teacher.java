package servlets.ai.teacher;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.TeacherDao;
import forms.TeacherForm;

@WebServlet("/ai/teacher")
public class Teacher extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String TEACHERS         = "teachers";
    private static final String NUMB_TEACHERS    = "numbTeachers";
    private static final String TEACHER_FORM     = "teacherForm";
    private static final String VIEW             = "/WEB-INF/ai/teacher/index.xhtml";
    private TeacherDao teacherDao;

    public Teacher() {
        super();
    }

    public void init() throws ServletException {
        this.teacherDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTeacherDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TeacherForm        teacherForm = new TeacherForm(this.teacherDao);
        Set<beans.Teacher> teachers    = teacherForm.search(request);

        request.setAttribute(TEACHER_FORM, teacherForm);
        request.setAttribute(TEACHERS, teachers);
        request.setAttribute(NUMB_TEACHERS, teachers.size());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
