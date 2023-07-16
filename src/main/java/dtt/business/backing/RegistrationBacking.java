package dtt.business.backing;

import dtt.business.utilities.EmailSender;
import dtt.business.utilities.TokenManager;
import dtt.dataAccess.exceptions.DBConnectionFailedException;
import dtt.dataAccess.repository.interfaces.FacultyDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.AccountState;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import dtt.global.tansport.UserState;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
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
@ViewScoped
@Named("registrationBacking")
public class RegistrationBacking implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationBacking.class);
    @Inject
    private TokenManager tokenManager;
    @Inject
    private FacultyDAO facultyDAO;
    private User user;
    private List<Faculty> listOfFaculties;
    private Faculty faculty;
    public List<Faculty> getListOfFaculties() {
        LOGGER.debug("getListOfFaculties() called.");
        return listOfFaculties;
    }

    public void setListOfFaculties(List<Faculty> listOfFaculties) {
        LOGGER.debug("setListOfFaculties() called.");
        this.listOfFaculties = listOfFaculties;
    }




    /**
     * Initializes the data transfer object for the new registered user.
     */
    @PostConstruct
    public void init()
    {
        LOGGER.debug("init() called.");
        user = new User();
        listOfFaculties = getListOfFacultiesFromDB();
        faculty = listOfFaculties.get(0);
    }

    public String goBack() {
        return "/views/anonymous/login.xhtml?faces-redirect=true";
    }

    /**
     * Register a new user, will send the verification link to the email
     * that the user has specified.
     *
     * @return Login page.
     */
    public String register(){
        LOGGER.debug("register() called.");
        //Fill userDTO
        user.setAccountState(AccountState.PENDING_ACTIVATION);

        LOGGER.debug("User: " + user.getFirstName() + " " + user.getLastName());
        LOGGER.debug(user.getEmail());
        LOGGER.debug(user.getBirthDate());

        user.getUserState().put(faculty, UserState.PENDING);


        EmailSender.sendEmail(user.getEmail(),"Set a new password!", tokenManager.generateToken(user));
        return "/views/anonymous/token?faces-redirect=true";
    }

    public User getUser() {
        LOGGER.debug("getUser() called: " + user.getFirstName());
        return user;
    }

    public void setUser(User user) {
        LOGGER.debug("setUser() called: " + user.getFirstName());
        this.user = user;
    }
    public Faculty getFaculty() {
        LOGGER.debug("getFaculty() called: " + faculty.getName());
        return faculty;
    }
    public void setFaculty(Faculty faculty) {
        LOGGER.debug("setFaculty() called: " + faculty.getName());
        this.faculty = faculty;
    }
    public List<Faculty> getListOfFacultiesFromDB() {
        LOGGER.debug("getListOfFaculties() called.");
        try (Transaction transaction = new Transaction()) {
            return facultyDAO.getFaculties(transaction);
        } catch (DBConnectionFailedException e) {
            LOGGER.error("DBConnectionFailedException :" + e.getMessage());
        }

        return new ArrayList<>(); // Return an empty list if an exception occurs
    }
}
