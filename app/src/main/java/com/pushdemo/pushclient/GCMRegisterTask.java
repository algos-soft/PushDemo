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
 * Registers the application with GCM servers asynchronously.
 * <p>
 * Stores the registration ID in the application's shared preferences.
 */
public class GCMRegisterTask extends AsyncTask<Void, Void, String> {

    private static final String TAG="GCMREG";
    private MainActivity activity;

    public GCMRegisterTask(MainActivity activity) {
        this.activity=activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        String msg = "";
        try {
            Context context = PushClientApp.getContext();
            String senderId= "115110381874";
            InstanceID instanceID = InstanceID.getInstance(context);
            String regToken = instanceID.getToken(senderId,  GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            // Persist the token - no need to register again.
            activity.getPrefs().edit().putString(Prefs.GCM_REGISTRATION_TOKEN, regToken).commit();


            // send the token to your server here
            //....



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
                String msg="Registrazione GCM effettuata.";
                Toast toast= Toast.makeText(act, msg, Toast.LENGTH_LONG);
                toast.show();
                Log.d(TAG, "Successfully registered to GCM");
                activity.showToken();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setPositiveButton("Continua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setTitle("Registrazione GCM fallita");
                builder.setMessage("Non verranno ricevute notifiche.\nErrore: " + s);
                builder.show();
                Log.d(TAG, "GCM registration failed: " + s);

            }

        }
    }


}
