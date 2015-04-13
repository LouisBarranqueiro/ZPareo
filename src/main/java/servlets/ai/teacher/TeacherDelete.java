package servlets.ai.teacher;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.TeacherDao;
import forms.TeacherForm;

@WebServlet("/ai/teacher/delete")
public class TeacherDelete extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String TEACHER_FORM     = "teacherForm";
    private static final String TEACHER          = "teacher";
    private static final String VIEW             = "/WEB-INF/ai/teacher/delete.xhtml";
    private String     contextPath;
    private TeacherDao teacherDao;

    public TeacherDelete() {
        super();
    }

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.teacherDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTeacherDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TeacherForm   teacherForm = new TeacherForm(this.teacherDao);
        beans.Teacher teacher     = teacherForm.get(request);

        request.setAttribute(TEACHER, teacher);
        request.setAttribute(TEACHER_FORM, teacherForm);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TeacherForm teacherForm = new TeacherForm(this.teacherDao);

        teacherForm.delete(request);
        response.sendRedirect(this.contextPath + "/ai/teacher");
    }
}
