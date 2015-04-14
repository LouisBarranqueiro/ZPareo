package servlets.pi.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.TestDao;
import forms.TestForm;

@WebServlet("/ti/test/update")
public class TestUpdate extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String TEST             = "test";
    private static final String TEST_FORM        = "testForm";
    private static final String VIEW             = "/WEB-INF/ti/test/update.xhtml";
    private String  contextPath;
    private TestDao testDao;

    public TestUpdate() {
        super();
    }

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.testDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTestDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TestForm   testForm = new TestForm(this.testDao);
        beans.Test test     = testForm.get(request);

        request.setAttribute(TEST_FORM, testForm);
        request.setAttribute(TEST, test);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TestForm   testForm = new TestForm(this.testDao);
        beans.Test test     = testForm.edit(request);

        if (testForm.getErrors().isEmpty()) {
            response.sendRedirect(this.contextPath + "/ti/test");
        }
        else {
            request.setAttribute(TEST_FORM, testForm);
            request.setAttribute(TEST, test);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
