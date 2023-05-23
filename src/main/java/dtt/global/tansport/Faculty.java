package dtt.global.tansport;

/**
 *  Represents a Faculty in the System.
 */
public class Faculty {
    private int id; // The ID of the faculty
    private String name; // The name of the faculty

    /**
     * Default constructor for the Faculity class.
     */
    public Faculty () {
    }

    /**
     * Constructs a Faculity object with the specified name.
     * @param name The name of the faculty
     */
    public Faculty (String name) {
        this.name = name;
    }

    /**
     * Sets the ID of the faculty.
     * @param id The ID of the faculty
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the faculty.
     * @param name The name of the faculty
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the ID of the faculty.
     * @return The ID of the faculty
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the faculty.
     * @return The name of the faculty
     */
    public String getName() {
        return name;
    }
}
