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

@WebServlet("/ai/teacher/create")
public class TeacherCreate extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String SUBJECTS         = "subjects";
    private static final String GROUPS           = "groups";
    private static final String TEACHER          = "teacher";
    private static final String TEACHER_FORM     = "teacherForm";
    private static final String VIEW             = "/WEB-INF/ai/teacher/create.xhtml";
    private String     contextPath;
    private SubjectDao subjectDao;
    private GroupDao   groupDao;
    private TeacherDao teacherDao;

    public TeacherCreate() {
        super();
    }

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.subjectDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getSubjectDao();
        this.groupDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
        this.teacherDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTeacherDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject      subject  = new Subject();
        Group        group    = new Group();
        Set<Group>   groups   = this.groupDao.search(group);
        Set<Subject> subjects = this.subjectDao.search(subject);

        request.setAttribute(GROUPS, groups);
        request.setAttribute(SUBJECTS, subjects);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TeacherForm   teacherForm = new TeacherForm(this.teacherDao);
        beans.Teacher teacher     = teacherForm.create(request);

        if (teacherForm.getErrors().isEmpty()) {
            response.sendRedirect(this.contextPath + "/ai/teacher");
        }
        else {
            Subject subject = new Subject();
            Group group = new Group();
            Set<Group> groups = this.groupDao.search(group);
            Set<Subject> subjects = this.subjectDao.search(subject);

            request.setAttribute(TEACHER_FORM, teacherForm);
            request.setAttribute(TEACHER, teacher);
            request.setAttribute(GROUPS, groups);
            request.setAttribute(SUBJECTS, subjects);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
