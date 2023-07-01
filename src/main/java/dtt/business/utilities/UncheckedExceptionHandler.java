package dtt.business.utilities;

import jakarta.faces.FacesException;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

/**
 * Custom exception handler for handling unchecked exceptions that occur during the JSF lifecycle.
 * The UncheckedExceptionHandler provides a mechanism to handle unchecked exceptions in a specific way,
 * allowing for additional logging or error response customization as needed.
 * This implementation overrides the default JSF exception handling behavior for unchecked exceptions.
 * @author Johannes Silvennoinen
 */
public class UncheckedExceptionHandler extends ExceptionHandlerWrapper {
    private static final Logger LOGGER = LogManager.getLogger(UncheckedExceptionHandler.class);

    public String UIMessageGenerator(FacesContext facesContext) {
        return null;
    }

    /**
     * Placeholder method for generating a message. You can implement it to return a specific message if required.
     * @return
     */
    public String generateMessage()  {
        return null;
    }

    /**
     * Placeholder method for converting a category. You can implement it to perform any necessary category conversion logic if needed.
     */
    public void convertCategory() {

    }

    /**
     * Handles the given exception by logging it and performing any necessary error response customization.
     *
     * @throws FacesException If an error occurs while handling the exception.
     */
    @Override
    public void handle() throws FacesException {
        // Get the current FacesContext
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

        Iterable<ExceptionQueuedEvent> exceptionQueue = getUnhandledExceptionQueuedEvents();
        for (ExceptionQueuedEvent event : exceptionQueue) {
            Throwable exception = event.getContext().getException();

            LOGGER.error("Unchecked exception thrown: " +  exception.getMessage());
            try {
                redirectToErrorPage(facesContext);
            } catch (IOException e) {
                LOGGER.error("An IOException occurred in handle(): " + e.getMessage());
            }
        }
    }

    private void redirectToErrorPage(FacesContext facesContext) throws IOException {
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        externalContext.redirect(externalContext.getRequestContextPath() + "/views/anonymous/errorPage.xhtml");
    }
    @Override
    public Throwable getRootCause(Throwable throwable) {
        return throwable.getCause();
    }

    public UncheckedExceptionHandler(ExceptionHandler handler) {
        super(handler);
    }
}
