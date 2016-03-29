package com.abea.pushclient;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Libreria per l'accesso alle preferenze della applicazione.
 */
public class Prefs {

    public static final String MY_PREFS_NAME = "UserPrefs";
    public static final String RECEIVE_NOTIFICATIONS = "receiveNotifications";
    public static final String GCM_REGISTRATION_TOKEN = "gmcRegistrationToken";


    /**
     * Ritorna le preferenze della applicazione
     */
    public static SharedPreferences getPrefs(){
        return PushClientApp.getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Ritorna un editor delle preferenze
     */
    public static SharedPreferences.Editor getEditor(){
        return getPrefs().edit();
    }

    public static void remove(String key){
        SharedPreferences.Editor editor=getEditor();
        editor.remove(key);
        editor.commit();
    }


    public static boolean isRiceviNotifiche() {
        return getPrefs().getBoolean(RECEIVE_NOTIFICATIONS,true);
    }

    public static String getRegistrationToken() {
        return getPrefs().getString(GCM_REGISTRATION_TOKEN,null);
    }

}
