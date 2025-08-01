/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental.cosplay.controller;

import rental.cosplay.model.RentalModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rental.cosplay.DatabaseConnection;
/**
 *
 * @author Hakim
 */
public class RentalController {
    private final DatabaseConnection dbConnection;

    public RentalController() {
        dbConnection = new DatabaseConnection();
    }
    
     public List<RentalModel> getAllRental() {
        List<RentalModel> rental = new ArrayList<>();
        Connection connection = dbConnection.getConnection();

        String sql = "SELECT * FROM rental";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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

                rental.add(new RentalModel(id_rental, nama, noTelp, alamat, durasi, ukuranKostum, tBiaya, String.valueOf(tglPinjam), String.valueOf(tglKembali), status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rental;
    }
     
     public RentalModel getRentalNama(String nama){
         RentalModel model = null;
         try{
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT * FROM rental WHERE nama=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nama);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()){
                model.setIdRental(rs.getString("id_rental"));
                model.setNama(rs.getString("nama"));
                model.setNomorTelp(rs.getString("nomor_telp"));
                model.setAlamat(rs.getString("alamat"));
                model.setIdKostum(rs.getString("id_kostum"));
                model.setDurasiPinjam(rs.getInt("durasi_pinjam"));
                model.setUkuran(rs.getString("ukuran_kostum"));
            } 
                 
             } catch (Exception e) {
                 e.printStackTrace();
        }
         return model;
     }
}
