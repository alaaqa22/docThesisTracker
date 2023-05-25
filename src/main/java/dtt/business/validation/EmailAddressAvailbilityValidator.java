package dtt.business.validation;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

/**
 * Email address validator that verifies if a provided email address exists within the database.
 */
@FacesValidator("EmailAddressAvailbilityValidator")
public class EmailAddressAvailbilityValidator implements Validator {

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
     * check email availability
     * @param email The email address to check.
     * @return Return {@code true} if the email address is available, {@code false} otherwise.
     */
    private boolean isEmailAddressAvailable(String email) {
        return true;
    }
}
