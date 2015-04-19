package servlets.pi.test;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.TestDao;
import forms.TestForm;

@WebServlet("/ti/test")
public class Test extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String TESTS            = "tests";
    private static final String NUMB_TESTS       = "numbTests";
    private static final String TEST_FORM        = "testForm";
    private static final String VIEW             = "/WEB-INF/ti/test/index.xhtml";
    private TestDao testDao;

    public Test() {
        super();
    }

    public void init() throws ServletException {
        this.testDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTestDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TestForm        testForm = new TestForm(testDao);
        Set<beans.Test> tests    = testForm.search(request);

        request.setAttribute(TEST_FORM, testForm);
        request.setAttribute(TESTS, tests);
        request.setAttribute(NUMB_TESTS, tests.size());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}