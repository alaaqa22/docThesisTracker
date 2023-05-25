package dtt.business.utilities;

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;


/**
 * UncheckedExceptionHandlerFactory is a custom implementation for handling unchecked exceptions.
 * The UncheckedExceptionHandlerFactory creates an instance of UncheckedExceptionHandler,
 * which is responsible for handling unchecked exceptions that occur during JSF lifecycle.
 *
 * This custom implementation allows for specific handling of unchecked exceptions,
 * providing additional logging and error response customization as needed.
 * @author Johannes Silvennoinen
 */
public class UncheckedExceptionHandlerFactory extends ExceptionHandlerFactory {

    /**
     * Creates a new instance of UncheckedExceptionHandler that will be used for
     * handling unchecked exceptions in the JSF application.
     *
     * @return An instance of UncheckedExceptionHandler.
     */
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new UncheckedExceptionHandler();
    }
}
