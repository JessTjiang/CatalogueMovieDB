package com.jesslyntjiang.android.cataloguemovieuiux;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class NotificationDaily extends BroadcastReceiver {
    private final static int NOTIF_REQUEST_CODE = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context, NOTIF_REQUEST_CODE);
    }

    public void showNotification(Context context, int notifId){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent gotoMainActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, gotoMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Catalogue Movie")
                .setContentText(context.getResources().getString(R.string.missingyou))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setVibrate(new long[]{500,500})
                .setSound(alarmSound)
                .setAutoCancel(true);

        notificationManager.notify(notifId, builder.build());
    }

    public void setDailyNotification(Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        Intent intent = new Intent(context, NotificationDaily.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationDaily.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_REQUEST_CODE, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

}