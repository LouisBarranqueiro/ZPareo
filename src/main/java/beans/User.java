package beans;

public abstract class User {
    private Long          id;
    private String        lastName;
    private String        firstName;
    private String        emailAddress;
    private String        password;
    private Administrator creator;
    private Administrator editor;

    /**
     * Constructor
     */
    public User() {
        this.id = null;
        this.lastName = null;
        this.firstName = null;
        this.emailAddress = null;
        this.password = null;
        this.creator = null;
        this.editor = null;
    }

    /**
     * Constructor
     * @param user
     */
    public User(User user) {
        this.id = user.getId();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.emailAddress = user.getEmailAddress();
        this.password = user.getPassword();
        this.creator = user.getCreator();
        this.editor = user.getEditor();
    }

    /**
     * Constructor
     * @param administrator
     */
    public User(Administrator administrator) {
        this.id = administrator.getId();
        this.lastName = administrator.getLastName();
        this.firstName = administrator.getFirstName();
        this.emailAddress = administrator.getEmailAddress();
        this.password = administrator.getPassword();
        this.creator = administrator.getCreator();
        this.editor = administrator.getEditor();
    }

    /**
     * Return suser's id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets user's id
     * @return id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns user's last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets user's last name
     * @return lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns user's first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets user's firstName
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns user's email address
     * @return emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets user's email address
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Returns user's password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user's password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns user's creator
     * @return creator
     */
    public Administrator getCreator() {
        return creator;
    }

    /**
     * Sets user's creator
     * @param creator
     */
    public void setCreator(Administrator creator) {
        this.creator = creator;
    }

    /**
     * Returns user's editor
     * @return editor
     */
    public Administrator getEditor() {
        return editor;
    }

    /**
     * Sets user's editor
     * @param editor
     */
    public void setEditor(Administrator editor) {
        this.editor = editor;
    }

}
