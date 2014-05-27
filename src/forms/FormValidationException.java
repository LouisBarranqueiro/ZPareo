package forms;

public class FormValidationException extends Exception 
{
    /**
     * @param message
     */
	public FormValidationException(String message) 
	{
		super(message);
	}
}
