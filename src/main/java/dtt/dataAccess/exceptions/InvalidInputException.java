package dtt.dataAccess.exceptions;

/**
 * Thrown when the input data is invalid or faulty, such as incorrect format, invalid values.
 */
public class InvalidInputException extends Exception {
    /**
     * Constructs an exception.
     */
    public InvalidInputException(){}

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.
     */
    public InvalidInputException(String message){}

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public InvalidInputException(String message , Throwable cause){}

}

