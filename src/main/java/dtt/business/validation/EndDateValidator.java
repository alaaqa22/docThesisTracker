package dtt.business.validation;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.time.LocalDate;

/**
 * Validator that check if end date in the circulation is after start date.
 */
@FacesValidator("EndDateValidator")
public class EndDateValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        LocalDate endDate = (LocalDate) value;

        // Get the start date from the model or UIComponent
        Object startDateValue = uiComponent.getAttributes().get("startDate");

        if (!(startDateValue instanceof LocalDate)) {
            // Handle the case when the start date is not available or invalid
            throw new ValidatorException(new FacesMessage("Invalid start date"));
        }

        LocalDate startDate = (LocalDate) startDateValue;

        // Perform the date validation
        if (endDate.isBefore(startDate)) {
            throw new ValidatorException(new FacesMessage("End date must be after the start date"));
        }
    }
}

