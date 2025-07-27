/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental.cosplay.controller;

import RentalCosplayModel.RentalModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import rental.cosplay.DatabaseConnection;

/**
 *
 * @author Hakim
 */
public class FormController {
    private final DatabaseConnection dbConnection;

    FormController(){
        dbConnection = new DatabaseConnection();
    }

    public boolean tambahUser(RentalModel form){
        Connection connection = dbConnection.getConnection();
        boolean isAdded = false;

        String sql = "INSERT INTO rental (id_rental, nama, nomor_telp, alamat, id_kostum, durasi_pinjam, tgl_pinjam, tgl_kembali, status, total_biaya) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, form.getNama());
           preparedStatement.setString(2, form.getNomorTelp());
           preparedStatement.setString(3, form.getAlamat());
           preparedStatement.setInt(4, form.getDurasiPinjam());
        } catch (SQLException e){
            e.printStackTrace();
        }

        return isAdded;
    }
}
