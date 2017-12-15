package wkwkw.asek.kerak.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ASUS on 01/12/2017.
 */

public class SharedPreferenceHelper {

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AndroidHiveLogin";
    private static final String KEY_ID_ORANG_TUA = "idOrangTua";
    private static final String KEY_SUDAH_PERNAH_LOGIN = "sudahPernahLogin";
    private static final  String KEY_KIRIM_SMS = "keyKirimSMS";
    private static final  String KEY_KIRIM_LOKASI = "keyKirimLokasi";

    private static String SHARE_KEY_AVATA = "avata";

    public SharedPreferenceHelper(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setIdOrangTua(String idOrangTua) {
        editor.putString(KEY_ID_ORANG_TUA, idOrangTua);
        // commit changes
        editor.commit();
    }
    public String getIdOrangTua(){
        return pref.getString(KEY_ID_ORANG_TUA, "default");
    }

    public void setSudahPernahLogin(boolean sudahPernahLogin){
        editor.putBoolean(KEY_SUDAH_PERNAH_LOGIN, sudahPernahLogin);
        // commit changes
        editor.commit();
    }

    public boolean getSudahPernahLogin(){
        return pref.getBoolean(KEY_SUDAH_PERNAH_LOGIN, false);
    }

    public void setKirimSMS(boolean isKirimSMS){
        editor.putBoolean(KEY_KIRIM_SMS, isKirimSMS);
        // commit changes
        editor.commit();

    }

    public boolean getKirimSMS(){
        return pref.getBoolean(KEY_KIRIM_SMS, false);
    }
    public void setKirimLokasi(boolean isKirimLokasi){
        editor.putBoolean(KEY_KIRIM_LOKASI, isKirimLokasi);
        // commit changes
        editor.commit();

    }

    public boolean getKirimLokasi(){
        return pref.getBoolean(KEY_KIRIM_LOKASI, false);
    }
}
