package com.example.rajeshpalkar.sweatout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {
    public MyService() {

    }

    boolean isRunning;
    int startId;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // START YOUR TASKS

        String state =  intent.getExtras().getString("extra");

        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
        }
        System.out.println("ABCCC");

        if(!this.isRunning && startId==1) {

            this.isRunning = true;
            startId = 0;
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


            Intent repeating_intent = new Intent(this.getApplicationContext(), MainActivity.class);
            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.drawable.ic_notifications_active)
                    .setContentTitle("Meal Time")
                    .setContentText("Happy Meal!")
                    .setAutoCancel(true);

            notificationManager.notify(100, builder.build());
        }
        return Service.START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        // STOP YOUR TASKS
        super.onDestroy();
    }


}



