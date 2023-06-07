package dtt.business.backing;

import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Backing bean for the registration page.
 * @author Alaa Qasem
 */
@RequestScoped
@Named
public class RegistrationBacking {

    private User regUser;
    private UserDAO userDAO;


    /**
     * Initializes the data transfer object for the new registered user.
     */
    @PostConstruct
    public void init(){

    }

    /**
     * Register a new user, will send the verification link to the email
     * that the user has specified.
     *
     * @return Login page.
     */
    public String register(){
        return null;


    }

    public User getRegUser() {
        return regUser;
    }

    public void setRegUser(User regUser) {
        this.regUser = regUser;
    }




}
