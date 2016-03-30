package com.abea.pushclient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class MainActivity extends Activity {

    public static final String GCM_REGISTRATION_TOKEN = "gmcRegistrationToken";
    private static final String PREF_FILE="prefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        showToken();

        Button bRegister=(Button)findViewById(R.id.registergcm);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegistration();
            }
        });

        Button bDelete=(Button)findViewById(R.id.deletegcm);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unregister();
                getPrefs().edit().remove(Prefs.GCM_REGISTRATION_TOKEN).commit();
                showToken();
            }
        });


    }

    /**
     * Mostra sul display il token contenuto nelle preferenze
     */
    public void showToken(){
        String regtoken = getPrefs().getString(GCM_REGISTRATION_TOKEN, "");
        TextView tv = (TextView)findViewById(R.id.gcmtoken);
        tv.setText(regtoken);
    }


    private void launchRegistration(){
        GCMRegisterTask task = new GCMRegisterTask(this);
        task.execute();
    }


    private void unregister(){
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getBaseContext());
        try {
            gcm.unregister();
        }
        catch (IOException e) {
            System.out.println("Error Message: " + e.getMessage());
        }

    }

//    public String getRegistrationToken() {
//        return getPrefs().getString(GCM_REGISTRATION_TOKEN,null);
//    }

    /**
     * Ritorna le preferenze della applicazione
     */
    public SharedPreferences getPrefs(){
        return getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    }


}
