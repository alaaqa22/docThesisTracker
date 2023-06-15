package dtt.dataAccess.utilities;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dtt.dataAccess.exceptions.DBConnectionFailedException;

/**
 * Class handling transactions on a Postgres Database.
 *
 * @author Stefan Witka
 *
 */
public class Transaction implements AutoCloseable {
    /** Connection on which the Transaction is run. */
    private Connection connection;
    /** Boolean to check if a successful commit took place. */
    private boolean didCommit;

    /** Initialize logger. */
    private final Logger logger = LogManager.getLogger();

    /**
     * Constructor for the transaction.
     */
    public Transaction() {
        didCommit = false;
        connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Error disabling AutoCommit", e);
            throw new DBConnectionFailedException(
                    "Error disabling AutoCommit", e);
        }
    }

    /**
     * Aborts the transaction and rolls back any changes made within the
     * transaction.
     */
    public void abort() {
        if (!didCommit) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Error aborting " + "the Transaction",
                        e);
                throw new DBConnectionFailedException(
                        "Error aborting " + "the Transaction", e);
            }
        }
    };

    /**
     * Commits the transaction,
     * persisting any changes made within the transaction.
     */
    public void commit() {
        if (!didCommit) {
            try {
                connection.commit();
            } catch (SQLException e) {
                logger.error("Error committing transaction", e);
                throw new DBConnectionFailedException(
                        "Error " + "committing transaction", e);
            }
            didCommit = true;
        }
    };

    /**
     * Retrieves the underlying connection associated with the transaction.
     *
     * @return The connection belonging to the Transaction.
     */
    public Connection getConnection() {
        return connection;
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (!didCommit) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Error during rollback", e);
                throw new DBConnectionFailedException(
                        "Rollback failed", e);
            }
            didCommit = true;
        }
        ConnectionPool.getInstance().releaseConnection(connection);
    }
}
