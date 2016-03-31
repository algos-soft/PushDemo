package com.pushdemo.pushclient;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

/**
 *
 */
public class PushClientApp extends Application {

    // Attenzione alle variabili statiche!
    // In Android, le variabili statiche possono essere ritenute
    // tra una sessione e l'altra della applicazione perché
    // il sistema non necessariamente distrugge il processo e ferma la JVM
    private static PushClientApp instance;


    public PushClientApp() {
        instance = this;
        init();
    }

    /**
     * Reinizializza tutte le variabili.
     * In Android, le variabili statiche (in questo caso il Singleton della app,
     * con tutte le sue variabili - anche non statiche) possono essere ritenute
     * tra una sessione e l'altra della applicazione perché
     * il sistema non necessariamente distrugge il processo e ferma la JVM.
     * Quindi all'avvio della app il prima possibile le devo resettare.
     */
    public static void init() {
    }

    public static Context getContext() {
        return getInstance();
    }

    public static PushClientApp getInstance() {
        return instance;
    }


    /**
     * Create and show a simple notification containing the received GCM message.
     */
    public void sendNotification(Bundle data) {

        Intent intent = new Intent();
        intent.setClass(this, PushActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String msg = data.getString("message");
        Uri defaultSoundUri = Lib.resourceToUri(R.raw.surprise);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Push Notification")
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        Notification mNotification = notificationBuilder.build();
        mNotification.flags |= Notification.FLAG_INSISTENT;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotification);

        // dopo l'invio della notifica sveglia il device
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), getClass().getSimpleName());
        wakeLock.acquire();


    }
}
