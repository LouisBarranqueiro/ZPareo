package servlets.ai.administrator;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.AdministratorDao;
import forms.AdministratorForm;

@WebServlet("/ai/administrator/update")
public class AdministratorUpdate extends HttpServlet {
    private static final String CONF_DAO_FACTORY   = "daofactory";
    private static final String ADMINISTRATOR      = "administrator";
    private static final String ADMINISTRATOR_FORM = "administratorForm";
    private static final String VIEW               = "/WEB-INF/ai/administrator/update.xhtml";
    private String           contextPath;
    private AdministratorDao administratorDao;

    public AdministratorUpdate() {
        super();
    }

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.administratorDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getAdministratorDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdministratorForm   administratorForm = new AdministratorForm(this.administratorDao);
        beans.Administrator administrator     = administratorForm.get(request);

        request.setAttribute(ADMINISTRATOR_FORM, administratorForm);
        request.setAttribute(ADMINISTRATOR, administrator);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdministratorForm   administratorForm = new AdministratorForm(this.administratorDao);
        beans.Administrator administrator     = administratorForm.edit(request);

        if (administratorForm.getErrors().isEmpty()) {
            response.sendRedirect(this.contextPath + "/ai/administrator");
        }
        else {
            request.setAttribute(ADMINISTRATOR_FORM, administratorForm);
            request.setAttribute(ADMINISTRATOR, administrator);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }

}
