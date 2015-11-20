package com.cs151.helpfulhints;

import android.app.Application;

import com.cs151.helpfulhints.Background.ReminderGCMTaskService;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class MainApplication extends Application {

    public static ReminderDataStructure Data;

    @Override
    public void onCreate(){
        super.onCreate();

        Data = ReminderDataStructure.getInstance(this);

        PeriodicTask periodicTask = new PeriodicTask.Builder()
            .setService(ReminderGCMTaskService.class)
            .setPeriod(60 * 30)
            .setTag("createNotif")
            .setRequiredNetwork(Task.NETWORK_STATE_ANY)
            .setPersisted(true)
            .setRequiresCharging(false)
            .build();
        GcmNetworkManager.getInstance(this).cancelAllTasks(ReminderGCMTaskService.class);

        /** This line will schedule a period task, use cautiously */
        GcmNetworkManager.getInstance(this).schedule(periodicTask);
    }
}
