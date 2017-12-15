package wkwkw.asek.kerak.SmsReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import wkwkw.asek.kerak.Model.SMS;
import wkwkw.asek.kerak.helper.SharedPreferenceHelper;

/**
 * Created by ASUS on 30/11/2017.
 */

public class SmsReceiver extends BroadcastReceiver {
    private SharedPreferenceHelper sharedPreferenceHelper;
    private static final String KEY_STATUS_KIRIM_SMS = "statusKirimSMS";

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        if(sharedPreferenceHelper.getKirimSMS()){
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            Bundle myBundle = intent.getExtras();
            SmsMessage[] messages = null;
            String strMessage = "";
            if (myBundle != null) {
                Object[] pdus = (Object[]) myBundle.get("pdus");
                messages = new SmsMessage[pdus.length];
                System.out.println("panjang " + messages.length);
                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                    strMessage += " : ";
                    strMessage += messages[i].getMessageBody();
                    strMessage += "\n";
                    SMS sms = new SMS(messages[i].getOriginatingAddress(), messages[i].getMessageBody());
                    mDatabase.child("users").child(sharedPreferenceHelper.getIdOrangTua()).child("SMS").push().setValue(sms);
                }
            }
        }
    }
}
