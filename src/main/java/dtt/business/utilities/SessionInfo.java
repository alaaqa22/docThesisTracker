package dtt.business.utilities;

import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import dtt.global.tansport.UserState;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Map;

/**
 * The SessionInfo class represents the session-scoped bean that stores
 * the User Data Transfer Object (DTO) for the currently logged-in user.
 * It provides access to the user's information throughout the user session.
 *
 * <p>The SessionInfo bean is designed to be used in conjunction with JSF's
 * session scope, using the @SessionScoped annotation. This ensures that
 * each user has their own instance of the SessionInfo bean, and the user's
 * data remains accessible throughout their session.
 *
 * <p>The SessionInfo bean is responsible for storing and retrieving the
 * User DTO for the logged-in user. It allows other components and pages
 * in the application to access the user's information without the need
 * for repetitive retrieval from a data source.
 * @author Johannes Silvennoinen
 */
@Named("sessionInfo")
@SessionScoped
public class SessionInfo implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final long serialVersionUID = 10;
    private User user = new User ();

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    private boolean loggedIn;

    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        LOGGER.info ("User set:" + user.getId ());
        LOGGER.info ("User is a:" + user.getUserState ());
        this.user = user;
    }

    /**
     * Checks if the user is an Admin.
     *
     * @return true if the user is an Admin.
     */
    public boolean isAdmin () {
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.ADMIN) {
                return true;
            }
        }
        return false;
    }

    public boolean isCommitteeMember () {
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.EXAMINCOMMITTEEMEMBERS) {
                return true;
            }
        }
        return false;
    }

    public boolean isExaminer () {
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.EXAMINER) {
                return true;
            }
        }
        return false;
    }

    public boolean isDeanery () {
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.DEANERY) {
                return true;
            }
        }
        return false;
    }

    public boolean isPending () {
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.PENDING) {
                return true;
            }
        }
        return false;
    }

    // test
    public boolean isAnonymous () {
        Map<Faculty, UserState> map = user.getUserState();
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }

}