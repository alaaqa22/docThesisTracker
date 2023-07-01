package dtt.business.validation;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
/**
 * CompositeValidator is a custom JSF validator that combines the functionality of
 * EmailAddressAvailabilityValidator and EmailAddressSyntaxValidator for the registration facelet to ensure proper
 * syntax and availability.
 * @author Johannes Silvennoinen
 */
@FacesValidator("compositeValidator")
public class CompositeValidator implements Validator {
    /**
     * Validates the specified value for an input component, using EmailAddressAvailabilityValidator
     * and EmailAddressSyntaxValidator.
     *
     * @param context   the FacesContext for the current request
     * @param component the UIComponent being validated
     * @param value     the value to be validated
     * @throws ValidatorException if validation fails
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        EmailAddressAvailabilityValidator availabilityValidator = new EmailAddressAvailabilityValidator();
        EmailAddressSyntaxValidator syntaxValidator = new EmailAddressSyntaxValidator();

        availabilityValidator.validate(context, component, value);
        syntaxValidator.validate(context, component, value);
    }
}
