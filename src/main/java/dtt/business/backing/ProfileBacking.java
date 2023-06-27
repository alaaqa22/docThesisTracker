package dtt.business.backing;

import dtt.business.utilities.Hashing;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import dtt.global.tansport.UserState;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * Backing bean for the profile page.
 *
 * @author Alaa Qasem
 */
@ViewScoped
@Named
public class ProfileBacking implements Serializable {
    private final static Logger LOGGER = LogManager.getLogger(ProfileBacking.class);
    private User user;
    @Inject
    private UserDAO userDAO;
    @Inject
    private SessionInfo sessionInfo;
    private List<UserState> userStates;
    private UserState currentUserState;
    private String newEmail;


    /**
     * Initializes the user dto object.
     */
    @PostConstruct
    private void init() {
        user = new User();
    }

    /**
     * Load the user's information.
     */
    public void load() {
        try (Transaction transaction = new Transaction()) {
            userDAO.getUserById(user, transaction);
            transaction.commit();
        } catch (DataNotFoundException e) {
            LOGGER.info("Failed to load user information.");
            throw new IllegalStateException("Failed to load user information.", e);

        }

    }

    /**
     * Saves the updated data to the user profile.
     * Allows administrators and deanery members to set the {@link UserState} for users in the system.
     */
    public void save() {
        if (sessionInfo.isAdmin() || sessionInfo.isDeanery()) {
            Map<Faculty, UserState> userState = user.getUserState();
            Set<Faculty> facultySet = userState.keySet();
            userState.put(facultySet.iterator().next(), currentUserState);
            user.setUserState(userState);
        }

        if (!user.getPassword().isEmpty()) {
            try {
                user.setPasswordSalt(Hashing.generateSalt());
                user.setPasswordHashed(Hashing.hashPassword(user.getPassword(), user.getPasswordSalt()));
                LOGGER.info("Password has been changed from " + user.getEmail());

            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                LOGGER.error("Failed to change password " + user.getEmail());
                throw new IllegalStateException("Failed to change password", e);
            }
        }
        if (!newEmail.isEmpty()) {
            LOGGER.info("Email has been changed from " + user.getEmail() + " to " + newEmail);
            user.setEmail(newEmail);
        }

        try (Transaction transaction = new Transaction()) {
            try {
                userDAO.update(user, transaction);
                transaction.commit();
                LOGGER.info("Changes saved" + user.getEmail());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Ihre persönlichen Informationen wurden erfolgreich aktualisiert.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (DataNotFoundException | InvalidInputException | KeyExistsException e) {
                LOGGER.error("Failed to save the updated information from userID: " + user.getEmail());
                transaction.abort();
                throw new IllegalStateException("Failed to save the updated information", e);
            }

        }

    }


    /**
     * Deletes the user profile.
     * Only the logged-in user, admin, and deanery members have the permission to delete the profile.
     */
    public String deleteProfile() {
        if (sessionInfo.getUser().equals(user) || sessionInfo.isAdmin() || sessionInfo.isDeanery()) {
            try (Transaction transaction = new Transaction()) {
                userDAO.remove(user, transaction);
                transaction.commit();
                LOGGER.debug("Profile with ID= " + user.getEmail() + " has been removed");
                return "/views/anonymous/login.xhtml?faces-redirect=true";
            } catch (DataNotFoundException e) {
                LOGGER.error("Failed to remove profile  " + user.getEmail());
                throw new IllegalStateException("Failed to remove profile.", e);
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

    public UserState getCurrentUserState() {
        Map<Faculty, UserState> userState = user.getUserState();
        currentUserState = userState.values().iterator().next();
        return currentUserState;
    }

    public void setCurrentUserState(UserState currentUserState) {
        this.currentUserState = currentUserState;
    }

    public List<UserState> getUserStates() {
        userStates = new ArrayList<>(Arrays.asList(UserState.values()));

        if (sessionInfo.isDeanery()) {
            userStates.remove(UserState.ADMIN);
        }

        return userStates;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
