package dtt.business.backing;

import dtt.business.utilities.EmailSender;
import dtt.business.utilities.TokenManager;
import dtt.dataAccess.exceptions.DBConnectionFailedException;
import dtt.dataAccess.repository.postgres.FacultyDAO;
import dtt.dataAccess.repository.postgres.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for the registration page.
 * @author Alaa Qasem
 */
@RequestScoped
@Named
public class RegistrationBacking implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationBacking.class);
    private User user;
    @Inject
    private UserDAO userDAO;
    @Inject
    private FacultyDAO facultyDAO;
    private Faculty faculty;
    private List<Faculty> listOfFaculties;


    /**
     * Initializes the data transfer object for the new registered user.
     */
    @PostConstruct
    public void init()
    {
        LOGGER.debug("init() called.");
        user = new User();
    }

    /**
     * Register a new user, will send the verification link to the email
     * that the user has specified.
     *
     * @return Login page.
     */
    public String register(){
        LOGGER.debug("register() called.");
        return "";
    }

    public User getUser() {
        LOGGER.debug("getUser() called.");
        return user;
    }

    public void setUser(User regUser) {
        LOGGER.debug("setUser() called.");
        this.user = regUser;
    }
    public Faculty getFaculty() {
        return faculty;
    }
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Faculty> getListOfFaculties() {
        try (Transaction transaction = new Transaction()) {
            return facultyDAO.getFaculties(transaction);
        } catch (DBConnectionFailedException e) {
            // Handle the exception
        }

        return new ArrayList<>(); // Return an empty list if an exception occurs
    }
    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

}
