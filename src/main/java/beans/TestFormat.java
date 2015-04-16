package beans;

public class TestFormat {
    private Long   id;
    private String name;

    /**
     * Constructor
     */
    public TestFormat() {
        this.id = null;
        this.name = null;
    }

    /**
     * Constructor
     * @param format
     */
    public TestFormat(TestFormat format) {
        this.id = format.getId();
        this.name = format.getName();
    }

    /**
     * Returns test format id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets test format id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns test format name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets test format name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}
