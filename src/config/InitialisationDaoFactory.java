package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import dao.DAOFactory;

@WebListener
public class InitialisationDaoFactory implements ServletContextListener 
{
	private static final String ATT_DAO_FACTORY = "daofactory";
    private DAOFactory daoFactory;

    public InitialisationDaoFactory() 
    {
    }

    /**
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) 
    {
        ServletContext servletContext = event.getServletContext();
        this.daoFactory = DAOFactory.getInstance();
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
    }
	
    /**
     * @param arg0
     */
    public void contextDestroyed(ServletContextEvent arg0) 
    {
    }
	
}