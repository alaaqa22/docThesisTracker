package dtt.business.validation;

import dtt.business.utilities.ConfigReader;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.util.regex.Pattern;

/**
* A JSF validator for password strength.
 * @author Hadi Abou Hassoun
 */
@FacesValidator("PasswordValidator")
public class PasswordValidator implements Validator {

    private static Pattern pattern;

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

        return pattern.matcher(password).matches();
    }

    static {
        String passwordPattern = ConfigReader.getProperty("PASSWORD_PATTERN");
        pattern = Pattern.compile(passwordPattern);
    }

}
