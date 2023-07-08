package dtt.business.validation;

import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

/**
 * Email address validator that verifies if a provided email address exists within the database.
 *
 * @author Hadi Abou Hassoun
 */
@FacesValidator("EmailAddressAvailabilityValidator")
public class EmailAddressAvailabilityValidator implements Validator {
    private UserDAO userDAO; // UserDAO object for database access.

    /**
     * @param context   FacesContext for the request we are processing
     * @param component UIComponent we are checking for correctness
     * @param value     the value to validate
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String email = (String) value;
        if (isEmailAddressAvailable(context,email)) {
            throw new ValidatorException(new FacesMessage("The provided email address is already in use."));
        }
    }

    /**
     * check email availability
     *
     * @param email The email address to check.
     * @return Return {@code true} if the email address is already in use, {@code false} otherwise.
     */
    private boolean isEmailAddressAvailable(final FacesContext context, final String email) {
        User user = new User();
        userDAO = getUserDAO(context);
        user.setEmail(email);
        try (Transaction transaction = new Transaction()) {
            return userDAO.findUserByEmail(user, transaction);

        }
    }
    private UserDAO getUserDAO(final FacesContext context) {
        return context.getApplication().evaluateExpressionGet(context,
                "#{userDAO}", UserDAO.class);
    }

}
