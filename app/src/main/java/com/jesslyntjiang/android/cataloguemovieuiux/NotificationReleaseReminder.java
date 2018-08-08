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
import android.util.Log;

import com.jesslyntjiang.android.cataloguemovieuiux.Search.MovieList;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class NotificationReleaseReminder extends BroadcastReceiver{

    private final int NOTIF_ID_REQ = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String date = new SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault()).format(new Date());
        final String title = "";

        String URL_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
        String URL_UPCOMING = "https://api.themoviedb.org/3/movie/upcoming?api_key="+URL_KEY+"&language=en-US";

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.get(URL_UPCOMING, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String (responseBody);
                    JSONObject response = new JSONObject(result);
                    JSONArray getResult = response.getJSONArray("results");

                    for(int i = 0; i < getResult.length(); i++){
                        JSONObject json = getResult.getJSONObject(i);
                        MovieList movieList = new MovieList(json);
                        Log.d("release", "onSuccess : "+movieList.getTanggalFilm());
                        if(movieList.getTanggalFilm().equals(date)){
                           title.concat(movieList.getJudulFilm()+", ");
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        String message = String.format(context.getResources().getString(R.string.todayrelease), title);
        if(!title.equals("")){
            showNotification(context, NOTIF_ID_REQ, title ,message);
        }

    }

    public void showNotification(Context context, int notifId, String title, String message){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent gotoMainActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, gotoMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setVibrate(new long[]{500,500})
                .setSound(alarmSound)
                .setAutoCancel(true);

        notificationManager.notify(notifId, builder.build());
    }

    public void setReleaseNotification(Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.PM);

        Intent intent = new Intent(context, NotificationReleaseReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REQ, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReleaseReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REQ, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

}
