package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Backing bean for the login page.
 *
 * @author Alaa Aasem
 */
@RequestScoped
@Named
public class LoginBacking {
    @Inject
    private SessionInfo sessionInfo;
    private UserDAO userDAO;
    private  User user;
    private String email;
    private String password;


    /**
     * Initializes the login backing bean.
     */
    @PostConstruct
    public void init(){

    }

    /**
     * Check the entered email and password and either show an error message or go
     * to circulationList page.
     *
     * @return Go to circulation-Details page on success or stay
     * in the same page on failure.
     */
    private String login(){

        return null;
    }

    /**
     * Go to register-page.
     *
     * @return Go to register-page.
     */
    private String register(){
        return "/view/register.xhtml";
    }

    /**
     * Go to forget-password page.
     *
     * @return Go to forget-password page.
     */
    private String forgetPass(){
        return "/view/forgetPass.xhtml";
    }


    public User getUser() {
        return user;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}
