package dtt.dataAccess.exceptions;

/**
 * thrown when an operation or action violates the data integrity rules defined for a system.
 * It serves as a way to signal that the integrity of the data has been compromised or that an inconsistency has occurred.
 */
public class DataIntegrityException extends RuntimeException {

    /**
     * Construct an exception
     *
     */
    public DataIntegrityException(){}

    /**
     * Construct an exception with a message
     * @param message - Error massage.
     */
    public DataIntegrityException(String message){}

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public DataIntegrityException(String message , Throwable cause){}


}
