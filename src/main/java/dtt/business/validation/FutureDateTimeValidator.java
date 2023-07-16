package dtt.business.validation;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A validator that checks whether a given date value falls in the future, ensuring that
 * It is a valid future date and time.
 *
 * @author Hadi Abou Hassoun
 */
@FacesValidator("FutureDateTimeValidator")
public class FutureDateTimeValidator implements Validator {
    /**
     * @param context   FacesContext for the request we are processing
     * @param component UIComponent we are checking for correctness
     * @param value     the value to validate
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        LocalDate input = (LocalDate) value;
        LocalDate originalDate = (LocalDate) component.getAttributes().get("originalDate");
        if (input.equals(originalDate)) {
            return;
        }
        LocalDateTime current = LocalDateTime.now();

        if (input.isBefore(current.toLocalDate())) {
            throw new ValidatorException(
                    new FacesMessage("Invalid date and time. Please enter a future date and time."));
        }

    }


}
