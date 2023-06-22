package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.postgres.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.AccountState;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * Backing bean for the profile page.
 *
 * @author Alaa Qasem
 */
@ViewScoped
@Named
public class ProfileBacking implements Serializable {
    private final Logger logger = LogManager.getLogger();
    private User user;
    @Inject
    private UserDAO userDAO;
    @Inject
    private SessionInfo sessionInfo;
    private AccountState[] accountStates;
    private AccountState currentState;


    /**
     * Initializes the dto objects.
     */
    @PostConstruct
    private void init() {
        user = new User();
        user.setAccountState(AccountState.PENDING_ACTIVATION);
    }

    /**
     * Load the user's information.
     */
    public void load() {
        try (Transaction transaction = new Transaction()) {
            userDAO.getUserById(user, transaction);
            transaction.commit();
        } catch (DataNotFoundException e) {
            logger.info("Failed to load user information.");
            throw new IllegalStateException("Failed to load user information.", e);

        }

    }

    /**
     * Save the updated data to the user profile.
     */
    public void save() {
        if (sessionInfo.isAdmin() || sessionInfo.isDeanery()) {
            user.setAccountState(currentState);
        }

        try (Transaction transaction = new Transaction()) {
            try {
                userDAO.update(user, transaction);
                transaction.commit();
            } catch (DataNotFoundException | InvalidInputException | KeyExistsException e) {
                logger.info("Error to save the updated information from " + user.getId());
                throw new IllegalStateException("Error to save the updated information", e);
            }

        }

    }


    /**
     * Method to delete the profile.
     */
    public String deleteProfile() {
        if (sessionInfo.getUser().equals(user) || sessionInfo.isAdmin() || sessionInfo.isDeanery()) {
            try (Transaction transaction = new Transaction()) {
                userDAO.remove(user, transaction);
                sessionInfo.setUser(null);
                return "/views/anonymous/login.xhtml?faces-redirect=true";
            } catch (DataNotFoundException e) {
                logger.info("Error by deleting profile " + user.getId());
                throw new IllegalStateException(e);
            }
        } else {
            return null;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public AccountState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AccountState currentState) {
        this.currentState = currentState;
    }

    public AccountState[] getAccountStates() {
        return AccountState.values();
    }
}
