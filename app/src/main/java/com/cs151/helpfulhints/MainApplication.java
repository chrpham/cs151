package com.cs151.helpfulhints;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cs151.helpfulhints.Background.ReminderGCMTaskService;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class MainApplication extends Application {

    public static final String NOTIF_INTERVAL_PREF = "com.cs151.helpfulhints.notif_interval_pref";
    public static final int DEFAULT_NOTIF_INTERVAL = 30;
    public static final String NOTIF_TASK_TAG = "com.cs151.helpfulhints.notif_task";

    public static final String MASTER_TOGGLE_PREF = "com.cs151.helpfulhints.master_toggle_pref";

    public static ReminderDataStructure Data;

    @Override
    public void onCreate(){
        super.onCreate();

        Data = ReminderDataStructure.getInstance(this);

        scheduleTask(this);
    }

    protected static void scheduleTask(Context context) {
        GcmNetworkManager.getInstance(context).cancelTask(NOTIF_TASK_TAG ,ReminderGCMTaskService.class);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int interval = prefs.getInt(NOTIF_INTERVAL_PREF, DEFAULT_NOTIF_INTERVAL);
        boolean notifsEnabled = prefs.getBoolean(MASTER_TOGGLE_PREF, true);
        Log.v("MainApp", "Notifs Enabled: " + notifsEnabled);
        if(notifsEnabled) {
            Log.v("MainApp", "Setting notif interval to: " + interval);
            PeriodicTask periodicTask = new PeriodicTask.Builder()
                .setService(ReminderGCMTaskService.class)
                .setPeriod(interval * 60)
                .setTag(NOTIF_TASK_TAG)
                .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                .setPersisted(true)
                .setRequiresCharging(false)
                .build();

            /** This line will schedule a period task, use cautiously */
            GcmNetworkManager.getInstance(context).schedule(periodicTask);
        }
    }
}
