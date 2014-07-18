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

@WebFilter("/ai/*")
public class AdminFilter implements Filter 
{
	private static final String URL_CONNEXION = "/connexion";
	private static final String ADMIN_SESSION = "sessionAdministrateur";
    
    public void init(FilterConfig config) throws ServletException
    {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException 
    {
        HttpServletRequest request   = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session          = request.getSession();
        String path                  = request.getRequestURI().substring(request.getContextPath().length());
        
        if ((path.startsWith("/assets")) || (path.startsWith("/connexion"))) 
        {
        	chain.doFilter(request, response);
            return;
        }
        
        if (session.getAttribute(ADMIN_SESSION) == null)
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