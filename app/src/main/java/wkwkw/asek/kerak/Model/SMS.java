package wkwkw.asek.kerak.Model;

/**
 * Created by ASUS on 30/11/2017.
 */

public class SMS {
    private String nomorAsal;
    private String isiSMS;

    public SMS(String nomorAsal, String isiSMS) {
        this.nomorAsal = nomorAsal;
        this.isiSMS = isiSMS;
    }

    public String getNomorAsal() {
        return nomorAsal;
    }

    public String getIsiSMS() {
        return isiSMS;
    }
}
