package dao;

@SuppressWarnings("serial")
public class DAOConfigurationException extends RuntimeException 
{
	/**
	 * @param message
	 */
    public DAOConfigurationException(String message) 
    {
        super(message);
    }
    
    /**
     * @param message
     * @param cause
     */
    public DAOConfigurationException(String message, Throwable cause) 
    {
        super(message, cause);
    }


    /**
     * @param cause
     */
    public DAOConfigurationException(Throwable cause) 
    {
        super(cause);
    }
}