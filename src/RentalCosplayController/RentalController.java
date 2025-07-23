package RentalCosplayController;

import rental.cosplay.DatabaseConnection;
import RentalCosplayModel.RentalModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RentalController {
    private final DatabaseConnection dbconnection;

    public RentalController() {
        dbconnection = new DatabaseConnection();
    }

    public boolean tambahData(RentalModel rental) {
        Connection connection = dbconnection.getConnection();
        boolean isAdded = false;

        String sql = "INSERT INTO rental (id_rental, nama, nomor_telp, alamat, durasi_pinjam VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            rental.setId(generateUniqueId(connection));
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getId());
            preparedStatement.setString(2, patient.getNama());
            preparedStatement.setString(3, patient.getTglLahir());
            preparedStatement.setString(4, patient.getNoTelpon());
            preparedStatement.setString(5, patient.getEmail());
            preparedStatement.setString(6, patient.getJenisKelamin());
            preparedStatement.setString(7, patient.getGolDarah());
            preparedStatement.setString(8, patient.getLayanan());

            isAdded = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAdded;
    }

    public boolean hapusPasien(String pasienId) {
        Connection connection = dbConnection.getConnection();
        boolean isDeleted = false;

        String sql = "DELETE FROM pasien WHERE id_pasien = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pasienId);
            isDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDeleted;
    }

    public List<PasienModel> getAllPatients() {
        List<PasienModel> patients = new ArrayList<>();
        Connection connection = dbConnection.getConnection();

        String sql = "SELECT * FROM pasien";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id_pasien");
                String nama = resultSet.getString("nama");
                String tglLahir = resultSet.getString("tgl_lahir");
                String noTelpon = resultSet.getString("no_telpon");
                String email = resultSet.getString("email");
                String jenisKelamin = resultSet.getString("jenis_kelamin");
                String golDarah = resultSet.getString("gol_darah");
                String layanan = resultSet.getString("layanan");
                patients.add(new PasienModel(id, nama, tglLahir, noTelpon, email, jenisKelamin, golDarah, layanan));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    public boolean updatePasien(PasienModel patient) {
        Connection connection = dbConnection.getConnection();
        boolean isUpdated = false;

        String sql = "UPDATE pasien SET nama = ?, tgl_lahir = ?, no_telpon = ?, email = ?, jenis_kelamin = ?, gol_darah = ?, layanan = ? WHERE id_pasien = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getNama());
            preparedStatement.setString(2, patient.getTglLahir());
            preparedStatement.setString(3, patient.getNoTelpon());
            preparedStatement.setString(4, patient.getEmail());
            preparedStatement.setString(5, patient.getJenisKelamin());
            preparedStatement.setString(6, patient.getGolDarah());
            preparedStatement.setString(7, patient.getLayanan());
            preparedStatement.setString(8, patient.getId());

            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }

    public Map<String, Integer> getJumlahPasienPerLayanan() {
        PasienController pasienController = new PasienController();
        List<PasienModel> pasienList = pasienController.getAllPatients();
        Map<String, Integer> jumlahPasienPerLayanan = new HashMap<>();

        for (PasienModel pasien : pasienList) {
            String layanan = pasien.getLayanan();
            jumlahPasienPerLayanan.put(layanan, jumlahPasienPerLayanan.getOrDefault(layanan, 0) + 1);
        }

        return jumlahPasienPerLayanan;
    }

    public String generateUniqueId(Connection connection) {
        String id;
        Random random = new Random();
        boolean unique = false;

        try {
            while (!unique) {
                int randomNum = 1000 + random.nextInt(9000);
                id = "PS-" + randomNum;
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM pasien WHERE id_pasien = ?");
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

}



