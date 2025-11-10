package rental.cosplay.model;

/**
 * Represents a costume (kostum) in the rental system.
 * Contains all costume details including ID, name, size, price, and description.
 *
 * @author ASUS
 * @version 1.0
 */
public class KostumModel {

    private String id_kostum;
    private String nama;
    private String ukuran;
    private int harga;
    private String deskripsi;
    
    /**
     * Constructs a new KostumModel with all costume details.
     *
     * @param id_kostum the unique identifier for the costume
     * @param nama the name of the costume
     * @param ukuran the size of the costume
     * @param harga the rental price of the costume
     * @param deskripsi the description of the costume
     */
    public KostumModel(String id_kostum, String nama, String ukuran, int harga, String deskripsi) {
        this.id_kostum = id_kostum;
        this.nama = nama;
        this.ukuran = ukuran;
        this.harga = harga;
        this.deskripsi = deskripsi;
    }

    /**
     * Gets the costume ID.
     *
     * @return the costume ID
     */
    public String getId_kostum() {
        return id_kostum;
    }

    /**
     * Sets the costume ID.
     *
     * @param id_kostum the costume ID to set
     */
    public void setId_kostum(String id_kostum) {
        this.id_kostum = id_kostum;
    }

    /**
     * Gets the costume name.
     *
     * @return the costume name
     */
    public String getNama() {
        return nama;
    }

    /**
     * Sets the costume name.
     *
     * @param nama the costume name to set
     */
    public void setNama(String nama) {
        this.nama = nama;
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
     * Gets the rental price.
     *
     * @return the rental price
     */
    public int getHarga() {
        return harga;
    }

    /**
     * Sets the rental price.
     *
     * @param harga the rental price to set
     */
    public void setHarga(int harga) {
        this.harga = harga;
    }

    /**
     * Gets the costume description.
     *
     * @return the costume description
     */
    public String getDeskripsi() {
        return deskripsi;
    }

    /**
     * Sets the costume description.
     *
     * @param deskripsi the costume description to set
     */
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    /**
     * Returns the costume name as the string representation.
     * Used for displaying the costume name in dropdown lists.
     *
     * @return the costume name
     */
    @Override
    public String toString() {
        return this.getNama();
    }
}
