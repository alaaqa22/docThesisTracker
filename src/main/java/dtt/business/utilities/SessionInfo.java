package dtt.business.utilities;

import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import dtt.global.tansport.UserState;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *
 * @author Johannes Silvennoinen
 */
@Named("sessionInfo")
@SessionScoped
public class SessionInfo implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger (SessionInfo.class);
    private static final long serialVersionUID = 10;
    private User user = new User ();

    public boolean isLoggedIn () {
        LOGGER.debug ("isLoggedIn() called.");
        return loggedIn;
    }

    public void setLoggedIn (boolean loggedIn) {
        LOGGER.debug ("setLoggedIn() called.");
        this.loggedIn = loggedIn;
    }

    private boolean loggedIn;

    public User getUser () {
        LOGGER.debug ("getUser() called.");
        return user;
    }

    public void setUser (User user) {
        LOGGER.debug ("setUser() called.");
        if (user == null) {
            setLoggedIn (false);
        }
        this.user = user;
        setLoggedIn (true);
    }

    /**
     * Checks if the user is an Admin.
     *
     * @return true if the user is an Admin.
     */
    public boolean isAdmin () {
        LOGGER.debug ("isAdmin() called.");
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.ADMIN) {
                return true;
            }
        }
        return false;
    }

    public boolean isCommitteeMember () {
        LOGGER.debug ("isCommitteeMember() called.");
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.EXAMINCOMMITTEEMEMBERS) {
                return true;
            }
        }
        return false;
    }

    public boolean isExaminer () {
        LOGGER.debug ("isExaminer() called.");
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.EXAMINER) {
                return true;
            }
        }
        return false;
    }

    public boolean isDeanery () {
        LOGGER.debug ("isDeanery() called.");
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.DEANERY) {
                return true;
            }
        }
        return false;
    }

    public boolean isPending () {
        LOGGER.debug ("isPending() called.");
        Map<Faculty, UserState> map = user.getUserState ();
        for (UserState state : map.values ()) {
            if (state == UserState.PENDING) {
                return true;
            }
        }
        return false;
    }


    public boolean isAnonymous () {
        LOGGER.debug ("isAnonymous() called.");
        Map<Faculty, UserState> map = user.getUserState ();
        if (map == null || map.isEmpty ()) {
            return true;
        }
        return false;
    }

    public List<UserState> getUserStates () {
        LOGGER.debug ("getUserStates() called.");
        if (user != null && user.getUserState () != null) {
            List<UserState> states = new ArrayList<> (user.getUserState ().values ());

            return states;
        } else {
            return new ArrayList<> ();
        }
    }

    public List<String> getUserFaculties () {
        LOGGER.debug ("getUserFaculties() called.");
        if (user != null && user.getUserState () != null) {
            List<Faculty> faculties = new ArrayList<> (user.getUserState ().keySet ());
            List<String> facultiesName = new ArrayList<> ();

            for (Faculty faculty : faculties) {
                facultiesName.add (faculty.getName ());

            }

            return facultiesName;
        } else {
            return new ArrayList<> ();
        }
    }


}