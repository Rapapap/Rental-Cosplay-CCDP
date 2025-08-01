package rental.cosplay.model;

public class RentalModel {
    private String idRental;
    private String nama;
    private String nomorTelp;
    private String alamat;
    private String idKostum;
    private int durasiPinjam;
    private String ukuran;
    private int harga;
    private String tglPinjam;
    private String tglKembali;
    private String status;
    
    public RentalModel(){
        
    }
    
    public RentalModel(String nama, String nomorTelp, String alamat, String kostum, String ukuran, int durasiPinjam, int harga){
        this.nama = nama;
        this.nomorTelp = nomorTelp;
        this.alamat = alamat;
        this.idKostum = kostum;
        this.ukuran = ukuran;
        this.durasiPinjam = durasiPinjam;
        this.harga = harga;
        
    }

    public String getIdRental() {
        return idRental;
    }

    public void setIdRental(String idRental) {
        this.idRental = idRental;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomorTelp() {
        return nomorTelp;
    }

    public void setNomorTelp(String nomorTelp) {
        this.nomorTelp = nomorTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getIdKostum() {
        return idKostum;
    }

    public void setIdKostum(String kostum) {
        this.idKostum = kostum;
    }

    public int getDurasiPinjam() {
        return durasiPinjam;
    }

    public void setDurasiPinjam(int durasiPinjam) {
        this.durasiPinjam = durasiPinjam;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getTglPinjam() {
        return tglPinjam;
    }

    public void setTglPinjam(String tglPinjam) {
        this.tglPinjam = tglPinjam;
    }

    public String getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(String tglKembali) {
        this.tglKembali = tglKembali;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
}
