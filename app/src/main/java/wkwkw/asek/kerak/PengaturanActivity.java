package wkwkw.asek.kerak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import wkwkw.asek.kerak.LocationService.LocationService;
import wkwkw.asek.kerak.helper.SharedPreferenceHelper;

public class PengaturanActivity extends AppCompatActivity {
    private Switch swSms, swLacakPosisi;
    private SharedPreferenceHelper sharedPreferenceHelper;
    //private SharedPreferenceHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        getSupportActionBar().setTitle("Menu");
        swSms = findViewById(R.id.sw_sms);
        swLacakPosisi = findViewById(R.id.sw_lacak_posisi);
        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        if(sharedPreferenceHelper.getKirimSMS()){
            swSms.setChecked(true);
        }
        if(sharedPreferenceHelper.getKirimLokasi()){
            swLacakPosisi.setChecked(true);
        }

        swSms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sharedPreferenceHelper.setKirimSMS(true);
                }else{
                    sharedPreferenceHelper.setKirimSMS(false);
                }
            }
        });
        swLacakPosisi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sharedPreferenceHelper.setKirimLokasi(true);
                    Intent service = new Intent(getApplicationContext(), LocationService.class);
                    startService(service);
                }else {
                    sharedPreferenceHelper.setKirimLokasi(false);
                    Intent service = new Intent(getApplicationContext(), LocationService.class);
                    stopService(service);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
