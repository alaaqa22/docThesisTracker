package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * Backing bean for the login page.
 *
 * @author Alaa Aasem
 */
public class LoginBacking {
    @Inject
    private SessionInfo sessionInfo;
    private UserDAO userDAO;

    /**
     * Initializes the login backing bean.
     */
    @PostConstruct
    public void init(){

    }

    /**
     * Check the entered email and password and either show an error message or go
     * to circulation-Details page.
     *
     * @return Go to circulation-Details page on success or stay
     * in the same page on failure.
     */
    private String login(){
        return null;
    }
}
