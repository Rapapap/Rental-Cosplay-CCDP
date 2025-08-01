/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental.cosplay.controller;

//import RentalCosplayModel.RentalModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import rental.cosplay.DatabaseConnection;
import java.util.Random;
import java.sql.ResultSet;
import java.sql.Date; 
import java.time.LocalDate;
import rental.cosplay.model.RentalModel;

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
    
    public boolean tambahUser(rental.cosplay.model.RentalModel form){
        Connection connection = dbConnection.getConnection();
        boolean isAdded = false;

        String sql = "INSERT INTO rental (id_rental, nama, nomor_telp, alamat, id_kostum, durasi_pinjam, tgl_pinjam, tgl_kembali, status, total_biaya, ukuran_kostum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            
           String idBaru = genereteUniqueId(connection);
            
           //ambil tanggal hari ini
           LocalDate tglPinjam = LocalDate.now();
           
           // hitung tanggal kembali sesuai dengan inputan user dari combo box
           int durasi = form.getDurasiPinjam();
           //LocalDate tglKembali = LocalDate.parse("0000-00-00");
           
           // ubah ke java.sql.date
           Date sqlTglPinjam = Date.valueOf(tglPinjam);
           //Date sqlTglKembali = Date.valueOf(tglKembali);
           
           // status awal
           String status = "Belum Kembali";
           
           // hitung total biaya 
           int totalBiaya = durasi * form.getHarga();
           
           
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, idBaru);
           preparedStatement.setString(2, form.getNama());
           preparedStatement.setString(3, form.getNomorTelp());
           preparedStatement.setString(4, form.getAlamat());
           preparedStatement.setString(5, form.getIdKostum());
           preparedStatement.setInt(6, form.getDurasiPinjam());
           preparedStatement.setDate(7, sqlTglPinjam);
           preparedStatement.setDate(8, null);
           preparedStatement.setString(9, status);
           preparedStatement.setInt(10, totalBiaya);
           preparedStatement.setString(11, form.getUkuran());
           
           int rowInserted = preparedStatement.executeUpdate();
           isAdded = rowInserted > 0;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return isAdded;
    }
    
    public boolean cekData(String idRental){
        
        boolean ada = false;
        
        try (Connection conn = dbConnection.getConnection();) {
            String sql = "SELECT COUNT(*) FROM rental WHERE id_rental = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, idRental);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next() && result.getInt(1) >  0){
                ada = true;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return ada;
    }
    
    public boolean hapusData(String idRental){
        
         Connection conn = dbConnection.getConnection();
        boolean isDeleted = false;
        
        String sql = "DELETE FROM rental WHERE id_rental = ?";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, idRental);
            
            int rowAffected = preparedStatement.executeUpdate();
            isDeleted = rowAffected > 0;
            
            preparedStatement.close();
            conn.close();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return isDeleted;
    }
    
    public boolean UpdateData(RentalModel form){
        Connection conn = dbConnection.getConnection();
        boolean isUpdate = false;
        
        String sql = "UPDATE rental SET nama = ?, nomor_telp = ?, alamat = ?, id_kostum = ?, durasi_pinjam = ?, tgl_pinjam = ?, tgl_kembali = ?, status = ?, total_biaya = ?, ukuran_kostum = ? WHERE id_rental = ?";
        
        //ambil tanggal hari ini
        LocalDate tglPinjam = LocalDate.now();
           
        // hitung tanggal kembali sesuai dengan inputan user dari combo box
        int durasi = form.getDurasiPinjam();
        LocalDate tglKembali = LocalDate.MAX;
        
        if (form.getStatus() == "Sudah Kembali") {
            tglKembali = LocalDate.now();
        }else{
            tglKembali = form.getTglKembali() == null ? null : LocalDate.parse(form.getTglKembali());;
        }
           
        // ubah ke java.sql.date
        Date sqlTglPinjam = Date.valueOf(tglPinjam);
        Date sqlTglKembali = tglKembali == null ? null : Date.valueOf(tglKembali);
           
        // status awal
        // String status = "Belum Kembali";
           
        // hitung total biaya 
        int totalBiaya = durasi * form.getHarga();
           
        
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
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

            isUpdate = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return isUpdate;
    }
    
        public RentalModel getDataById(String idRental){
            RentalModel  rental = null;
            
            try{
                Connection conn = dbConnection.getConnection();

                String sql = "SELECT * FROM rental WHERE id_rental = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, idRental);
                ResultSet result = preparedStatement.executeQuery();

               if (result.next()){
                    rental = new RentalModel();
                    rental.setIdRental(result.getString("id_rental"));
                    rental.setNama(result.getString("nama"));
                    rental.setNomorTelp(result.getString("nomor_telp"));
                    rental.setAlamat(result.getString("alamat"));
                    rental.setIdKostum(result.getString("id_kostum"));
                    rental.setUkuran(result.getString("ukuran_kostum"));
                    rental.setDurasiPinjam(result.getInt("durasi_pinjam"));
                    rental.setStatus(result.getString("status"));     

                }
            } catch (Exception e){
                e.printStackTrace();
            }

            return rental;
        }
}
