/**
 * A converter class that converts between the Faculty object and its corresponding String representation.
 * This class is used in the user interface to convert Faculty objects to Strings for display purposes and vice versa.
 * @author Johannes Silvennoinen
 */
package dtt.business.utilities;

import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;

import dtt.dataAccess.repository.interfaces.FacultyDAO;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The FacultyConverter class is a converter used to convert between Faculty objects and their corresponding String representations.
 * It implements the Converter interface for the Faculty class.
 */
@FacesConverter(value = "facultyConverter")
public class FacultyConverter implements Converter<Faculty> {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Converts the given String value into a Faculty object.
     *
     * @param context   the FacesContext
     * @param component the UIComponent
     * @param value     the String value to convert
     * @return the converted Faculty object, or null if the value is null or empty
     */
    @Override
    public Faculty getAsObject(FacesContext context, UIComponent component, String value) {
        LOGGER.debug("getAsObject called() " + value);
        if (value == null || value.isEmpty()) {
            return null;
        }

        FacultyDAO facultyDAO = getFacultyDAO(context);
        Faculty faculty = new Faculty();
        faculty.setName(value);
        try (Transaction transaction = new Transaction()) {
            if (!facultyDAO.findFacultyByName(faculty, transaction)) {
                return null;
            }
            transaction.commit();
        }
        return faculty;

    }

    /**
     * Converts the given Faculty object into its corresponding String representation.
     *
     * @param context   the FacesContext
     * @param component the UIComponent
     * @param value     the Faculty object to convert
     * @return the String representation of the Faculty object
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Faculty value) {
        LOGGER.debug("getAsString called : " + value.getName());
        return value.getName();
    }
    /**
     * Retrieves the FacultyDAO instance from the FacesContext.
     *
     * @param context the FacesContext
     * @return the FacultyDAO instance
     */
    private FacultyDAO getFacultyDAO(FacesContext context) {
        return context.getApplication().evaluateExpressionGet(context, "#{facultyDAO}", FacultyDAO.class);
    }
}
