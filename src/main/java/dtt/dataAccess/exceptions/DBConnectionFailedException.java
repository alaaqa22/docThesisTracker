package dtt.dataAccess.exceptions;

/**
 * Thrown when there is a failure to establish a connection to a database.
 * @author Hadi Abou Hassoun
 */
public class DBConnectionFailedException extends RuntimeException {

    /**
     * Constructs an exception.
     */
    public DBConnectionFailedException(){
        super();
    }

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.
     */
    public DBConnectionFailedException(String message){
        super(message);
    }

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public DBConnectionFailedException(String message , Throwable cause){
        super(message, cause);
    }
}
