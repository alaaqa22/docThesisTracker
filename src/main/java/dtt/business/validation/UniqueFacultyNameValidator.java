package dtt.business.validation;

import dtt.dataAccess.repository.interfaces.CirculationDAO;
import dtt.dataAccess.repository.interfaces.FacultyDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;

/**
 * @author Hadi Abou Hassoun
 */
@FacesValidator(value = "UniqueFacultyNameValidator", managed = true)
public class UniqueFacultyNameValidator implements Validator {


    private FacultyDAO facultyDAO; // facultyDAO object for database access.


    /**
     * @param context   FacesContext for the request we are processing
     * @param component UIComponent we are checking for correctness
     * @param value     the value to validate
     * @throws ValidatorException
     */
    @Override
    public void validate (FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String facultyName = (String) value;
        if (!isValueUnique (context, facultyName)) {
            FacesMessage msg = new FacesMessage (
                    "A faculty with the same title "
                            + "already exists in the Database.");
            msg.setSeverity (FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException (msg);
        }
    }

    /**
     * check if the Faculty name is unique
     * @param context   FacesContext for the request we are processing
     * @param facultyName : The Name of the Faculty.
     * @return {@code true} if the faculty name is unique, {@code false} otherwise.
     */
    private boolean isValueUnique (final FacesContext context, String facultyName) {

        Faculty faculty = new Faculty ();
        faculty.setName (facultyName);
        facultyDAO = getFacultyDAO (context);
        try (Transaction transaction = new Transaction ()) {
            System.out.println (facultyDAO.findFacultyByName (faculty, transaction));
            System.out.println (facultyName);
            return !facultyDAO.findFacultyByName (faculty, transaction);

        }

    }

    private FacultyDAO getFacultyDAO (final FacesContext context) {
        return context.getApplication ().evaluateExpressionGet (context,
                "#{facultyDAO}", FacultyDAO.class);
    }
}
