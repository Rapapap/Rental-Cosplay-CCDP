package rental.cosplay.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import rental.cosplay.DatabaseConnection;
import rental.cosplay.model.KostumModel;

public class KostumController {
    
    private final DatabaseConnection dbConnection;

    public KostumController() {
        dbConnection = new DatabaseConnection();
    }

    public boolean tambahKostum(KostumModel kostum) {
        Connection connection = dbConnection.getConnection();
        boolean isAdded = false;

        String sql = "INSERT INTO kostum (id_kostum, nama, ukuran, harga, deskripsi) VALUES (?, ?, ?, ?, ?)";
        try {
            kostum.setId_kostum(generateUniqueId(connection));
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, kostum.getId_kostum());
            preparedStatement.setString(2, kostum.getNama());
            preparedStatement.setString(3, kostum.getUkuran());
            preparedStatement.setInt(4, kostum.getHarga());
            preparedStatement.setString(5, kostum.getDeskripsi());

            isAdded = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAdded;
    }
    
    public List<KostumModel> getAllKostum() {
        List<KostumModel> kostum = new ArrayList<>();
        Connection connection = dbConnection.getConnection();

        String sql = "SELECT * FROM kostum";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_kostum = resultSet.getString("id_kostum");
                String nama = resultSet.getString("nama");
                String ukuran = resultSet.getString("ukuran");
                int harga = resultSet.getInt("harga");
                String deskripsi = resultSet.getString("deskripsi");

                kostum.add(new KostumModel(id_kostum, nama, ukuran, harga, deskripsi));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kostum;
    }
    
    public boolean updateKostum(KostumModel kostum) {
        Connection connection = dbConnection.getConnection();
        boolean isUpdated = false;

        String sql = "UPDATE kostum SET nama = ?, ukuran = ?, harga = ?, deskripsi = ? WHERE id_kostum = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, kostum.getNama());
            preparedStatement.setString(2, kostum.getUkuran());
            preparedStatement.setInt(3, kostum.getHarga());
            preparedStatement.setString(4, kostum.getDeskripsi());
            preparedStatement.setString(5, kostum.getId_kostum());

            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }
    
    public boolean hapusKostum(String kostumId) {
        Connection connection = dbConnection.getConnection();
        boolean isDeleted = false;

        String sql = "DELETE FROM kostum WHERE id_kostum = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, kostumId);
            isDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDeleted;
    }
    
    public String generateUniqueId(Connection connection) {
        String id;
        Random random = new Random();
        boolean unique = false;

        try {
            while (!unique) {
                int randomNum = 1000 + random.nextInt(9000);
                id = "C-" + randomNum; //ID KOSTUM DIMULAI DARI C
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM kostum WHERE id_kostum = ?");
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    unique = true;
                    return id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
