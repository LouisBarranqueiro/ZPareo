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

@SuppressWarnings("serial")
@WebServlet("/pi/examen/creation")
public class TestCreation extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String TEST             = "test";
	private static final String TEST_FORM        = "testForm";
    private static final String VIEW             = "/WEB-INF/pi/test/creation.jsp";
	private TestDao testDao;
	
    public TestCreation() 
    {
        super();
    }
    
    public void init() throws ServletException 
    {
        this.testDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTestDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		TestForm testForm = new TestForm(this.testDao);
        beans.Test test   = testForm.create(request);
		
        if (testForm.getErrors().isEmpty())
        {
        	response.sendRedirect("http://localhost:8080/ZPareo/pi/examen/edition?id=" + test.getId());   
        }
        else
        {
        	request.setAttribute(TEST_FORM, testForm);
        	request.setAttribute(TEST, test);
        	this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);   
        }
	}

}
