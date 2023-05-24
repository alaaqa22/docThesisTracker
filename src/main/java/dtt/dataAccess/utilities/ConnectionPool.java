package dtt.dataAccess.utilities;

import java.sql.Connection;
import java.util.List;

import java.sql.Connection;
import java.util.List;

/**
 * @author Stefan Witka
 *
 */
public class ConnectionPool {
	private List<Connection> connections;
	
	/**
	 * Get a connection from the connection pool.
	 * 
	 * @return The database connection
	 * @throws ConnectionPoolException if there is an error while getting a connection from the pool
	 */
	public synchronized Connection getConnection() {
		return null;
	}

	/**
	 * Release a connection back to the connection pool.
	 * 
	 * @param connection The connection to be released
	 * @throws ConnectionPoolException if there is an error while releasing the connection
	 */
	public void releaseConnection(Connection connection) {
		
	}

	/**
	 * Create a new connection and add it to the connection pool.
	 * 
	 * @throws ConnectionPoolException if there is an error while creating a connection
	 */
	public void createConnection() {
		
	}

	/**
	 * Get the singleton instance of the connection pool.
	 * 
	 * @return The connection pool instance
	 */
	public static ConnectionPool getInstance() {
		return null;	
	}
}
