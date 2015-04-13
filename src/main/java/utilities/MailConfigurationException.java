package utilities;

public class MailConfigurationException extends RuntimeException {

    public MailConfigurationException(String message) {
        super(message);
    }

    public MailConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailConfigurationException(Throwable cause) {
        super(cause);
    }
}
