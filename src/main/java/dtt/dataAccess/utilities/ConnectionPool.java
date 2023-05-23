package dtt.dataAccess.utilities;

import java.sql.Connection;
import java.util.List;

public class ConnectionPool {
	public static ConnectionPool instance;
	public List<Connection> connections;
	
	public Connection getConnection() {
		return null;
	}

	public void releaseConnection(Connection connection) {
		
	}

	public void createConnection() {
		
	}

	public static ConnectionPool getInstance() {
		return instance;	
	}

}
