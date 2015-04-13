package servlets.ai.matter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Subject;
import dao.DAOFactory;
import dao.SubjectDao;
import forms.MatterForm;

@WebServlet("/ai/subject/delete")
public class SubjectDelete extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String SUBJECT          = "subject";
    private static final String VIEW             = "/WEB-INF/ai/subject/delete.xhtml";
    private String     path;
    private SubjectDao subjectDao;

    public void init(HttpServlet config) throws ServletException {
        this.path = config.getServletContext().getContextPath();
        this.subjectDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getMatterDao();
    }

    public SubjectDelete() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MatterForm matterForm = new MatterForm(this.subjectDao);
        Subject    subject    = matterForm.get(request);

        request.setAttribute(SUBJECT, subject);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MatterForm matterForm = new MatterForm(this.subjectDao);

        matterForm.delete(request);
        response.sendRedirect(this.path + "/ai/subject");
    }

}
