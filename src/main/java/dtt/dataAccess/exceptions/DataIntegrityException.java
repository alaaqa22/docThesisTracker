package dtt.dataAccess.exceptions;

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
