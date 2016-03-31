package com.pushdemo.pushclient;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Lib {


    public static String getDeviceID() {
        return Settings.Secure.getString(PushClientApp.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }



    /**
     * Ritorna l'activity in primo piano
     */
    public static Activity getForegroundActivity() {
        Activity activity = null;

        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            HashMap activities = (HashMap) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    activity = (Activity) activityField.get(activityRecord);
                }
            }


        } catch (Exception e) {
        }

        return activity;
    }

    public static Uri resourceToUri (int resID) {
        Context context = PushClientApp.getContext();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID));
    }


}
