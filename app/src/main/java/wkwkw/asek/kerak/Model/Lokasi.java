package wkwkw.asek.kerak.Model;

/**
 * Created by ASUS on 05/12/2017.
 */

public class Lokasi {
    private String latitude;
    private String longitude;
    private String tempatLokasi;

    public Lokasi(String latitude, String longitude, String tempatLokasi) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.tempatLokasi = tempatLokasi;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTempatLokasi() {
        return tempatLokasi;
    }
}
