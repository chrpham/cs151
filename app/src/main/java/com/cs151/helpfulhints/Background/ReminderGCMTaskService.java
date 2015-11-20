package com.cs151.helpfulhints.Background;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.cs151.helpfulhints.R;
import com.cs151.helpfulhints.ReminderDataStructure;
import com.cs151.helpfulhints.Reminder;
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
        Reminder reminder = ReminderDataStructure.getInstance(this).getNextNotification();
        if(reminder != null) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle(reminder.getTitle());
            builder.setContentText(reminder.getBody());
            builder.setPriority(Notification.PRIORITY_LOW);
            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }
    }
}
