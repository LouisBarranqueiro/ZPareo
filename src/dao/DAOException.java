package dao;

@SuppressWarnings("serial")
public class DAOException extends RuntimeException 
{
	/**
	 * @param message
	 */
    public DAOException(String message) 
    {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public DAOException(String message, Throwable cause) 
    {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public DAOException(Throwable cause) 
    {
        super( cause );
    }
}
