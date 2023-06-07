package dtt.dataAccess.exceptions;

import java.io.IOException;

/**
 * Thrown when the input data is invalid or faulty, such as incorrect format, invalid values.
 * @author Hadi Abou Hassoun
 */
public class InvalidInputException extends IOException {
    /**
     * Constructs an exception.
     */
    public InvalidInputException(){
        super();
    }

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.
     */
    public InvalidInputException(String message){
        super(message);
    }

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public InvalidInputException(String message , Throwable cause){
        super(message, cause);
    }

}

