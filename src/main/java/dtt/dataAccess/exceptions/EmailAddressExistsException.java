package dtt.dataAccess.exceptions;

/**
 * Thrown when attempting to create or register a user with an email address that already exists in the system.
 */
public class EmailAddressExistsException extends Exception {

    /**
     * Constructs an exception.

     */
    public EmailAddressExistsException (){}

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.

     */
    public EmailAddressExistsException (String message){}

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public EmailAddressExistsException (String message , Throwable cause){}
}
