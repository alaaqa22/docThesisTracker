package dtt.dataAccess.utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dtt.dataAccess.exceptions.DBConnectionFailedException;
import dtt.global.utilities.ConfigReader;

class ConnectionPoolTest {
    private ConnectionPool connectionPool;

    @BeforeEach
    public void setUp() {
    	ConfigReader.loadProperties();
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initialize(2); // Initialize the connection pool with 2 connections
    }

    @AfterEach
    public void tearDown() {
        connectionPool.shutdown(); // Shutdown the connection pool after the test
    }

    @Test
    public void testGetConnection() throws DBConnectionFailedException {
        Connection connection1 = connectionPool.getConnection();
        Connection connection2 = connectionPool.getConnection();

        assertNotNull(connection1, "Connection 1 should not be null");
        assertNotNull(connection2, "Connection 2 should not be null");
        assertEquals(0, connectionPool.getAvailableConnectionsCount(), "Number of available connections after getting two connections");
        assertEquals(2, connectionPool.getBusyConnectionsCount(), "Number of busy connections after getting two connections");
    }

    @Test
    public void testReleaseConnection() throws DBConnectionFailedException {
        Connection connection1 = connectionPool.getConnection();
        Connection connection2 = connectionPool.getConnection();

        connectionPool.releaseConnection(connection1);

        assertEquals(1, connectionPool.getAvailableConnectionsCount(), "Number of available connections after releasing one connection");
        assertEquals(1, connectionPool.getBusyConnectionsCount(), "Number of busy connections after releasing one connection");

        connectionPool.releaseConnection(connection2);

        assertEquals(2, connectionPool.getAvailableConnectionsCount(), "Number of available connections after releasing two connections");
        assertEquals(0, connectionPool.getBusyConnectionsCount(), "Number of busy connections after releasing two connections");
    }
}