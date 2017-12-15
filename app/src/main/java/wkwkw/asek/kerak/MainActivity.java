package wkwkw.asek.kerak;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import wkwkw.asek.kerak.control.SMSControl;
import wkwkw.asek.kerak.helper.SharedPreferenceHelper;

import static android.Manifest.permission_group.CAMERA;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scanBtn;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private DatabaseReference mDatabase;
    private SharedPreferenceHelper sharedPreferencesHelper;
    int cekIdOrangTua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button) findViewById(R.id.scan_button);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedPreferencesHelper = new SharedPreferenceHelper(this);
        scanBtn.setOnClickListener(this);
        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion >=  Build.VERSION_CODES.M){
            if(checkPermission()){
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else {
                requestPermission();
            }
        }

    }

    public void onClick(View v) {
            int currentApiVersion = Build.VERSION.SDK_INT;
            if(currentApiVersion >=  Build.VERSION_CODES.M){
                if(checkPermission()){
                   openScanner();
                }
                else {
                    requestPermission();
                }
            }else{
               openScanner();
            }
    }
    public void openScanner(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }
    @Override
    public void onResume() {
        super.onResume();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()){

            } else {
                requestPermission();
            }
        }
    }
    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null){
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                cekIdOrangTua =0;
                DatabaseReference ref = mDatabase.child("users");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                            if(singleSnapshot.getKey().equals(result.getContents())){
                               cekIdOrangTua =1;
                            }
                        }
                        if(cekIdOrangTua ==1 ){
                            if( sharedPreferencesHelper.getIdOrangTua().toString().trim().equals("default")||sharedPreferencesHelper.getIdOrangTua().toString().trim().equals(result.getContents())){
                                if(sharedPreferencesHelper.getSudahPernahLogin()){

                                }else{
                                    sharedPreferencesHelper.setSudahPernahLogin(true);
                                    sharedPreferencesHelper.setIdOrangTua(result.getContents());

                                }
                                Intent intent = new Intent(MainActivity.this, PengaturanActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(MainActivity.this, "Mohon maaf anda bukan orang tua dari anak ini", Toast.LENGTH_LONG).show();
                                kirimSMS();
                            }

                        }else{
                            Toast.makeText(MainActivity.this, "tidak terdaftar", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void kirimSMS(){
        final DatabaseReference ref =  mDatabase.child("users").child(sharedPreferencesHelper.getIdOrangTua()).child("no_telepon");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   SMSControl smsControl = new SMSControl();
                    smsControl.smsControl(MainActivity.this, "Seseorang berusaha mengakses hp anak anda",dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                       openScanner();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}