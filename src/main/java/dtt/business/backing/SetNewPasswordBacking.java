package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.UserDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Backing bean for the new password page.
 * @author Alaa Qasem
 */
@RequestScoped
@Named
public class SetNewPasswordBacking {
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

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public String getPassword() {
        return password;
    }
}
