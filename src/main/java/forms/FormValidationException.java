package forms;

@SuppressWarnings("serial")
public class FormValidationException extends Exception {
    public FormValidationException(String message) {
        super(message);
    }
}
