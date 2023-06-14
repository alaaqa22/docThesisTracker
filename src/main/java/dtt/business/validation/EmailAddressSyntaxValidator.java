package dtt.business.validation;

import dtt.global.utilities.ConfigReader;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.util.Arrays;

/**
 * A FacesValidator implementation that validates email addresses.
 * This validator checks if the domain of the email address is included in a list of valid domains,
 * which are retrieved from a configuration file using the ConfigReader class.
 *
 * @author Hadi Abou Hassoun
 */
@FacesValidator("EmailAddressSyntaxValidator")
public class EmailAddressSyntaxValidator implements Validator {

    /**
     * An array of valid domains.
     */
    private static String[] validDomains;

    /**
     * Validates an email address.
     * This method checks if the domain of the email address is included in the list of valid domains.
     * If the email is not valid, a ValidatorException is thrown.
     *
     * @param context The FacesContext for the request being processed.
     * @param component The UIComponent this validator is associated with.
     * @param value The value to validate.
     * @throws ValidatorException if the email is not valid.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String email = (String) value;

        if (!isValidEmailAddress(email)) {
            throw new ValidatorException(new FacesMessage("Invalid email address. Please enter a valid email address."));
        }
    }

    /**
     * Checks if an email address is valid.
     * An email is considered valid if its domain is included in the list of valid domains.
     *
     * @param email The email address to validate.
     * @return true if the email is valid, false otherwise.
     */
    private boolean isValidEmailAddress(String email) {
        if (!ConfigReader.arePropertiesLoaded()) {
            ConfigReader.loadProperties();
        }

        if (validDomains == null) {
            validDomains = ConfigReader.getProperty("EMAIL_PATTERN").split(",");
        }

        String domain = email.substring(email.indexOf("@") + 1);  // Extract the domain from the email

        return Arrays.asList(validDomains).contains(domain);
    }
}
