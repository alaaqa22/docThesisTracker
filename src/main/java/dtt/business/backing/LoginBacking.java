package dtt.business.backing;

import jakarta.annotation.PostConstruct;

/**
 * Backing bean for the login page.
 */
public class LoginBacking {

    /**
     *
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
