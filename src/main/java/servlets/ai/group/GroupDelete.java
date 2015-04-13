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

@WebServlet("/ai/group/delete")
public class GroupDelete extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String GROUP            = "group";
    private static final String VIEW             = "/WEB-INF/ai/group/delete.xhtml";
    private String   contextPath;
    private GroupDao groupDao;

    public GroupDelete() {
        super();
    }

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.groupDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GroupForm groupeForm = new GroupForm(this.groupDao);
        Group     group      = groupeForm.get(request);

        request.setAttribute(GROUP, group);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GroupForm groupeForm = new GroupForm(this.groupDao);

        groupeForm.delete(request);
        response.sendRedirect(this.contextPath + "/ai/group");
    }
}
