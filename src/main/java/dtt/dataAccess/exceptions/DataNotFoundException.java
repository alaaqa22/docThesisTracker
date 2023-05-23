package dtt.dataAccess.exceptions;

public class DataNotFoundException extends Exception {

    /**
     * Constructs an exception.
     */
   public DataNotFoundException(){}

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.
     */
    public DataNotFoundException(String message){}

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public DataNotFoundException(String message , Throwable cause){}

}
