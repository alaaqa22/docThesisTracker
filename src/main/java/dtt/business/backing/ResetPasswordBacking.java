package dtt.business.backing;

import dtt.business.utilities.TokenManager;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

import dtt.dataAccess.repository.interfaces.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Backing bean for reset-password page.
 */
@RequestScoped
@Named
public class ResetPasswordBacking implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(CirculationDetailsBacking.class);

    @Inject
    private UserDAO userDAO;

    private User user;


    @PostConstruct
    public void init() {
        user = new User();

    }

    /**
     * Method reset a password.it will be new unique token generated and redirect to another page to set a new password.
     */

    public void sendResetPasswordEmail() {

        try (Transaction transaction = new Transaction()) {
            boolean found = userDAO.findUserByEmail(user, transaction);
            if (found) {
                TokenManager tokenManager = new TokenManager();
                tokenManager.generateToken(user);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Email was sent", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Email", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
