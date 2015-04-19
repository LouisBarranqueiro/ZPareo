package servlets.ai.subject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Subject;
import forms.SubjectForm;
import dao.DAOFactory;
import dao.SubjectDao;

@WebServlet("/ai/subject/create")
public class SubjectCreate extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String SUBJECT          = "subject";
    private static final String SUBJECT_FORM     = "subjectForm";
    private static final String VIEW             = "/WEB-INF/ai/subject/create.xhtml";
    private String     contextPath;
    private SubjectDao subjectDao;

    public SubjectCreate() {
        super();
    }

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.subjectDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getSubjectDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubjectForm subjectForm = new SubjectForm(this.subjectDao);
        Subject     subject     = subjectForm.create(request);

        if (subjectForm.getErrors().isEmpty()) {
            response.sendRedirect(this.contextPath + "/ai/subject");
        }
        else {
            request.setAttribute(SUBJECT_FORM, subjectForm);
            request.setAttribute(SUBJECT, subject);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
