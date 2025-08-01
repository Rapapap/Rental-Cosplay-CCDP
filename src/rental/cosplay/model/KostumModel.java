package rental.cosplay.model;

public class KostumModel {
    private String id_kostum;
    private String nama;
    private String ukuran;
    private int harga;
    private String deskripsi;
    
    public KostumModel(String id_kostum, String nama, String ukuran, int harga, String deskripsi){
        this.id_kostum = id_kostum;
        this.nama = nama;
        this.ukuran = ukuran;
        this.harga = harga;
        this.deskripsi = deskripsi;
    }


    public String getId_kostum() {
        return id_kostum;
    }

    public void setId_kostum(String id_kostum) {
        this.id_kostum = id_kostum;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    @Override
    public String toString(){
        return this.getNama(); // agar yang di tampilkan di dropdown tetap nama
    }
}
