package dtt.business.validation;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.time.LocalDateTime;

/**
 * A validator that checks whether a given date and time value falls in the future, ensuring that
 * It is a valid future date and time.
 *  @author Hadi Abou Hassoun
 */
@FacesValidator("FutureDateTimeValidator")
public class FutureDateTimeValidator implements Validator {
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
     * Converts the provided value to a LocalDateTime object.
     *
     * @param value the value to convert
     * @return the LocalDateTime representation of the value
     * @throws IllegalArgumentException if the value type is unsupported
     */
    private LocalDateTime convertToDateTime(Object value) {
        return null;
    }
}
