package dtt.dataAccess.exceptions;

/**
 * Thrown when a query or operation on a data source fails
 */
public class DatasourceQueryFailedException extends RuntimeException {
    /**
     * Construct an exception
     *
     */
    public DatasourceQueryFailedException(){}

    /**
     * Construct an exception with a message
     * @param message - Error massage.
     */
    public DatasourceQueryFailedException(String message){}

    /**
     * Constructs an exception with a message and a cause.
     * @param message - Error massage.
     * @param cause - Cause of the exception.
     */
    public DatasourceQueryFailedException (String message , Throwable cause){}





}
