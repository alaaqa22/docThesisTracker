package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.postgres.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * Backing bean for the profile page.
 *
 * @author Alaa Qasem
 */
@ViewScoped
@Named
public class ProfileBacking implements Serializable {
    private User user;
    @Inject
    private UserDAO userDAO;
    @Inject
    private SessionInfo sessionInfo;


    /**
     * Initializes the dto objects.
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
            userDAO.getUserById(sessionInfo.getUser(), transaction);
            user = sessionInfo.getUser();
        } catch (DataNotFoundException e) {

        }

    }

    /**
     * Save the updated data to the user profile.
     */
    public void save() {
        try (Transaction transaction = new Transaction()) {
            userDAO.update(user, transaction);
        } catch (DataNotFoundException | InvalidInputException | KeyExistsException e) {

        }

    }


    /**
     * Method to delete the profile.
     */
    public String deleteProfile() {
        if (sessionInfo.getUser().equals(user)) {

            try (Transaction transaction = new Transaction()) {
                userDAO.remove(user, transaction);
                sessionInfo.setUser(null);
            } catch (DataNotFoundException e) {
            }
            return "/views/anonymous/login.xhtml?faces-redirect=true";
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


}
