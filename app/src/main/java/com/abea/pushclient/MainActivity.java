package com.abea.pushclient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


public class MainActivity extends Activity {

    public static final String GCM_REGISTRATION_TOKEN = "gmcRegistrationToken";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public String getRegistrationToken() {
        return getPrefs().getString(GCM_REGISTRATION_TOKEN,null);
    }

    /**
     * Ritorna le preferenze della applicazione
     */
    public SharedPreferences getPrefs(){
        return getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
    }


}
