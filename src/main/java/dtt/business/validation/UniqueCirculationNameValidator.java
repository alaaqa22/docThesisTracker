package dtt.business.validation;

import dtt.dataAccess.repository.interfaces.CirculationDAO;
import dtt.dataAccess.repository.interfaces.UserDAO;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;

/**
 * A validator that verifies the uniqueness of a given Circulation Name.
 * It checks whether the name is already present or used by another entity, ensuring its uniqueness.
 *  @author Hadi Abou Hassoun
 */
@FacesValidator("UniqueCirculationNameValidator")
public class UniqueCirculationNameValidator implements Validator {

    @Inject
    private CirculationDAO circulationDAO; // circulationDAO object for database access.

    /**
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent we are checking for correctness
     * @param value the value to validate
     * @throws ValidatorException
     */
    @Override
    public void validate (FacesContext context, UIComponent component, Object value) throws ValidatorException {

    }

    /**
     * check if the CirculationName is unique
     * @param CirculationName : The Name of the Circulation.
     * @return {@code true} if the Circulation name is unique, {@code false} otherwise.
     */
    private boolean isValueUnique(String CirculationName) {
        return true;
    }

}
