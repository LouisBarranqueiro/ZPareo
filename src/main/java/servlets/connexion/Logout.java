package servlets.connexion;

import dao.DAOFactory;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/logout")
public class Logout extends HttpServlet {
    private String path;

    public Logout() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.path = config.getServletContext().getContextPath();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.invalidate();
        response.sendRedirect(this.path + "/logout");
    }

}
