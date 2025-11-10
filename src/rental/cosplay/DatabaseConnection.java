package rental.cosplay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages database connections for the rental cosplay application.
 * Follows the Singleton pattern to ensure only one connection instance.
 */
public class DatabaseConnection implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/db_rental";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    private static final String CONNECTION_SUCCESS_MESSAGE = "Database connection established successfully";
    private static final String CONNECTION_FAILED_MESSAGE = "Failed to establish database connection";
    private static final String CONNECTION_CLOSED_MESSAGE = "Database connection closed successfully";
    private static final String CLOSE_FAILED_MESSAGE = "Failed to close database connection";

    private Connection connection;

    /**
     * Creates a new database connection.
     *
     * @throws RuntimeException if connection cannot be established
     */
    public DatabaseConnection() {
        try {
            this.connection = createConnection();
            LOGGER.log(Level.INFO, CONNECTION_SUCCESS_MESSAGE);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, CONNECTION_FAILED_MESSAGE, e);
            throw new RuntimeException(CONNECTION_FAILED_MESSAGE, e);
        }
    }

    /**
     * Creates and returns a new database connection.
     *
     * @return Connection object
     * @throws SQLException if connection fails
     */
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
    
    /**
     * Returns the current database connection.
     *
     * @return Connection object
     * @throws IllegalStateException if connection is null or closed
     */
    public Connection getConnection() {
        validateConnection();
        return connection;
    }

    /**
     * Validates that the connection is available and open.
     *
     * @throws IllegalStateException if connection is null or closed
     */
    private void validateConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("Database connection is not available");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking connection status", e);
            throw new IllegalStateException("Error validating database connection", e);
        }
    }

    /**
     * Checks if the connection is currently open.
     *
     * @return true if connection is open, false otherwise
     */
    public boolean isConnectionOpen() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error checking connection status", e);
            return false;
        }
    }

    /**
     * Closes the database connection.
     * This method is idempotent and can be called multiple times safely.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.log(Level.INFO, CONNECTION_CLOSED_MESSAGE);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, CLOSE_FAILED_MESSAGE, e);
            } finally {
                connection = null;
            }
        }
    }

    /**
     * Implements AutoCloseable for use with try-with-resources.
     */
    @Override
    public void close() {
        closeConnection();
    }
}

