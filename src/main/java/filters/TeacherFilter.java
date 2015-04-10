package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/pi/*")
public class TeacherFilter implements Filter 
{
	private static final String URL_CONNEXION   = "/connexion";
	private static final String TEACHER_SESSION = "teacherSession";
    
    public void init(FilterConfig config) throws ServletException
    {
    }

    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException 
    {
        HttpServletRequest request   = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        HttpSession session          = request.getSession();
        String path 				 = request.getRequestURI().substring(request.getContextPath().length());
        
        if ((path.startsWith("/javax.faces.resource")) || (path.startsWith("/connexion")))
        {
        	chain.doFilter(request, response);
            return;
        }
        
        if (session.getAttribute(TEACHER_SESSION) == null)
        {
        	request.getRequestDispatcher(URL_CONNEXION).forward(request, response);
        } 
        else 
        {
            chain.doFilter(request, response);
        }
    }

    public void destroy()
    {
    }
}