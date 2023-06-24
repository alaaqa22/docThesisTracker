package dtt.global.tansport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;

/**
 *  Represents a Faculty in the System.
 *  @author Hadi Abou Hassoun
 */
public class Faculty {
    private static final Logger LOGGER = LogManager.getLogger(Faculty.class);
    private int id; // The ID of the faculty
    private String name; // The name of the faculty

    /**
     * Default constructor for the Faculty class.
     */
    public Faculty () {
    }

    /**
     * Sets the ID of the faculty.
     * @param id The ID of the faculty
     */
    public void setId(int id) {
        LOGGER.debug("setId() called: " + id);
        this.id = id;
    }

    /**
     * Sets the name of the faculty.
     * @param name The name of the faculty
     */
    public void setName(String name) {
        LOGGER.debug("setName() called: " + name);
        this.name = name;
    }

    /**
     * Returns the ID of the faculty.
     * @return The ID of the faculty
     */
    public int getId() {
        LOGGER.debug("getId() called: " + id);
        return id;
    }

    /**
     * Returns the name of the faculty.
     *
     * @return The name of the faculty
     */
    public String getName() {
        LOGGER.debug("getName() called: " + name);
        return name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id &&
                Objects.equals(name, faculty.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
