package beans;

public class Subject implements Comparable<Subject> {
    private Long          id;
    private String        name;
    private Administrator creator;
    private Administrator editor;

    /**
     * Constructor
     */
    public Subject() {
        this.id = null;
        this.name = null;
        this.creator = null;
        this.editor = null;
    }

    /**
     * Constructor
     * @param id
     */
    public Subject(Long id) {
        this.id = id;
        this.name = null;
        this.creator = null;
        this.editor = null;
    }

    /**
     * Constructor
     * @param subject
     */
    public Subject(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.creator = subject.getCreator();
        this.editor = subject.getEditor();
    }

    /**
     * Returns matter id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets matter id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns matter name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * SetS matter name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns matter creator
     * @return creator
     */
    public Administrator getCreator() {
        return creator;
    }

    /**
     * Sets matter creator
     * @param creator
     */
    public void setCreator(Administrator creator) {
        this.creator = creator;
    }

    /**
     * Returns matter editor
     * @return editor
     */
    public Administrator getEditor() {
        return editor;
    }

    /**
     * Sets matter editor
     * @param editor
     */
    public void setEditor(Administrator editor) {
        this.editor = editor;
    }

    /**
     * Compares two matter id
     * @param subject2
     */
    public int compareTo(Subject subject2) {
        int compId = subject2.getId().compareTo(this.getId());

        return ((compId != 0) ? compId : 0);
    }

}
