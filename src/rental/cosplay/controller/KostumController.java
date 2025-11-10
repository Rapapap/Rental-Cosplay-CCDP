package rental.cosplay.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import rental.cosplay.DatabaseConnection;
import rental.cosplay.model.KostumModel;

/**
 * Controller for managing costume (kostum) operations.
 * Handles CRUD operations for costumes in the database.
 *
 * @author ASUS
 * @version 1.0
 */
public class KostumController {
    
    private static final Logger LOGGER = Logger.getLogger(KostumController.class.getName());
    private static final String COSTUME_ID_PREFIX = "C-";
    private static final int ID_MIN_RANGE = 1000;
    private static final int ID_MAX_RANGE = 9000;

    private static final String INSERT_COSTUME_SQL =
        "INSERT INTO kostum (id_kostum, nama, ukuran, harga, deskripsi) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_COSTUMES_SQL = "SELECT * FROM kostum";
    private static final String UPDATE_COSTUME_SQL =
        "UPDATE kostum SET nama = ?, ukuran = ?, harga = ?, deskripsi = ? WHERE id_kostum = ?";
    private static final String DELETE_COSTUME_SQL = "DELETE FROM kostum WHERE id_kostum = ?";
    private static final String CHECK_ID_EXISTS_SQL = "SELECT COUNT(*) FROM kostum WHERE id_kostum = ?";

    private final DatabaseConnection dbConnection;

    /**
     * Constructs a new KostumController with database connection.
     */
    public KostumController() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Adds a new costume to the database.
     * Automatically generates a unique costume ID.
     *
     * @param kostum the costume model to add
     * @return true if the costume was added successfully, false otherwise
     */
    public boolean tambahKostum(KostumModel kostum) {
        if (kostum == null) {
            LOGGER.log(Level.WARNING, "Attempted to add null costume");
            return false;
        }

        Connection connection = dbConnection.getConnection();
        boolean isAdded = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COSTUME_SQL)) {
            kostum.setId_kostum(generateUniqueId(connection));
            preparedStatement.setString(1, kostum.getId_kostum());
            preparedStatement.setString(2, kostum.getNama());
            preparedStatement.setString(3, kostum.getUkuran());
            preparedStatement.setInt(4, kostum.getHarga());
            preparedStatement.setString(5, kostum.getDeskripsi());

            isAdded = preparedStatement.executeUpdate() > 0;

            if (isAdded) {
                LOGGER.log(Level.INFO, "Costume added successfully: {0}", kostum.getId_kostum());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding costume", e);
        }

        return isAdded;
    }
    
    /**
     * Retrieves all costumes from the database.
     *
     * @return list of all costumes, empty list if none found
     */
    public List<KostumModel> getAllKostum() {
        List<KostumModel> kostumList = new ArrayList<>();
        Connection connection = dbConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COSTUMES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                KostumModel kostum = createKostumFromResultSet(resultSet);
                kostumList.add(kostum);
            }

            LOGGER.log(Level.INFO, "Retrieved {0} costumes from database", kostumList.size());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving costumes", e);
        }

        return kostumList;
    }
    
    /**
     * Updates an existing costume in the database.
     *
     * @param kostum the costume model with updated information
     * @return true if the costume was updated successfully, false otherwise
     */
    public boolean updateKostum(KostumModel kostum) {
        if (kostum == null) {
            LOGGER.log(Level.WARNING, "Attempted to update null costume");
            return false;
        }

        Connection connection = dbConnection.getConnection();
        boolean isUpdated = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COSTUME_SQL)) {
            preparedStatement.setString(1, kostum.getNama());
            preparedStatement.setString(2, kostum.getUkuran());
            preparedStatement.setInt(3, kostum.getHarga());
            preparedStatement.setString(4, kostum.getDeskripsi());
            preparedStatement.setString(5, kostum.getId_kostum());

            isUpdated = preparedStatement.executeUpdate() > 0;

            if (isUpdated) {
                LOGGER.log(Level.INFO, "Costume updated successfully: {0}", kostum.getId_kostum());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating costume", e);
        }

        return isUpdated;
    }
    
    /**
     * Deletes a costume from the database.
     *
     * @param kostumId the ID of the costume to delete
     * @return true if the costume was deleted successfully, false otherwise
     */
    public boolean hapusKostum(String kostumId) {
        if (kostumId == null || kostumId.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to delete costume with invalid ID");
            return false;
        }

        Connection connection = dbConnection.getConnection();
        boolean isDeleted = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COSTUME_SQL)) {
            preparedStatement.setString(1, kostumId);
            isDeleted = preparedStatement.executeUpdate() > 0;

            if (isDeleted) {
                LOGGER.log(Level.INFO, "Costume deleted successfully: {0}", kostumId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting costume", e);
        }

        return isDeleted;
    }
    
    /**
     * Generates a unique costume ID.
     * Format: C-XXXX where XXXX is a random 4-digit number.
     *
     * @param connection the database connection
     * @return a unique costume ID, or null if generation fails
     */
    public String generateUniqueId(Connection connection) {
        Random random = new Random();
        boolean unique = false;

        try {
            while (!unique) {
                int randomNum = ID_MIN_RANGE + random.nextInt(ID_MAX_RANGE);
                String id = COSTUME_ID_PREFIX + randomNum;

                if (!isIdExists(connection, id)) {
                    LOGGER.log(Level.FINE, "Generated unique costume ID: {0}", id);
                    return id;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error generating unique ID", e);
        }

        return null;
    }
    
    /**
     * Checks if a costume ID already exists in the database.
     *
     * @param connection the database connection
     * @param id the costume ID to check
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
     * Retrieves a costume by its ID.
     *
     * @param idKostum the costume ID to search for
     * @return the costume model if found, null otherwise
     */
    public KostumModel getKostumById(String idKostum) {
        if (idKostum == null || idKostum.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to retrieve costume with invalid ID");
            return null;
        }

        for (KostumModel kostum : getAllKostum()) {
            if (kostum.getId_kostum().equals(idKostum)) {
                return kostum;
            }
        }

        LOGGER.log(Level.WARNING, "Costume not found with ID: {0}", idKostum);
        return null;
    }

    /**
     * Creates a KostumModel object from a ResultSet.
     *
     * @param resultSet the ResultSet containing costume data
     * @return a new KostumModel object
     * @throws SQLException if database error occurs
     */
    private KostumModel createKostumFromResultSet(ResultSet resultSet) throws SQLException {
        String id_kostum = resultSet.getString("id_kostum");
        String nama = resultSet.getString("nama");
        String ukuran = resultSet.getString("ukuran");
        int harga = resultSet.getInt("harga");
        String deskripsi = resultSet.getString("deskripsi");

        return new KostumModel(id_kostum, nama, ukuran, harga, deskripsi);
    }
}
