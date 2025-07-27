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
import java.util.Random;
import java.sql.ResultSet;
import java.sql.Date; 
import java.time.LocalDate;

/**
 *
 * @author Hakim
 */
public class FormController {
    private final DatabaseConnection dbConnection;

    public FormController(){
        dbConnection = new DatabaseConnection();
    }
    
    public String genereteUniqueId(Connection connection){
        String id_r;
        Random random = new Random();
        boolean unique = false;
        
        try{
            while (!unique){
                int randomNum = 1000 + random.nextInt(9000);
                id_r = "UR-" + randomNum;
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM rental WHERE id_rental = ?");
                preparedStatement.setString(1, id_r);
                ResultSet resultSet =  preparedStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) == 0){
                    unique = true;
                    return id_r;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean tambahUser(RentalModel form){
        Connection connection = dbConnection.getConnection();
        boolean isAdded = false;

        String sql = "INSERT INTO rental (id_rental, nama, nomor_telp, alamat, id_kostum, durasi_pinjam, tgl_pinjam, tgl_kembali, status, total_biaya) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            
            String idBaru = genereteUniqueId(connection);
            
           //ambil tanggal hari ini
           LocalDate tglPinjam = LocalDate.now();
           
           // hitung tanggal kembali sesuai dengan inputan user dari combo box
           int durasi = form.getDurasiPinjam();
           LocalDate tglKembali = tglPinjam.plusDays(durasi);
           
           // ubah ke java.sql.date
           Date sqlTglPinjam = Date.valueOf(tglPinjam);
           Date sqlTglKembali = Date.valueOf(tglKembali);
           
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, idBaru);
           preparedStatement.setString(2, form.getNama());
           preparedStatement.setString(3, form.getNomorTelp());
           preparedStatement.setString(4, form.getAlamat());
           preparedStatement.setString(5, form.getKostum());
           preparedStatement.setInt(6, form.getDurasiPinjam());
           preparedStatement.setDate(7, sqlTglPinjam);
           preparedStatement.setDate(8, sqlTglKembali);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return isAdded;
    }

    public void tambahUser(rental.cosplay.model.RentalModel from) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
