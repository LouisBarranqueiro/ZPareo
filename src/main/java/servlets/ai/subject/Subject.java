package servlets.ai.subject;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import forms.SubjectForm;
import dao.DAOFactory;

@WebServlet("/ai/subject")
public class Subject extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String SUBJECTS         = "subjects";
    private static final String NUMB_SUBJECTS    = "numbSubjects";
    private static final String SUBJECT_FORM     = "subjectForm";
    private static final String VIEW             = "/WEB-INF/ai/subject/index.xhtml";
    private SubjectDao subjectDao;

    public Subject() {
        super();
    }

    public void init() throws ServletException {
        this.subjectDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getSubjectDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubjectForm        subjectForm = new SubjectForm(this.subjectDao);
        Set<beans.Subject> subjects    = subjectForm.search(request);

        request.setAttribute(SUBJECT_FORM, subjectForm);
        request.setAttribute(SUBJECTS, subjects);
        request.setAttribute(NUMB_SUBJECTS, subjects.size());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
