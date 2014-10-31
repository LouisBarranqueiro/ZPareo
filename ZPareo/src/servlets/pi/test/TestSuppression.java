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
@WebServlet("/pi/examen/suppression")
public class TestSuppression extends HttpServlet 
{
	private static final String CONF_DAO_FACTORY       = "daofactory";
	private static final String ATT_EXAMEN             = "test";
	private static final String VUE_SUPPRESSION        = "/WEB-INF/pi/test/suppression.jsp";
	private TestDao testDao;
	
	public void init() throws ServletException 
    {
        this.testDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTestDao();
    }

    public TestSuppression() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		TestForm testForm = new TestForm(this.testDao);
		beans.Test test   = testForm.get(request);
		
		request.setAttribute(ATT_EXAMEN, test);
        this.getServletContext().getRequestDispatcher(VUE_SUPPRESSION).forward(request, response);   
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		TestForm testForm = new TestForm(this.testDao);
		
		testForm.delete(request);
		response.sendRedirect("http://localhost:8080/ZPareo/pi/examen");  
	}

}
