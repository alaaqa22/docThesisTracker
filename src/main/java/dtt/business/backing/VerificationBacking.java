package dtt.business.backing;

import dtt.dataAccess.repository.Postgres.UserDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Backing bean for verification.
 */
@RequestScoped
@Named
public class VerificationBacking {


    @Inject
    private UserDAO userDAO;

    /**
     * Method to verify the email address.
     */
    public void verifyEmail(){

    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}
