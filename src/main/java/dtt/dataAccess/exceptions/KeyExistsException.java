package dtt.dataAccess.exceptions;

/**
 *  Thrown when the repository tries to save data with a unique value that already exists in the data.
 *  @author Hadi Abou Hassoun
 */
public class KeyExistsException  extends Exception{

    /**
     * Constructs an exception.
     */
    public KeyExistsException(){
        super();
    }

    /**
     * Constructs an exception with a message.
     * @param message - Error massage.
     */
    public KeyExistsException(String message){
        super(message);
    }

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public KeyExistsException(String message , Throwable cause){
        super(message,cause);
    }

}


