package servlets.ai.group;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Group;
import dao.DAOFactory;
import dao.GroupDao;
import forms.GroupForm;

@WebServlet("/ai/group/update")
public class GroupUpdate extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String GROUP            = "group";
    private static final String GROUP_FORM       = "groupForm";
    private static final String VIEW             = "/WEB-INF/ai/group/update.xhtml";
    private String   path;
    private GroupDao groupeDao;

    public void init(HttpServlet config) throws ServletException {
        this.path = config.getServletContext().getContextPath();
        this.groupeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
    }

    public GroupUpdate() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GroupForm groupForm = new GroupForm(this.groupeDao);
        Group     group     = groupForm.get(request);

        request.setAttribute(GROUP, group);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GroupForm groupForm = new GroupForm(this.groupeDao);
        Group     group     = groupForm.edit(request);

        if (groupForm.getErrors().isEmpty()) {
            response.sendRedirect(this.path + "/ai/group");
        }
        else {
            request.setAttribute(GROUP_FORM, groupForm);
            request.setAttribute(GROUP, group);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }

}
