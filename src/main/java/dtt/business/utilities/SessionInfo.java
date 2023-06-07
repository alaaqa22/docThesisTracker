package dtt.business.utilities;

import dtt.global.tansport.User;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;

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
@SessionScoped
public class SessionInfo implements Serializable {

    private static final long serialVersionUID = 10;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Checks if the user is an Admin.
     * @return true if the user is an Admin.
     */
    public boolean isAdmin() {
        //Assuming we're still using magic numbers.
        return user.getRoleId() == 5;
    }
}
