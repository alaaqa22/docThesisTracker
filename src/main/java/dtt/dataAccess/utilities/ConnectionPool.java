package dtt.dataAccess.utilities;

import java.sql.Connection;
import java.util.List;

import dtt.global.utilities.ConfigReader;
import dtt.dataAccess.exceptions.DBConnectionFailedException;

/**
 * Postgres Connection pool implementation as Singleton
 * 
 * @author Stefan Witka
 *
 */
public class ConnectionPool {
	private static final ConnectionPool connectionPool = new ConnectionPool();
	private List<Connection> available; //List of available connections
	private List<Connection> busy; //List of in use connections
	
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static String DB_HOST;
    private static String DB_NAME;
    private static String DB_USER;
    private static String DB_PASSWORD;

	/**
	 * Private Constructor for singleton pattern
	 */
	private ConnectionPool() {
		DB_HOST = ConfigReader.getProperty(ConfigReader.DATABASE_URL);
		DB_NAME = ConfigReader.getProperty(ConfigReader.DATABASE_USER);
		DB_USER = ConfigReader.getProperty(ConfigReader.DATABASE_USER);
		DB_PASSWORD = ConfigReader.getProperty(ConfigReader.DATABASE_PASSWORD);
	}
	
	/**
	 * Get a connection from the connection pool.
	 * 
	 * <p> Returns a Connection from the list of available connections. 
	 * If available connections fall below TODO then additional temporary connections are created, up to a maximum of TODO connections.
	 * 
	 * @return The database connection
	 * @throws DBConnectionFailedException if there is an error while getting a connection from the pool
	 */
	public synchronized Connection getConnection() throws DBConnectionFailedException {
		return null;
	}

	/**
	 * Release a connection back to the connection pool.
	 * 
	 * <p> Releases a connection and moves it back to the list of available connections, or removes them if they were temporary.
	 * 
	 * @param connection The connection to be released
	 * @throws DBConnectionFailedException if there is an error while releasing the connection
	 */
	public void releaseConnection(Connection connection) throws DBConnectionFailedException {
		
	}

	/**
	 * Create a new connection and add it to the connection pool.
	 * 
	 * @throws DBConnectionFailedException if there is an error while creating a connection
	 */
	public void createConnection() throws DBConnectionFailedException {
		
	}

	/**
	 * Get the singleton instance of the connection pool.
	 * 
	 * @return The connection pool instance
	 */
	public static ConnectionPool getInstance() {
		return connectionPool;	
	}
	
}
