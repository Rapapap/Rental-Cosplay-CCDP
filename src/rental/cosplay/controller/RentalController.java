package rental.cosplay.controller;

import rental.cosplay.model.RentalModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rental.cosplay.DatabaseConnection;

/**
 * Controller for managing rental operations.
 * Handles retrieval and management of rental transactions from the database.
 *
 * @author Hakim
 * @version 1.0
 */
public class RentalController {

    private static final Logger LOGGER = Logger.getLogger(RentalController.class.getName());
    private static final String SELECT_ALL_RENTALS_SQL = "SELECT * FROM rental";
    private static final String SELECT_RENTAL_BY_NAME_SQL = "SELECT * FROM rental WHERE nama = ?";

    private final DatabaseConnection dbConnection;

    /**
     * Constructs a new RentalController with database connection.
     */
    public RentalController() {
        dbConnection = new DatabaseConnection();
    }
    
    /**
     * Retrieves all rental records from the database.
     *
     * @return list of all rental records, empty list if none found
     */
    public List<RentalModel> getAllRental() {
        List<RentalModel> rentalList = new ArrayList<>();
        Connection connection = dbConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RENTALS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                RentalModel rental = createRentalFromResultSet(resultSet);
                rentalList.add(rental);
            }

            LOGGER.log(Level.INFO, "Retrieved {0} rental records from database", rentalList.size());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving rental records", e);
        }

        return rentalList;
    }

    /**
     * Retrieves a rental record by customer name.
     *
     * @param nama the customer name to search for
     * @return the rental model if found, null otherwise
     */
    public RentalModel getRentalNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to retrieve rental with invalid name");
            return null;
        }

        RentalModel model = null;

        try {
            Connection conn = dbConnection.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_RENTAL_BY_NAME_SQL)) {
                preparedStatement.setString(1, nama);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        model = new RentalModel();
                        model.setIdRental(rs.getString("id_rental"));
                        model.setNama(rs.getString("nama"));
                        model.setNomorTelp(rs.getString("nomor_telp"));
                        model.setAlamat(rs.getString("alamat"));
                        model.setIdKostum(rs.getString("id_kostum"));
                        model.setDurasiPinjam(rs.getInt("durasi_pinjam"));
                        model.setUkuran(rs.getString("ukuran_kostum"));

                        LOGGER.log(Level.INFO, "Rental found for customer: {0}", nama);
                    } else {
                        LOGGER.log(Level.INFO, "No rental found for customer: {0}", nama);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving rental by name", e);
        }

        return model;
    }

    /**
     * Creates a RentalModel object from a ResultSet.
     *
     * @param resultSet the ResultSet containing rental data
     * @return a new RentalModel object
     * @throws SQLException if database error occurs
     */
    private RentalModel createRentalFromResultSet(ResultSet resultSet) throws SQLException {
        String id_rental = resultSet.getString("id_rental");
        String nama = resultSet.getString("nama");
        String noTelp = resultSet.getString("nomor_telp");
        String alamat = resultSet.getString("alamat");
        int durasi = resultSet.getInt("durasi_pinjam");
        Date tglPinjam = resultSet.getDate("tgl_pinjam");
        Date tglKembali = resultSet.getDate("tgl_kembali");
        String status = resultSet.getString("status");
        int tBiaya = resultSet.getInt("total_biaya");
        String ukuranKostum = resultSet.getString("ukuran_kostum");

        return new RentalModel(id_rental, nama, noTelp, alamat, durasi, ukuranKostum,
                               tBiaya, String.valueOf(tglPinjam), String.valueOf(tglKembali), status);
    }
}
