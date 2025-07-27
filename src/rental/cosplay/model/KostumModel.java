package rental.cosplay.model;

public class KostumModel {
    private String id_kostum;
    private String nama;
    private String ukuran;
    private int harga;
    
    public KostumModel(String id_kostum, String nama, String ukuran, int harga){
        this.id_kostum = id_kostum;
        this.nama = nama;
        this.ukuran = ukuran;
        this.harga = harga;
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
    
}
