package dtt.business.backing;

import dtt.business.utilities.EmailSender;
import dtt.business.utilities.TokenManager;
import dtt.dataAccess.exceptions.DBConnectionFailedException;
import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.postgres.FacultyDAO;
import dtt.dataAccess.repository.postgres.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.AccountState;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import dtt.global.tansport.UserState;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Backing bean for the registration page.
 * @author Alaa Qasem
 */
@RequestScoped
@Named("registrationBacking")
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
        listOfFaculties = getListOfFaculties();
    }

    /**
     * Register a new user, will send the verification link to the email
     * that the user has specified.
     *
     * @return Login page.
     */
    public String register(){
        LOGGER.debug("register() called.");
        LOGGER.debug("User: " + user.getFirstName() + " " + user.getLastName());
        LOGGER.debug(user.getEmail());
        LOGGER.debug(user.getBirthDate());

        user.setAccountState(AccountState.PENDING_ACTIVATION);
        Map<Faculty, UserState> facultyMap = new HashMap<>();
        facultyMap.put(faculty, UserState.PENDING);
        user.setUserState(facultyMap);


        try (Transaction transaction = new Transaction()){
            userDAO.add(user, transaction);
            TokenManager tokenManager = new TokenManager();
            boolean success = userDAO.findUserByEmail(user, transaction);
            transaction.commit();
            if (success) {
                tokenManager.generateToken(user);
                return "/views/anonymous/login?faces-redirect=true";
            } else {
                return null;
            }



        } catch (DataNotCompleteException | KeyExistsException | InvalidInputException e) {
            LOGGER.error("Exceptions thrown");
            throw new RuntimeException(e);
        }

    }

    public User getUser() {
        LOGGER.debug("getUser() called.");
        return user;
    }

    public void setUser(User user) {
        LOGGER.debug("setUser() called.");
        this.user = user;
    }
    public Faculty getFaculty() {
        LOGGER.debug("getFaculty() called.");
        return faculty;
    }
    public void setFaculty(Faculty faculty) {
        LOGGER.debug("setFaculty() called.");
        this.faculty = faculty;
    }

    public List<Faculty> getListOfFaculties() {
        LOGGER.debug("getListOfFaculties() called.");
        try (Transaction transaction = new Transaction()) {
            return facultyDAO.getFaculties(transaction);
        } catch (DBConnectionFailedException e) {
            // Handle the exception
        }

        return new ArrayList<>(); // Return an empty list if an exception occurs
    }
}
