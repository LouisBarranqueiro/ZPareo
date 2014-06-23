package utilitaires;

@SuppressWarnings("serial")
public class MailConfigurationException extends RuntimeException 
{
	/**
	 * @param message
	 */
    public MailConfigurationException(String message) 
    {
        super(message);
    }
    
    /**
     * @param message
     * @param cause
     */
    public MailConfigurationException(String message, Throwable cause) 
    {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public MailConfigurationException(Throwable cause) 
    {
        super(cause);
    }
}
