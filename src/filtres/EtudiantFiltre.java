package filtres;

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

@WebFilter("/ei/*")
public class EtudiantFiltre implements Filter 
{
	public static final String URL_CONNEXION        = "/connexion";
    public static final String ATT_SESSION_ETUDIANT = "sessionEtudiant";
    
    public void init(FilterConfig config) throws ServletException
    {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException 
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        HttpSession session = request.getSession();
        String chemin = request.getRequestURI().substring(request.getContextPath().length());
        
        if ((chemin.startsWith("/ressources")) || (chemin.startsWith("/connexion"))) 
        {
        	chain.doFilter(request, response);
            return;
        }
        
        if (session.getAttribute(ATT_SESSION_ETUDIANT) == null)
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