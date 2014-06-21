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

@WebFilter("/*")
public class ZPareoFiltre implements Filter 
{
	private static final String URL_CONNEXION              = "/connexion";
	private static final String ATT_SESSION_ETUDIANT       = "sessionEtudiant";
	private static final String ATT_SESSION_PROFESSEUR     = "sessionProfesseur";
    private static final String ATT_SESSION_ADMINISTRATEUR = "sessionAdministrateur";
    
    public void init(FilterConfig config) throws ServletException
    {
    }

    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException 
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        HttpSession session = request.getSession();
        String chemin = request.getRequestURI().substring(request.getContextPath().length());
        
        if ((chemin.startsWith("/ressources")) || (chemin.startsWith("/connexion"))) 
        {
        	chain.doFilter(request, response);
            return;
        }
        
        if ((session.getAttribute(ATT_SESSION_ETUDIANT) == null) && (session.getAttribute(ATT_SESSION_PROFESSEUR) == null) && (session.getAttribute(ATT_SESSION_ADMINISTRATEUR) == null) )
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