package dtt.dataAccess.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dtt.global.utilities.ConfigReader;
import dtt.dataAccess.exceptions.DBConnectionFailedException;

/**
 * Postgres Connection pool implementation as Singleton.
 *
 * @author Stefan Witka
 *
 */
public final class ConnectionPool {
    /** Static instance of the singleton. */
    private static ConnectionPool connectionPool;
    /** List of available connections. */
    private List<Connection> available;
    /** List of in use connections. */
    private List<Connection> busy;

    /** Driver of the Database. */
    private final String dbDriver;
    /** Host URL of the Database. */
    private final String dbHost;
    /** Name of the Database. */
    private final String dbName;
    /** Name of the User of the Database. */
    private final String dbUser;
    /** Password of the Database. */
    private final String dbPassword;
    /** Maximum Number of connections. */
    private final int cpMaxSize = 100;
    /** Target size of the connection pool. */
    private int cpInitialSize;

    /** Initialize logger. */
    private final Logger logger = LogManager.getLogger();

    /**
     * Private Constructor for singleton pattern.
     */
    private ConnectionPool() {
        dbDriver = ConfigReader
                .getProperty(ConfigReader.DATABASE_DRIVER);
        dbHost = ConfigReader.getProperty(ConfigReader.DATABASE_URL);
        dbName = ConfigReader
                .getProperty(ConfigReader.DATABASE_USER);
        dbUser = ConfigReader
                .getProperty(ConfigReader.DATABASE_USER);
        dbPassword = ConfigReader
                .getProperty(ConfigReader.DATABASE_PASSWORD);

        available = Collections.synchronizedList(new ArrayList<>());
        busy = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Initialize the connection pool with a given number of connections.
     *
     * @param initialConnections The initial number of connections to create
     * @throws DBConnectionFailedException if there is an error
     *  while creating the
     *                                     initial connections
     */
    public void initialize(final int initialConnections) {
        try {
            Class.forName(dbDriver);
            for (int i = 0; i < initialConnections; i++) {
                Connection connection = createConnection();
                available.add(connection);
            }
            cpInitialSize = initialConnections;
        } catch (ClassNotFoundException e) {
            throw new DBConnectionFailedException(
                    "JDBC Driver not found", e);
        }
    }

    /**
     * Get a connection from the connection pool.
     *
     * <p>
     * Returns a Connection from the list of available connections. If available
     * connections fall below TODO then additional temporary connections are
     * created, up to a maximum of TODO connections.
     *
     * @return The database connection
     * @throws DBConnectionFailedException if there is an error while getting a
     *                                     connection from the pool
     */
    public synchronized Connection getConnection() {
        if (available.isEmpty()) {
            if (available.size() + busy.size() < cpMaxSize) {
                try {
                    Connection connection = createConnection();
                    busy.add(connection);
                    return connection;
                } catch (DBConnectionFailedException e) {
                    System.err.println(
                            "Failed to create a new connection"
                            + " for the connection pool.");
                    e.printStackTrace();
                    throw e;
                }
            } else {
                throw new DBConnectionFailedException(
                        "Connection pool has reached its maximum capacity.");
            }
        } else {
            Connection connection = available.remove(0);
            busy.add(connection);
            return connection;
        }
    }

    /**
     * Release a connection back to the connection pool.
     *
     * <p>
     * Releases a connection and moves it back to the
     * list of available connections,
     * or removes them if they were temporary.
     *
     * @param connection The connection to be released
     * @throws DBConnectionFailedException if
     * there is an error while releasing the
     *                                     connection
     */
    public synchronized void releaseConnection(
            final Connection connection) {
        if (busy.remove(connection)) {
            if (busy.size() + available.size() < cpInitialSize) {
                available.add(connection);
            } else {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DBConnectionFailedException(
                            "Failed to close connection", e);
                }
            }
        } else {
            throw new DBConnectionFailedException(
                    "Failed to release the connection"
                    + " back to the connection pool.");
        }
    }

    /**
     * Create a new connection and add it to the connection pool.
     *
     * @return the created connection
     *
     * @throws DBConnectionFailedException if there is an error while creating a
     *                                     connection
     */
    private Connection createConnection() {
        // TODO Improve and test create conn
        Properties props = new Properties();
        props.setProperty("user", dbUser);
        props.setProperty("password", dbPassword);
        props.setProperty("ssl", "true");
        props.setProperty("sslfactory",
                "org.postgresql.ssl.DefaultJavaSSLFactory");
        try {
            Class.forName(dbDriver);
            String url = "jdbc:postgresql://" + dbHost + "/"
                    + dbName;
            Connection conn = DriverManager.getConnection(url, props);
            conn.setAutoCommit(false);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBConnectionFailedException(
                    "Failed to create a new database connection.", e);
        }

    }

    /**
     * Get the singleton instance of the connection pool.
     *
     * @return The connection pool instance
     */
    public static ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    /**
     * Closes all connections and clers the lists.
     */
    public void shutdown() {
        for (Connection connection : available) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Failed to close connection", e);
            }
        }
        available.clear();
        for (Connection connection : busy) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Failed to close connection", e);
            }
        }
        busy.clear();
    }

    /**
     * Get the amount of available connections.
     *
     * @return the amount of available connections
     */
    public int getAvailableConnectionsCount() {
        return available.size();
    }

    /**
     * Get the amount of busy connections.
     *
     * @return the amount of busy connections
     */
    public int getBusyConnectionsCount() {
        return busy.size();
    }

}
