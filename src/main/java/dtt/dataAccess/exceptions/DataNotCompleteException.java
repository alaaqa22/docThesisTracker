package dtt.dataAccess.exceptions;

/**
 *  Thrown when Data is incomplete or missing, preventing a specific operation from being executed successfully.
 *  @author Hadi Abou Hassoun
 */
public class DataNotCompleteException extends Exception {

    /**
     * Construct an exception
     *
     */
    public DataNotCompleteException(){
        super();
    }

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.
     */
    public DataNotCompleteException(String message){
        super(message);
    }

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public DataNotCompleteException(String message , Throwable cause){
        super(message, cause);
    }

}
