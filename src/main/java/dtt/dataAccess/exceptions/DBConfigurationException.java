package dtt.dataAccess.exceptions;

public class DBConfigurationException extends RuntimeException{

    /**
     * Constructs an exception.
     */
    public DBConfigurationException (){}

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.
     */
    public DBConfigurationException (String message){}

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public DBConfigurationException (String message , Throwable cause){}


}
