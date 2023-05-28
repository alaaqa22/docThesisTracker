package dtt.business.backing;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Backing bean for the error page.
 * @author Alaa Qasem
 */

@RequestScoped
@Named
public class ErrorPageBacking {
    private String errorMessage;

    /**
     * Initialize the error message.
     */
    @PostConstruct
    public void init() {

    }

    /**
     * Return the error message.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set the error message.
     *
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
