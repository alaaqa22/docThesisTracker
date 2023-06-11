package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.UserDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * Backing bean for the new password page.
 * @author Alaa Qasem
 */
@RequestScoped
@Named
public class SetNewPasswordBacking implements Serializable {
    private String password;
    @Inject
    private UserDAO userDAO;
    @Inject
    private SessionInfo sessionInfo;

    /**
     * Save the new password.
     */
    public void save(){

    }

    public String getPassword() {
        return password;
    }
}
