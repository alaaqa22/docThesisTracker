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

@FacesConverter(value = "facultyConverter")
public class FacultyConverter implements Converter<Faculty> {
    private static final Logger LOGGER = LogManager.getLogger();

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
            if (facultyDAO.findFacultyByName(faculty, transaction)) {

            } else return null;
            transaction.commit();
        }
        return faculty;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Faculty value) {
        LOGGER.debug("getAsString called : " + value.getName());
        return value.getName();
    }

    private FacultyDAO getFacultyDAO(FacesContext context) {
        return context.getApplication().evaluateExpressionGet(context, "#{facultyDAO}", FacultyDAO.class);
    }
}
