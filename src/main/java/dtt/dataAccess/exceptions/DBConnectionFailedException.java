package dtt.dataAccess.exceptions;

public class DBConnectionFailedException extends RuntimeException {

    /**
     * Constructs an exception.
     */
    public DBConnectionFailedException(){}

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.
     */
    public DBConnectionFailedException(String message){}

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public DBConnectionFailedException(String message , Throwable cause){}
}
