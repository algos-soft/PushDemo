package com.pushdemo.pushclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Unregisters the application from GCM servers, asynchronously.
 * <p>
 * deletes the registration ID from the application's shared preferences.
 */
public class GCMUnregisterTask extends AsyncTask<Void, Void, String> {

    private static final String TAG="GCMUNREG";
    private MainActivity activity;

    public GCMUnregisterTask(MainActivity activity) {
        this.activity=activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        String msg = "";
        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(activity);
            gcm.unregister();
            activity.getPrefs().edit().remove(Prefs.GCM_REGISTRATION_TOKEN).commit();

        } catch (IOException ex) {
            msg = "Error : " + ex.getMessage();
        }
        return msg;
    }



    @Override
    protected void onPostExecute(String s) {
        Activity act = Lib.getForegroundActivity();
        if(act!=null){

            if(s.equals("")){
                String msg="Deregistrazione GCM effettuata.";
                Toast toast= Toast.makeText(act, msg, Toast.LENGTH_LONG);
                toast.show();
                Log.d(TAG, "Successfully unregistered from GCM");
                activity.showToken();

            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setPositiveButton("Continua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setTitle("Deregistrazione GCM fallita");
                builder.setMessage("Errore: " + s);
                builder.show();
                Log.d(TAG, "GCM unregistration failed: " + s);

            }

        }
    }



}
