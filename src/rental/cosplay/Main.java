package rental.cosplay;

/**
 * Main entry point for the Rental Cosplay application.
 * Launches the login view on application startup.
 *
 * @author Rental Cosplay Team
 */
public class Main {

    /**
     * Main method to start the application.
     *
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        new LoginView().setVisible(true);
    }
    
}
