package servlets.ai.subject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Subject;
import dao.DAOFactory;
import dao.SubjectDao;
import forms.SubjectForm;

@WebServlet("/ai/subject/delete")
public class SubjectDelete extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String SUBJECT          = "subject";
    private static final String VIEW             = "/WEB-INF/ai/subject/delete.xhtml";
    private String     contextPath;
    private SubjectDao subjectDao;

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.subjectDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getSubjectDao();
    }

    public SubjectDelete() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubjectForm subjectForm = new SubjectForm(this.subjectDao);
        Subject     subject     = subjectForm.get(request);

        request.setAttribute(SUBJECT, subject);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubjectForm subjectForm = new SubjectForm(this.subjectDao);

        subjectForm.delete(request);
        response.sendRedirect(this.contextPath + "/ai/subject");
    }
}
