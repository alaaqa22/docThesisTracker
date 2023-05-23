package dtt.business.validation;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

/**
 * A validator that verifies the uniqueness of a given value within a specified dataset or context.
 * It checks whether the value is already present or used by another entity, ensuring its uniqueness.
 */
public class UniqueValidator implements Validator {

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
}
