package dtt.business.utilities;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


/**
 * The SystemInitializer is responsible for initializing and configuring the
 * system during server startup. It implements the ServletContextListener
 * interface and is annotated with @WebListener to register it as a listener
 * for servlet context lifecycle events.
 * @author Johannes Silvennoinen
 */
@WebListener
public class SystemInitializer implements ServletContextListener {
    /**
     * This class is called during startup and performs the followings task:
     *  - Initializes the logger to ensure proper logging functionality.
     *  - Reads the configuration file to retrieve system settings and parameters.
     *  - Establishes a connection to the database using the Connection Pool.
     *  - Starts a maintenance thread for system maintenance and monitoring.
     * @param arg The ServletContextEvent object that contains information about the ServletContext
     */
    @Override
    public void contextInitialized(ServletContextEvent arg) {

    }

    /**
     * During system shutdown this method is used to perform the following tasks:
     *  - Closes the database connection by calling the closeConnections() method.
     *  - Stops the logger to ensure all pending log entries are written by calling the stopLogger() method.
     *  - Ends the maintenance thread by calling the stopMaintenanceThread() method.
     * @param arg
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg) {

    }
}
