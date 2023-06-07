package dtt.dataAccess.exceptions;

/**
 * thrown when an operation or action violates the data integrity rules defined for a system.
 * It serves as a way to signal that the integrity of the data has been compromised or that an inconsistency has occurred.
 * @author Hadi Abou Hassoun
 */
public class DataIntegrityException extends Exception  {

    /**
     * Construct an exception
     *
     */
    public DataIntegrityException(){
        super();
    }

    /**
     * Construct an exception with a message
     * @param message - Error massage.
     */
    public DataIntegrityException(String message){
        super(message);
    }

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public DataIntegrityException(String message , Throwable cause){
        super(message, cause);
    }


}
