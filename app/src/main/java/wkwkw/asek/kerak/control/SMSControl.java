package wkwkw.asek.kerak.control;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by ASUS on 01/12/2017.
 */

public class SMSControl {

   String token;
    public void smsControl(final Context context, final String message, final String noTeleponMurid){
        String authorization = null;

        authorization = "MxNl12P0ZXJUpGMpLZPFPyGlDuUjM0rN" + ":" + "4jBgMNVCH420515A";
        if (authorization != null) {
            byte[] encodedBytes;
            encodedBytes = Base64.encode(authorization.getBytes(), Base64.NO_WRAP);
            authorization = "Basic " + new String(encodedBytes);
            System.out.println("auto" + authorization);
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Basic TXhObDEyUDBaWEpVcEdNcExaUEZQeUdsRHVVak0wck46NGpCZ01OVkNINDIwNTE1QQ==");
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        RequestParams params = new RequestParams();
        params.add("grant_type", "client_credentials");
        client.post("https://blinke-stage.apigee.net/oauth/token",params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject tokenResult = new JSONObject(result);
                    token = tokenResult.getString("access_token");
                    Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
                    kirimSMS(context, token, message, noTeleponMurid);
                } catch (JSONException e) {
                    Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "Gagal1", Toast.LENGTH_SHORT).show();
                System.out.println("error "+error.getMessage());

            }

        });


    }

    public void kirimSMS(Context context, String token, String message, String noTeleponMurid){


        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer "+token);
        JSONObject kirimsms = new JSONObject();
        try {
            kirimsms.put("message", message);
            kirimsms.put("msisdn", noTeleponMurid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity= new StringEntity(kirimsms.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(context, "https://blinke-stage.apigee.net/imx/sms",entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject tokenResult = new JSONObject(result);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("error "+error.getMessage());

            }

        });
    }
}
