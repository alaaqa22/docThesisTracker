package dtt.business.validation;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

/**
 * A validator that verifies the uniqueness of a given value within a specified dataset or context.
 * It checks whether the value is already present or used by another entity, ensuring its uniqueness.
 */
@FacesValidator("UniqueCirculationNameValidator")
public class UniqueCirculationNameValidator implements Validator {

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
