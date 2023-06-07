package dtt.dataAccess.exceptions;

/**
 * Thrown when there is an error or failure in reading a Configuration File.
 * @author Hadi Abou Hassoun
 */
public class ConfigurationReadException extends RuntimeException {
    /**
     * Construct an exception
     *
     */
    public ConfigurationReadException(){
        super();
    }

    /**
     * Construct an exception with a message
     * @param message - Error massage.
     */
    public ConfigurationReadException(String message){
        super(message);
    }

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public ConfigurationReadException (String message , Throwable cause){
        super(message, cause);
    }

}
