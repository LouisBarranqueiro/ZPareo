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

@WebServlet("/ai/group/create")
public class GroupCreate extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String GROUP            = "group";
    private static final String GROUP_FORM       = "groupForm";
    private static final String VIEW             = "/WEB-INF/ai/group/create.xhtml";
    private String   contextPath;
    private GroupDao groupDao;

    public GroupCreate() {
        super();
    }

    public void init() throws ServletException {
        this.contextPath = getServletContext().getContextPath();
        this.groupDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GroupForm groupForm = new GroupForm(this.groupDao);
        Group     group     = groupForm.create(request);

        if (groupForm.getErrors().isEmpty()) {
            response.sendRedirect(this.contextPath + "/ai/group");
        }
        else {
            request.setAttribute(GROUP_FORM, groupForm);
            request.setAttribute(GROUP, group);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
