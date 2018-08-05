package com.jesslyntjiang.android.cataloguemovieuiux;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

public class NotificationDaily extends BroadcastReceiver {
    private final static int NOTIF_REQUEST_CODE = 200;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent gotoMainActivity = new Intent(context, MainActivity.class);
        gotoMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIF_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setContentTitle("Catalogue Movie")
                .setContentText(context.getResources().getString(R.string.missingyou))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setLights(Color.RED, 3000, 3000)
                .setAutoCancel(true);

        notificationManager.notify(NOTIF_REQUEST_CODE, notification.build());

    }
}
