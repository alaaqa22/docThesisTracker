package dtt.dataAccess.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import dtt.global.utilities.ConfigReader;
import dtt.dataAccess.exceptions.ConfigurationReadException;
import dtt.dataAccess.exceptions.DBConnectionFailedException;

/**
 * Postgres Connection pool implementation as Singleton
 * 
 * @author Stefan Witka
 *
 */
public class ConnectionPool {
	private static ConnectionPool connectionPool;
	private List<Connection> available; // List of available connections
	private List<Connection> busy; // List of in use connections

	private static String DB_DRIVER;
	private static String DB_HOST;
	private static String DB_NAME;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static int DB_INITIAL_SIZE;
	private static int DB_MAX_SIZE;

	/**
	 * Private Constructor for singleton pattern
	 */
	private ConnectionPool() {
		DB_DRIVER = ConfigReader.getProperty(ConfigReader.DATABASE_DRIVER);
		DB_HOST = ConfigReader.getProperty(ConfigReader.DATABASE_URL);
		DB_NAME = ConfigReader.getProperty(ConfigReader.DATABASE_USER);
		DB_USER = ConfigReader.getProperty(ConfigReader.DATABASE_USER);
		DB_PASSWORD = ConfigReader.getProperty(ConfigReader.DATABASE_PASSWORD);
		DB_MAX_SIZE = 100;

//		try {
//			DB_MAX_SIZE = Integer.parseInt(ConfigReader.getProperty(ConfigReader.DATABASE_MAX_SIZE));
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			throw new ConfigurationReadException("DATABASE_SIZE not a readable integer", e);
//		}

		available = Collections.synchronizedList(new ArrayList<>());
		busy = Collections.synchronizedList(new ArrayList<>());
	}

	/**
	 * Initialize the connection pool with a given number of connections.
	 * 
	 * @param initialConnections The initial number of connections to create
	 * @throws DBConnectionFailedException if there is an error while creating the
	 *                                     initial connections
	 */
	public void initialize(int initialConnections) throws DBConnectionFailedException {
		try {
			Class.forName(DB_DRIVER);
			for (int i = 0; i < initialConnections; i++) {
				Connection connection = createConnection();
				available.add(connection);
			}
			DB_INITIAL_SIZE = initialConnections;
		} catch (ClassNotFoundException e) {
			throw new DBConnectionFailedException("JDBC Driver not found", e);
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
	public synchronized Connection getConnection() throws DBConnectionFailedException {
		if (available.isEmpty()) {
			if (available.size() + busy.size() < DB_MAX_SIZE) {
				try {
					Connection connection = createConnection();
					busy.add(connection);
					return connection;
				} catch (DBConnectionFailedException e) {
					System.err.println("Failed to create a new connection for the connection pool.");
					e.printStackTrace();
					throw e;
				}
			} else {
				throw new DBConnectionFailedException("Connection pool has reached its maximum capacity.");
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
	 * Releases a connection and moves it back to the list of available connections,
	 * or removes them if they were temporary.
	 * 
	 * @param connection The connection to be released
	 * @throws DBConnectionFailedException if there is an error while releasing the
	 *                                     connection
	 */
	public synchronized void releaseConnection(Connection connection) throws DBConnectionFailedException {
		if (busy.remove(connection)) {
			if (busy.size() + available.size() < DB_INITIAL_SIZE) {
				available.add(connection);
			} else {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DBConnectionFailedException("Failed to close connection", e);
				}
			}
		} else {
			throw new DBConnectionFailedException("Failed to release the connection back to the connection pool.");
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
	private Connection createConnection() throws DBConnectionFailedException {
		// TODO Improve and test create conn
		Properties props = new Properties();
		props.setProperty("user", DB_USER);
		props.setProperty("password", DB_PASSWORD);
		props.setProperty("ssl", "true");
		props.setProperty("sslfactory", "org.postgresql.ssl.DefaultJavaSSLFactory");
		try {
			Class.forName(DB_DRIVER);
			String url = "jdbc:postgresql://" + DB_HOST + "/" + DB_NAME;
			Connection conn = DriverManager.getConnection(url, props);
			conn.setAutoCommit(false);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			throw new DBConnectionFailedException("Failed to create a new database connection.", e);
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

	public void shutdown() throws DBConnectionFailedException {
		for (Connection connection : available) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DBConnectionFailedException("Failed to close connection", e);
			}
		}
		available.clear();
		for (Connection connection : busy) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DBConnectionFailedException("Failed to close connection", e);
			}
		}
		busy.clear();
	}

	/**
	 * Get the amount of available connections
	 * 
	 * @return the amount of available connections
	 */
	public int getAvailableConnectionsCount() {
		return available.size();
	}

	/**
	 * Get the amount of busy connections
	 * 
	 * @return the amount of busy connections
	 */
	public int getBusyConnectionsCount() {
		return busy.size();
	}

}
