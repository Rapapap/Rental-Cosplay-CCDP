package rental.cosplay;

/**
 * Represents a user in the rental cosplay system.
 * Contains user authentication credentials and profile information.
 *
 * @author ASUS
 * @version 1.0
 */
public class UserModel {

    private String username;
    private String password;
    private String name;

    /**
     * Constructs a new UserModel with specified credentials.
     *
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the display name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the display name of the user.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
