package rental.cosplay.model;

/**
 * Represents a rental transaction in the cosplay rental system.
 * Contains all rental details including customer information, costume details,
 * rental duration, dates, and payment information.
 *
 * @author ASUS
 * @version 1.0
 */
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
    
    /**
     * Default constructor for RentalModel.
     */
    public RentalModel() {
    }
    
    /**
     * Constructs a new RentalModel for creating a rental transaction.
     *
     * @param nama customer name
     * @param nomorTelp customer phone number
     * @param alamat customer address
     * @param kostum costume ID
     * @param ukuran costume size
     * @param durasiPinjam rental duration in days
     * @param harga rental price per day
     */
    public RentalModel(String nama, String nomorTelp, String alamat, String kostum,
                       String ukuran, int durasiPinjam, int harga) {
        this.nama = nama;
        this.nomorTelp = nomorTelp;
        this.alamat = alamat;
        this.idKostum = kostum;
        this.ukuran = ukuran;
        this.durasiPinjam = durasiPinjam;
        this.harga = harga;   
    }

    /**
     * Constructs a complete RentalModel with all rental information.
     *
     * @param idRental unique rental transaction ID
     * @param nama customer name
     * @param nomorTelp customer phone number
     * @param alamat customer address
     * @param durasiPinjam rental duration in days
     * @param ukuran costume size
     * @param harga rental price per day
     * @param tglPinjam rental start date
     * @param tglKembali expected return date
     * @param status rental status (e.g., "Belum Kembali", "Sudah Kembali")
     */
    public RentalModel(String idRental, String nama, String nomorTelp, String alamat,
                       int durasiPinjam, String ukuran, int harga, String tglPinjam,
                       String tglKembali, String status) {
        this.idRental = idRental;
        this.nama = nama;
        this.nomorTelp = nomorTelp;
        this.alamat = alamat;
        this.durasiPinjam = durasiPinjam;
        this.ukuran = ukuran;
        this.harga = harga;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
        this.status = status;
    }
    
    /**
     * Gets the rental ID.
     *
     * @return the rental ID
     */
    public String getIdRental() {
        return idRental;
    }

    /**
     * Sets the rental ID.
     *
     * @param idRental the rental ID to set
     */
    public void setIdRental(String idRental) {
        this.idRental = idRental;
    }

    /**
     * Gets the customer name.
     *
     * @return the customer name
     */
    public String getNama() {
        return nama;
    }

    /**
     * Sets the customer name.
     *
     * @param nama the customer name to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * Gets the customer phone number.
     *
     * @return the customer phone number
     */
    public String getNomorTelp() {
        return nomorTelp;
    }

    /**
     * Sets the customer phone number.
     *
     * @param nomorTelp the customer phone number to set
     */
    public void setNomorTelp(String nomorTelp) {
        this.nomorTelp = nomorTelp;
    }

    /**
     * Gets the customer address.
     *
     * @return the customer address
     */
    public String getAlamat() {
        return alamat;
    }

    /**
     * Sets the customer address.
     *
     * @param alamat the customer address to set
     */
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    /**
     * Gets the costume ID.
     *
     * @return the costume ID
     */
    public String getIdKostum() {
        return idKostum;
    }

    /**
     * Sets the costume ID.
     *
     * @param kostum the costume ID to set
     */
    public void setIdKostum(String kostum) {
        this.idKostum = kostum;
    }

    /**
     * Gets the rental duration in days.
     *
     * @return the rental duration
     */
    public int getDurasiPinjam() {
        return durasiPinjam;
    }

    /**
     * Sets the rental duration in days.
     *
     * @param durasiPinjam the rental duration to set
     */
    public void setDurasiPinjam(int durasiPinjam) {
        this.durasiPinjam = durasiPinjam;
    }

    /**
     * Gets the costume size.
     *
     * @return the costume size
     */
    public String getUkuran() {
        return ukuran;
    }

    /**
     * Sets the costume size.
     *
     * @param ukuran the costume size to set
     */
    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    /**
     * Gets the rental price per day.
     *
     * @return the rental price
     */
    public int getHarga() {
        return harga;
    }

    /**
     * Sets the rental price per day.
     *
     * @param harga the rental price to set
     */
    public void setHarga(int harga) {
        this.harga = harga;
    }

    /**
     * Gets the rental start date.
     *
     * @return the rental start date
     */
    public String getTglPinjam() {
        return tglPinjam;
    }

    /**
     * Sets the rental start date.
     *
     * @param tglPinjam the rental start date to set
     */
    public void setTglPinjam(String tglPinjam) {
        this.tglPinjam = tglPinjam;
    }

    /**
     * Gets the expected return date.
     *
     * @return the expected return date
     */
    public String getTglKembali() {
        return tglKembali;
    }

    /**
     * Sets the expected return date.
     *
     * @param tglKembali the expected return date to set
     */
    public void setTglKembali(String tglKembali) {
        this.tglKembali = tglKembali;
    }

    /**
     * Gets the rental status.
     *
     * @return the rental status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the rental status.
     *
     * @param status the rental status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
