package rental.cosplay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Handles user authentication and logout operations.
 * Manages database queries for user verification and session management.
 *
 * @author ASUS
 * @version 1.0
 */
public class AuthController {

    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());
    private static final String AUTHENTICATION_QUERY = "SELECT * FROM user WHERE username = ? AND password = ?";

    private final DatabaseConnection dbConnection;
    
    /**
     * Constructs a new AuthController with database connection.
     */
    public AuthController() {
        dbConnection = new DatabaseConnection();
    }
    
    /**
     * Authenticates a user based on provided credentials.
     *
     * @param user the user model containing username and password
     * @return authenticated UserModel if credentials are valid, null otherwise
     * @throws IllegalArgumentException if user is null
     */
    public UserModel authenticate(UserModel user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Connection connection = dbConnection.getConnection();
        UserModel authenticatedUser = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATION_QUERY)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    authenticatedUser = new UserModel(user.getUsername(), user.getPassword());
                    LOGGER.log(Level.INFO, "User authenticated successfully: {0}", user.getUsername());
                } else {
                    LOGGER.log(Level.WARNING, "Authentication failed for user: {0}", user.getUsername());
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during authentication", e);
        }

        return authenticatedUser;
    }
    
    /**
     * Logs out the current user and displays the login view.
     * Disposes the current view and creates a new login window.
     *
     * @param user the user to logout (not used in current implementation)
     * @param view the current view frame to close
     * @throws IllegalArgumentException if view is null
     */
    public void logout(UserModel user, JFrame view) {
        if (view == null) {
            throw new IllegalArgumentException("View cannot be null");
        }

        closeCurrentView(view);
        showLoginView();

        LOGGER.log(Level.INFO, "User logged out successfully");
    }

    /**
     * Closes and disposes the current view.
     *
     * @param view the view to close
     */
    private void closeCurrentView(JFrame view) {
        view.setVisible(false);
        view.dispose();
    }

    /**
     * Creates and displays a new login view.
     */
    private void showLoginView() {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);

    }
}
