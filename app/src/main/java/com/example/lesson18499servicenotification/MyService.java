package com.example.lesson18499servicenotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    NotificationCompat.Builder builder;
    NotificationManager nm;
    Notification notification;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        showSimpleNotification(1, R.drawable.ic_launcher_foreground, "Title", "Notification text");
//        updateSimpleNotification(1);
//        showSimpleNotification(2, android.R.drawable.ic_dialog_email, "Title 2", "Notification text 2");
//        cancelNotification(1);
//        showIntentNotification();
        showMyCustomNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelNotifications();
    }

    void createSimpleNotificationBuilder(int icon, String title, String text) {
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text);
    }

    void createNotification() {
        notification = builder.build();
    }

    void showSimpleNotification(int id, int icon, String title, String text) {
        createSimpleNotificationBuilder(icon, title, text);
        createNotification();
        nm.notify(id, notification);
    }

    void updateSimpleNotification(int id) {
        pause(5);
        showSimpleNotification(id, android.R.drawable.ic_dialog_email, "TitleChange", "Notification text change");
    }

    void cancelNotification(int id) {
        pause(2);
        nm.cancel(id);
    }

    void cancelNotifications() {
        nm.cancelAll();
    }

    void showIntentNotification() {
        // Create PendingIntent
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(MainActivity.FILE_NAME, "Some file");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Create Notification
        createSimpleNotificationBuilder(R.drawable.ic_launcher_foreground, "Title", "Notification with intent");
        builder.setContentIntent(pendingIntent).setAutoCancel(true);
        createNotification();
        nm.notify(3, notification);
    }

    void showMyCustomNotification() {
        pause(3);
        createSimpleNotificationBuilder(R.drawable.ic_launcher_foreground, "Custom", "Notification text");
        builder.setNumber(5);
        builder.setContentInfo("info");
        builder.setColor(Color.GREEN);
//        builder.setWhen(1);
        builder.setShowWhen(true);
        builder.setUsesChronometer(true);
        builder.setOngoing(true);
        builder.setVibrate(new long[2]);
        builder.setSound(null);
        builder.setLights(Color.GREEN, 5000, 5000);
        createNotification();
        nm.notify(1, notification);
    }

    void pause(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
