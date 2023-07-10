package dtt.business.backing;

import dtt.business.utilities.Hashing;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.interfaces.FacultyDAO;
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
    @Inject
    private FacultyDAO facultyDAO;
    private List<UserState> userStates;
    private UserState currentUserState;
    private String newEmail;
    private Faculty currentFaculty;
    private List<Faculty> faculties;


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
            if (!(sessionInfo.isAdmin() || isOwnProfile() ||
                    (sessionInfo.deaneryInCurrentFaculty() && user.getUserState().containsKey(sessionInfo.getCurrentFaculty())))) {

                // User does not meet the conditions.
                throw new IllegalStateException();
            }
            transaction.commit();
        } catch (DataNotFoundException e) {
            LOGGER.info("Failed to load user information.");
            throw new IllegalStateException("Failed to load user information.", e);

        }

    }


    /**
     * Remove authentication from a user on a certain faculty.
     */
    public void removeAuth() {

        if ((sessionInfo.isAdmin() || sessionInfo.deaneryInCurrentFaculty()) && !isOwnProfile()) {
            try (Transaction transaction = new Transaction()) {
                if (sessionInfo.deaneryInCurrentFaculty()) {
                    currentFaculty = sessionInfo.getCurrentFaculty();
                }

                //only the admin can see this user state
                if (currentUserState == UserState.ADMIN) {
                    currentFaculty = new Faculty();

                }

                //check if the selected faculty to remove from admin, is actually the user belong to.
                if (!user.getUserState().containsKey(currentFaculty)) {
                    throw new InvalidInputException();
                }

                User userToDelete = new User();
                userToDelete.setId(user.getId());
                userToDelete.getUserState().put(currentFaculty, null);

                userDAO.removeAuth(userToDelete, transaction);
                user.getUserState().remove(currentFaculty);

                userDAO.update(user, transaction);
                transaction.commit();
                LOGGER.info("Authentication" + currentFaculty.getName() + " " + currentUserState + "was removed from" + user.getEmail());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Authentifizierung wurde entfernt!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (DataNotFoundException | DataNotCompleteException e) {
                LOGGER.error("Failed to save the updated information from userID: " + user.getEmail());
                throw new IllegalStateException("Failed to save the updated information", e);

            } catch (InvalidInputException | KeyExistsException e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Der Benutzer ist nicht Teil der ausgewählten Fakultät.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

            }

        }

    }

    /**
     * Allows administrators and deanery members to set the {@link UserState} for users in the system.
     * The admin can set the {@link Faculty} and the {@link UserState} while the deanery can only set the user state of the faculty
     * that he is deanery member in.
     */
    public void addOrUpdateAuth() {
        if (sessionInfo.isAdmin() && !isOwnProfile()) {
            if (currentUserState == UserState.ADMIN) {
                currentFaculty = new Faculty();
            }
            user.getUserState().put(currentFaculty, currentUserState);
        } else if (sessionInfo.deaneryInCurrentFaculty() && !isOwnProfile()) {
            user.getUserState().put(sessionInfo.getCurrentFaculty(), currentUserState);
        }
        try (Transaction transaction = new Transaction()) {
            try {
                userDAO.update(user, transaction);
                transaction.commit();
                LOGGER.info("Changes saved " + user.getEmail());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Authentifizierung wurde aktualisiert.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (DataNotFoundException e) {
                LOGGER.error("Failed to save the updated userstate information from  " + user.getEmail());
                transaction.abort();
                throw new IllegalStateException("Failed to save the updated information", e);
            } catch (InvalidInputException | KeyExistsException e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Fehler bei der Authentifizierung.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

            }
        }
    }

    /**
     * Saves the new updated data from the user.
     */
    public void save() {

        //check if a new password was given,then new password salt will be generated and will be hashed with the given password.
        if (!user.getPassword().isEmpty() || user.getPassword() != null) {
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
                LOGGER.info("Changes saved " + user.getEmail());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Neue Änderungen wurden erfolgreich aktualisiert.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (DataNotFoundException e) {
                LOGGER.error("Failed to save the updated information from userID: " + user.getEmail());
                transaction.abort();
                throw new IllegalStateException("Failed to save the updated information", e);
            } catch (InvalidInputException | KeyExistsException e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Fehler beim Speichern.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);


            }

        }

    }

    /**
     * Check if the logged-in user viewing his own profile.
     *
     * @return {@code true} if the current user viewing his own profile and {@code false} otherwise.
     */
    public boolean isOwnProfile() {
        return sessionInfo.getUser().getId() == user.getId();
    }

    public boolean isSameLevel() {
        return !isOwnProfile() && sessionInfo.getCurrentUserState() == user.getUserState().get(sessionInfo.getCurrentFaculty());
    }


    /**
     * Deletes the user from the system.
     * Only the logged-in user, admin, and deanery members have the permission to delete the profile.
     *
     * @return redirect to the login page if the user delete his profile or redirect to userlist page if an admin or deanery
     * deleted the profile.
     */
    public String deleteProfile() {
        if (isOwnProfile() || sessionInfo.isAdmin() || sessionInfo.deaneryInCurrentFaculty()) {
            try (Transaction transaction = new Transaction()) {
                userDAO.remove(user, transaction);
                transaction.commit();
                LOGGER.debug("Profile with email " + user.getEmail() + " has been removed");
                if (isOwnProfile()) {
                    return "/views/anonymous/login.xhtml?faces-redirect=true";
                }
                return "/views/deanery/userList.xhtml?faces-redirect=true";

            } catch (DataNotFoundException e) {
                LOGGER.error("Failed to remove profile  " + user.getEmail());
                throw new IllegalStateException("Failed to remove profile.", e);
            }
        } else {
            return null;
        }
    }


    public List<Faculty> getFaculties() {
        try (Transaction transaction = new Transaction()) {
            faculties = facultyDAO.getFaculties(transaction);
        }
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
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
        return currentUserState;
    }

    public void setCurrentUserState(UserState currentUserState) {
        this.currentUserState = currentUserState;
    }


    public Faculty getCurrentFaculty() {

        return currentFaculty;
    }

    public void setCurrentFaculty(Faculty currentFaculty) {
        this.currentFaculty = currentFaculty;
    }

    public List<UserState> getUserStates() {
        userStates = new ArrayList<>(Arrays.asList(UserState.values()));

        //remove the admin-state option for the deanery.
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
