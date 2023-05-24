package dtt.dataAccess.utilities;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Stefan Witka
 *
 */
public class Transaction implements AutoCloseable {
	private Connection connection;
	
	/**
	 * Aborts the transaction and rolls back any changes made within the transaction.
	 * 
	 * @throws SQLException if there is an error aborting the transaction
	 */
	public void abort() throws SQLException {
		// TODO: Implement abort logic
	};
	
	/**
	 * Commits the transaction, persisting any changes made within the transaction.
	 * 
	 * @throws SQLException if there is an error committing the transaction
	 */
	public void commit() throws SQLException {
		// TODO: Implement commit logic
	};
	
	/**
	 * Retrieves the underlying connection associated with the transaction.
	 * 
	 * @return The connection associated with the transaction.
	 */
	public Connection getConnection() {
		// TODO: Implement getConnection logic
		return null;
	};
	
	/**
	 * Closes the transaction and releases any associated resources.
	 * 
	 * @throws SQLException if there is an error closing the transaction
	 */
	@Override
	public void close() throws SQLException {
		// TODO: Implement close logic
	}
}
