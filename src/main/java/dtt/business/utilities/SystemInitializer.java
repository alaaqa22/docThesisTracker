package dtt.business.utilities;

import dtt.dataAccess.utilities.ConnectionPool;
import dtt.global.utilities.ConfigReader;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The SystemInitializer is responsible for initializing and configuring the
 * system during server startup. It implements the ServletContextListener
 * interface and is annotated with @WebListener to register it as a listener
 * for servlet context lifecycle events.
 * @author Johannes Silvennoinen
 */
@WebListener
public class SystemInitializer implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(SystemInitializer.class);
    @Inject
    MaintenanceThread maintenanceThread;

    /**
     * This class is called during startup and performs the followings task:
     *  - Initializes the logger to ensure proper logging functionality.
     *  - Reads the configuration file to retrieve system settings and parameters.
     *  - Establishes a connection the database connection pool.
     *  - Starts a maintenance thread for system maintenance and monitoring.
     * @param arg The ServletContextEvent object that contains information about the ServletContext
     */
    @Override
    public void contextInitialized(ServletContextEvent arg) {

        LOGGER.info("-----Logger initialized-----");
        ConfigReader.loadProperties();
        LOGGER.info("Config read.");
        ConnectionPool.getInstance().initialize(Integer.parseInt(ConfigReader.getProperty(ConfigReader.DATABASE_SIZE)));
        LOGGER.info("Connection pool initialized.");
        maintenanceThread.startMaintenance();
    }

    /**
     * During system shutdown this method is used to perform the following tasks:
     *  - Closes the database connection by calling the closeConnections() method.
     *  - Stops the logger to ensure all pending log entries are written by calling the shutdown() method.
     *  - Ends the maintenance thread by calling the stopMaintenanceThread() method.
     * @param arg The ServletContextEvent object that contains information about the ServletContext
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg) {
        ConnectionPool.getInstance().shutdown();
        LOGGER.info("-----Logger shutting down-----");
        LogManager.shutdown();
        maintenanceThread.startMaintenance();
    }
}
