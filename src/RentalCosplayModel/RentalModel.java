package RentalCosplayModel;

public class RentalModel {
    private String idRental;
    private String nama;
    private String nomorTelp;
    private String alamat;
    private String kostum;
    private int durasiPinjam;
    private String ukuran;
    
    public RentalModel(){
        
    }
    
    public RentalModel(String nama, String nomorTelp, String alamat, String kostum, String ukuran, int durasiPinjam){
        this.nama = nama;
        this.nomorTelp = nomorTelp;
        this.alamat = alamat;
        this.kostum = kostum;
        this.ukuran = ukuran;
        this.durasiPinjam = durasiPinjam;
        
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

    public int getDurasiPinjam() {
        return durasiPinjam;
    }

    public void setDurasiPinjam(int durasiPinjam) {
        this.durasiPinjam = durasiPinjam;
    }

    public String getKostum() {
        return kostum;
    }

    public void setKostum(String kostum) {
        this.kostum = kostum;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getIdRental() {
        return idRental;
    }

    public void setIdRental(String idRental) {
        this.idRental = idRental;
    }
    
    
    
    
    
    
}
