package dtt.dataAccess.exceptions;

public class NoPermissionException extends Exception {

    /**
     * Constructs an exception.
     */
    public NoPermissionException(){}

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.
     */
    public NoPermissionException(String message){}

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public NoPermissionException(String message , Throwable cause){}

}
