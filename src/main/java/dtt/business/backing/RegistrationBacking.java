package dtt.business.backing;

import jakarta.annotation.PostConstruct;

/**
 * Backing bean for the registration page.
 */
public class RegistrationBacking {

    /**
     *  Initializes the data transfer object for the new registered user.
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

}
