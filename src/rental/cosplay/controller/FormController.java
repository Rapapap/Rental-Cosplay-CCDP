package rental.cosplay.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import rental.cosplay.DatabaseConnection;
import java.util.Random;
import java.sql.ResultSet;
import java.sql.Date; 
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import rental.cosplay.model.RentalModel;

/**
 * Controller for managing rental form operations.
 * Handles CRUD operations for rental transactions in the database.
 *
 * @author Hakim
 * @version 1.0
 */
public class FormController {

    private static final Logger LOGGER = Logger.getLogger(FormController.class.getName());
    private static final String RENTAL_ID_PREFIX = "UR-";
    private static final int ID_MIN_RANGE = 1000;
    private static final int ID_MAX_RANGE = 9000;
    private static final String STATUS_NOT_RETURNED = "Belum Kembali";
    private static final String STATUS_RETURNED = "Sudah Kembali";

    private static final String INSERT_RENTAL_SQL =
        "INSERT INTO rental (id_rental, nama, nomor_telp, alamat, id_kostum, durasi_pinjam, " +
        "tgl_pinjam, tgl_kembali, status, total_biaya, ukuran_kostum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CHECK_ID_EXISTS_SQL = "SELECT COUNT(*) FROM rental WHERE id_rental = ?";
    private static final String DELETE_RENTAL_SQL = "DELETE FROM rental WHERE id_rental = ?";
    private static final String UPDATE_RENTAL_SQL =
        "UPDATE rental SET nama = ?, nomor_telp = ?, alamat = ?, id_kostum = ?, durasi_pinjam = ?, " +
        "tgl_pinjam = ?, tgl_kembali = ?, status = ?, total_biaya = ?, ukuran_kostum = ? WHERE id_rental = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM rental WHERE id_rental = ?";

    private final DatabaseConnection dbConnection;

    /**
     * Constructs a new FormController with database connection.
     */
    public FormController() {
        dbConnection = new DatabaseConnection();
    }
    
    /**
     * Generates a unique rental ID.
     * Format: UR-XXXX where XXXX is a random 4-digit number.
     *
     * @param connection the database connection
     * @return a unique rental ID, or null if generation fails
     */
    public String genereteUniqueId(Connection connection) {
        Random random = new Random();

        try {
            while (true) {
                int randomNum = ID_MIN_RANGE + random.nextInt(ID_MAX_RANGE);
                String id_r = RENTAL_ID_PREFIX + randomNum;

                if (!isIdExists(connection, id_r)) {
                    LOGGER.log(Level.FINE, "Generated unique rental ID: {0}", id_r);
                    return id_r;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error generating unique rental ID", e);
        }

        return null;
    }
    
    /**
     * Checks if a rental ID already exists in the database.
     *
     * @param connection the database connection
     * @param id the rental ID to check
     * @return true if the ID exists, false otherwise
     * @throws SQLException if database error occurs
     */
    private boolean isIdExists(Connection connection, String id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ID_EXISTS_SQL)) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    /**
     * Adds a new rental transaction to the database.
     * Automatically generates unique ID, sets rental date to today, and calculates total cost.
     *
     * @param form the rental model containing transaction details
     * @return true if the rental was added successfully, false otherwise
     */
    public boolean tambahUser(RentalModel form) {
        if (form == null) {
            LOGGER.log(Level.WARNING, "Attempted to add null rental form");
            return false;
        }

        Connection connection = dbConnection.getConnection();
        boolean isAdded = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RENTAL_SQL)) {
            String idBaru = genereteUniqueId(connection);
            LocalDate tglPinjam = LocalDate.now();
            Date sqlTglPinjam = Date.valueOf(tglPinjam);
            int totalBiaya = calculateTotalCost(form.getDurasiPinjam(), form.getHarga());

            setRentalInsertParameters(preparedStatement, idBaru, form, sqlTglPinjam, totalBiaya);

            int rowInserted = preparedStatement.executeUpdate();
            isAdded = rowInserted > 0;

            if (isAdded) {
                LOGGER.log(Level.INFO, "Rental added successfully: {0}", idBaru);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding rental", e);
        }

        return isAdded;
    }

    /**
     * Sets parameters for rental insertion prepared statement.
     *
     * @param preparedStatement the prepared statement
     * @param idBaru the new rental ID
     * @param form the rental form data
     * @param sqlTglPinjam the rental start date
     * @param totalBiaya the total cost
     * @throws SQLException if parameter setting fails
     */
    private void setRentalInsertParameters(PreparedStatement preparedStatement, String idBaru,
                                          RentalModel form, Date sqlTglPinjam, int totalBiaya)
            throws SQLException {
        preparedStatement.setString(1, idBaru);
        preparedStatement.setString(2, form.getNama());
        preparedStatement.setString(3, form.getNomorTelp());
        preparedStatement.setString(4, form.getAlamat());
        preparedStatement.setString(5, form.getIdKostum());
        preparedStatement.setInt(6, form.getDurasiPinjam());
        preparedStatement.setDate(7, sqlTglPinjam);
        preparedStatement.setDate(8, null);
        preparedStatement.setString(9, STATUS_NOT_RETURNED);
        preparedStatement.setInt(10, totalBiaya);
        preparedStatement.setString(11, form.getUkuran());
    }

    /**
     * Calculates the total rental cost.
     *
     * @param duration rental duration in days
     * @param pricePerDay price per day
     * @return total cost
     */
    private int calculateTotalCost(int duration, int pricePerDay) {
        return duration * pricePerDay;
    }
    
