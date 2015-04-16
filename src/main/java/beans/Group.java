package beans;

public class Group implements Comparable<Group> {
    private Long          id;
    private String        name;
    private Administrator creator;
    private Administrator editor;

    /**
     * Constructor
     */
    public Group() {
        this.id = null;
        this.name = null;
        this.creator = null;
        this.editor = null;
    }

    /**
     * Constructor
     * @param group
     */
    public Group(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.creator = group.getCreator();
        this.creator = group.getEditor();
    }

    /**
     * Returns group id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets group id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns group name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets group name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns group creator
     * @return creator
     */
    public Administrator getCreator() {
        return creator;
    }

    /**
     * Sets group creator
     * @param creator
     */
    public void setCreator(Administrator creator) {
        this.creator = creator;
    }

    /**
     * Returns group editor
     * @return editor
     */
    public Administrator getEditor() {
        return editor;
    }

    /**
     * Sets group editor
     * @param editor
     */
    public void setEditor(Administrator editor) {
        this.editor = editor;
    }

    /**
     * Compares two group id
     * @param group2
     */
    public int compareTo(Group group2) {
        int compId = group2.getId().compareTo(this.getId());

        return ((compId != 0) ? compId : 0);
    }
}