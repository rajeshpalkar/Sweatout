package com.example.rajeshpalkar.sweatout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by rajeshpalkar on 8/9/17.
 */

public class Notification_Receiver extends BroadcastReceiver {
    //  public static final String MY_ACTION = "com.sample.myaction";
    @Override
    public void onReceive(Context context, Intent intent) {
        //   if(intent.getAction().equals(MY_ACTION)) {

        String get_extra = intent.getExtras().getString("extra");
        Intent serviceIntent = new Intent(context,MyService.class);
        serviceIntent.putExtra("extra",get_extra);
        context.startService(serviceIntent);

     /*      NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            Intent repeating_intent = new Intent(context, Repeating_activity.class);
            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.drawable.ic_notifications_active)
                    .setContentTitle("Notification Title")
                    .setContentText("Meal Time")
                    .setAutoCancel(true);

            notificationManager.notify(100, builder.build());*/


    }

    // }

}

