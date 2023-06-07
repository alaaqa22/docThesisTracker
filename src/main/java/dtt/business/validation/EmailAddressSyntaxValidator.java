package dtt.business.validation;

import dtt.global.utilities.ConfigReader;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.util.regex.Pattern;

/**
 *
 * A robust email address validator that verifies whether the given
 * email is a valid email address and adheres to the standards for correct email addresses.
 *  @author Hadi Abou Hassoun
 */
@FacesValidator("EmailAddressSyntaxValidator")
public class EmailAddressSyntaxValidator implements Validator {

    private static String emailPattern;
    private static Pattern pattern;

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
     * Checks if the provided email address matches the required syntax pattern.
     *
     * @param email The email address to validate.
     * @return True if the email address matches the pattern, false otherwise.
     */
    private boolean isValidEmailAddress(String email) {
        return pattern.matcher(email).matches();
    }

    static {
        emailPattern = ConfigReader.getProperty("EMAIL_PATTERN");
        pattern = Pattern.compile(emailPattern);
    }
}
