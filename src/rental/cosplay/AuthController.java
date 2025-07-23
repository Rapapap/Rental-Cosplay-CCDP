package rental.cosplay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;

public class AuthController {
    private final DatabaseConnection dbConnection;
    
    public AuthController() {
        dbConnection = new DatabaseConnection();
    }
    
    public UserModel authenticate(UserModel user) {
        Connection connection = dbConnection.getConnection();
        UserModel authenticatedUser = null;

        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
//                String name = resultSet.getString("nama");  // Ambil kolom nama
                authenticatedUser = new UserModel(user.getUsername(), user.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authenticatedUser;
    }
    
    public void logout(UserModel user, JFrame view){
        user = null;
        
        view.setVisible(false);
        view.dispose();
        view.revalidate();
        
        LoginView loginView = new LoginView();
        loginView.setVisible(true);

    }
}
