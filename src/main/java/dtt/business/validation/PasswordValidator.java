package dtt.business.validation;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

/**
* A JSF validator for password strength.
 */
@FacesValidator("PasswordValidator")
public class PasswordValidator implements Validator {

    private final String PASSWORD_PATTERN = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";

    /**
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent we are checking for correctness
     * @param value the value to validate
     * @throws ValidatorException If the password is not strong enough.
     */
    @Override
    public void validate (FacesContext context, UIComponent component, Object value) throws ValidatorException {

    }

    /**
     * Checks if a password meets the required criteria.
     *
     * @param password The password to be checked.
     * @return {@code true} if the password is valid, {@code false} otherwise.
     */
    private boolean isValidPassword(String password) {

        return password.matches(PASSWORD_PATTERN);
    }


}