    /**
     * Checks if a rental record exists in the database.
     *
     * @param idRental the rental ID to check
     * @return true if the rental exists, false otherwise
     */
    public boolean cekData(String idRental) {
        if (idRental == null || idRental.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to check rental with invalid ID");
            return false;
        }

        boolean ada = false;
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CHECK_ID_EXISTS_SQL)) {

            preparedStatement.setString(1, idRental);
            try (ResultSet result = preparedStatement.executeQuery()) {
                ada = result.next() && result.getInt(1) > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking rental data", e);
        }

        return ada;
    }
    
    /**
     * Deletes a rental record from the database.
     *
     * @param idRental the ID of the rental to delete
     * @return true if the rental was deleted successfully, false otherwise
     */
    public boolean hapusData(String idRental) {
        if (idRental == null || idRental.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to delete rental with invalid ID");
            return false;
        }

        Connection conn = dbConnection.getConnection();
        boolean isDeleted = false;
        
        try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_RENTAL_SQL)) {
            preparedStatement.setString(1, idRental);
            
            int rowAffected = preparedStatement.executeUpdate();
            isDeleted = rowAffected > 0;
            
            if (isDeleted) {
                LOGGER.log(Level.INFO, "Rental deleted successfully: {0}", idRental);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting rental", e);
        }
        
        return isDeleted;
    }
    
    /**
     * Updates an existing rental record in the database.
     *
     * @param form the rental model with updated information
     * @return true if the rental was updated successfully, false otherwise
     */
    public boolean UpdateData(RentalModel form) {
        if (form == null) {
            LOGGER.log(Level.WARNING, "Attempted to update null rental form");
            return false;
        }

        Connection conn = dbConnection.getConnection();
        boolean isUpdate = false;
        
        LocalDate tglPinjam = LocalDate.now();
        int durasi = form.getDurasiPinjam();
        LocalDate tglKembali = calculateReturnDate(form);

        Date sqlTglPinjam = Date.valueOf(tglPinjam);
        Date sqlTglKembali = tglKembali == null ? null : Date.valueOf(tglKembali);

        int totalBiaya = calculateTotalCost(durasi, form.getHarga());

        try (PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_RENTAL_SQL)) {
            setRentalUpdateParameters(preparedStatement, form, sqlTglPinjam, sqlTglKembali, totalBiaya);

            isUpdate = preparedStatement.executeUpdate() > 0;

            if (isUpdate) {
                LOGGER.log(Level.INFO, "Rental updated successfully: {0}", form.getIdRental());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating rental", e);
        }

        return isUpdate;
    }

    /**
     * Calculates the return date based on rental status.
     *
     * @param form the rental form
     * @return the return date, or null if not yet returned
     */
    private LocalDate calculateReturnDate(RentalModel form) {
        if (STATUS_RETURNED.equals(form.getStatus())) {
            return LocalDate.now();
        } else if (form.getTglKembali() != null) {
            return LocalDate.parse(form.getTglKembali());
        }
        return null;
    }

    /**
     * Sets parameters for rental update prepared statement.
     *
     * @param preparedStatement the prepared statement
     * @param form the rental form data
     * @param sqlTglPinjam the rental start date
     * @param sqlTglKembali the return date
     * @param totalBiaya the total cost
     * @throws SQLException if parameter setting fails
     */
    private void setRentalUpdateParameters(PreparedStatement preparedStatement, RentalModel form,
                                          Date sqlTglPinjam, Date sqlTglKembali, int totalBiaya)
            throws SQLException {
        preparedStatement.setString(1, form.getNama());
        preparedStatement.setString(2, form.getNomorTelp());
        preparedStatement.setString(3, form.getAlamat());
        preparedStatement.setString(4, form.getIdKostum());
        preparedStatement.setInt(5, form.getDurasiPinjam());
        preparedStatement.setDate(6, sqlTglPinjam);
        preparedStatement.setDate(7, sqlTglKembali);
        preparedStatement.setString(8, form.getStatus());
        preparedStatement.setInt(9, totalBiaya);
        preparedStatement.setString(10, form.getUkuran());
        preparedStatement.setString(11, form.getIdRental());
    }
    
    /**
     * Retrieves a rental record by its ID.
     *
     * @param idRental the rental ID to search for
     * @return the rental model if found, null otherwise
     */
    public RentalModel getDataById(String idRental) {
        if (idRental == null || idRental.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to retrieve rental with invalid ID");
            return null;
        }

        RentalModel rental = null;

        try {
            Connection conn = dbConnection.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_BY_ID_SQL)) {
                preparedStatement.setString(1, idRental);

                try (ResultSet result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        rental = createRentalFromResultSet(result);
                        LOGGER.log(Level.INFO, "Rental found: {0}", idRental);
                    } else {
                        LOGGER.log(Level.INFO, "No rental found with ID: {0}", idRental);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving rental by ID", e);
        }

        return rental;
    }

    /**
     * Creates a RentalModel object from a ResultSet.
     *
     * @param result the ResultSet containing rental data
     * @return a new RentalModel object
     * @throws SQLException if database error occurs
     */
    private RentalModel createRentalFromResultSet(ResultSet result) throws SQLException {
        RentalModel rental = new RentalModel();
        rental.setIdRental(result.getString("id_rental"));
        rental.setNama(result.getString("nama"));
        rental.setNomorTelp(result.getString("nomor_telp"));
        rental.setAlamat(result.getString("alamat"));
        rental.setIdKostum(result.getString("id_kostum"));
        rental.setUkuran(result.getString("ukuran_kostum"));
        rental.setDurasiPinjam(result.getInt("durasi_pinjam"));
        rental.setStatus(result.getString("status"));
        return rental;
    }
}
