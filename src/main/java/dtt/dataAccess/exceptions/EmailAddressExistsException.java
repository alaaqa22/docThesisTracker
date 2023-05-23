package dtt.dataAccess.exceptions;

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
