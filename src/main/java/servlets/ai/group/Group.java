package servlets.ai.group;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.GroupDao;
import forms.GroupForm;

@WebServlet("/ai/group")
public class Group extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String GROUPS           = "groups";
    private static final String NUMB_GROUPS      = "numbGroups";
    private static final String GROUP_FORM       = "groupForm";
    private static final String VIEW             = "/WEB-INF/ai/group/index.xhtml";
    private GroupDao groupDao;

    public Group() {
        super();
    }

    public void init() throws ServletException {
        this.groupDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getGroupDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GroupForm        groupForm = new GroupForm(this.groupDao);
        Set<beans.Group> groups    = groupForm.search(request);

        request.setAttribute(GROUP_FORM, groupForm);
        request.setAttribute(GROUPS, groups);
        request.setAttribute(NUMB_GROUPS, groups.size());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
