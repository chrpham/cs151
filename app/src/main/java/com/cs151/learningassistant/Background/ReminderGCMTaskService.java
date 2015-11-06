package com.cs151.learningassistant.Background;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.cs151.learningassistant.R;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

public class ReminderGCMTaskService extends GcmTaskService {
    @Override
    public int onRunTask(TaskParams taskParams) {
        Log.d("ReminderGCMTaskService", taskParams.getTag());
        if(taskParams.getTag().equals("createNotif")) {
            createNotification();
        }
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    private void createNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Test");
        builder.setContentText("This notification is a test, sent via GCM!");
        builder.setPriority(Notification.PRIORITY_LOW);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
