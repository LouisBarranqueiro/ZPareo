package servlets.ai.administrator;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.AdministratorDao;
import forms.AdministratorForm;

@WebServlet("/ai/administrator")
public class Administrator extends HttpServlet {
    private static final String CONF_DAO_FACTORY    = "daofactory";
    private static final String ADMINISTRATORS      = "administrators";
    private static final String NUMB_ADMINISTRATORS = "numbAdministrators";
    private static final String ADMINISTRATOR_FORM  = "administratorForm";
    private static final String VIEW                = "/WEB-INF/ai/administrator/index.xhtml";
    private String           path;
    private AdministratorDao administratorDao;

    public Administrator() {
        super();
    }

    public void init() throws ServletException {
        this.administratorDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getAdministratorDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdministratorForm        administratorForm = new AdministratorForm(this.administratorDao);
        Set<beans.Administrator> administrators    = administratorForm.search(request);

        request.setAttribute(ADMINISTRATOR_FORM, administratorForm);
        request.setAttribute(ADMINISTRATORS, administrators);
        request.setAttribute(NUMB_ADMINISTRATORS, administrators.size());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
