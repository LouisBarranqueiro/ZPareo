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

@WebServlet("/ai/teacher/update")
public class TeacherUpdate extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String MATTERS          = "subjects";
    private static final String GROUPS           = "groups";
    private static final String TEACHERS         = "teacher";
    private static final String TEACHER_FORM     = "teacherForm";
    private static final String VIEW             = "/WEB-INF/ai/teacher/update.xhtml";
    private String     contextPath;
    private TeacherDao teacherDao;
    private GroupDao   groupeDao;
    private SubjectDao matiereDao;

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.matiereDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getSubjectDao();
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
        this.teacherDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTeacherDao();
    }

    public TeacherUpdate() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject       matiere     = new Subject();
        Group         groupe      = new Group();
        Set<Group>    groups      = this.groupeDao.search(groupe);
        Set<Subject>  subjects    = this.matiereDao.search(matiere);
        TeacherForm   teacherForm = new TeacherForm(this.teacherDao);
        beans.Teacher teacher     = teacherForm.get(request);

        request.setAttribute(TEACHER_FORM, teacherForm);
        request.setAttribute(TEACHERS, teacher);
        request.setAttribute(MATTERS, subjects);
        request.setAttribute(GROUPS, groups);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject       matiere     = new Subject();
        Group         groupe      = new Group();
        TeacherForm   teacherForm = new TeacherForm(this.teacherDao);
        Set<Group>    groups      = this.groupeDao.search(groupe);
        Set<Subject>  subjects    = this.matiereDao.search(matiere);
        beans.Teacher teacher     = teacherForm.edit(request);

        if (teacherForm.getErrors().isEmpty()) {
            response.sendRedirect(this.contextPath + "/ai/teacher");
        }
        else {
            request.setAttribute(TEACHER_FORM, teacherForm);
            request.setAttribute(TEACHERS, teacher);
            request.setAttribute(MATTERS, subjects);
            request.setAttribute(GROUPS, groups);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}