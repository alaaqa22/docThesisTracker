package dtt.business.validation;

import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.global.tansport.User;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;

/**
 * Email address validator that verifies if a provided email address exists within the database.
 *  @author Hadi Abou Hassoun
 */
@FacesValidator("EmailAddressAvailbilityValidator")
public class EmailAddressAvailbilityValidator implements Validator {
    @Inject
    private UserDAO userDAO; // UserDAO object for database access.

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
        User user = new User();
        user.setEmail(email);
        return true;
    }
}
