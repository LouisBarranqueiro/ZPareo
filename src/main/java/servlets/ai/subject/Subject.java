package servlets.ai.matter;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import forms.MatterForm;
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
        this.subjectDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatterDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MatterForm         matterForm = new MatterForm(this.subjectDao);
        Set<beans.Subject> subjects   = matterForm.search(request);

        request.setAttribute(SUBJECT_FORM, matterForm);
        request.setAttribute(SUBJECTS, subjects);
        request.setAttribute(NUMB_SUBJECTS, subjects.size());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
