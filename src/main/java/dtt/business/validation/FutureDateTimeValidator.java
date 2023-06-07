package dtt.business.validation;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.time.LocalDateTime;

/**
 * A validator that checks whether a given date value falls in the future, ensuring that
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

        LocalDateTime input = convertToDateTime (value);
        LocalDateTime current = LocalDateTime.now ();

        if(input.isBefore (current)){
            throw new ValidatorException(
                    new FacesMessage("Invalid date and time. Please enter a future date and time."));
        }
        }

    /**
     * Converts the provided value to a LocalDateTime object.
     *
     * @param value the value to convert
     * @return the LocalDateTime representation of the value
     * @throws IllegalArgumentException if the value type is unsupported
     */
    private LocalDateTime convertToDateTime(Object value) {
        if(value instanceof LocalDateTime){
            return (LocalDateTime) value;
        }
        else{
            throw new IllegalArgumentException ("Unsupported value type for conversion to LocalDateTime");
        }
    }
}
