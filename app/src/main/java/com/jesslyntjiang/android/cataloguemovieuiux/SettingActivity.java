package com.jesslyntjiang.android.cataloguemovieuiux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    SwitchCompat switchDaily, switchRelease;
    private NotificationDaily notificationDaily = new NotificationDaily();
    private NotificationReleaseReminder notificationReleaseReminder = new NotificationReleaseReminder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchDaily = findViewById(R.id.reminderdaily);
        switchRelease = findViewById(R.id.reminderrelease);
        setDailyAlarm();
        setReleaseAlarm();
    }


    public void setDailyAlarm(){
        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchRelease.isChecked()){
                    notificationDaily.setDailyNotification(SettingActivity.this);
                    Toast.makeText(SettingActivity.this, "Daily Notification : on", Toast.LENGTH_SHORT).show();
                } else {
                    notificationDaily.cancelAlarm(SettingActivity.this);
                    Toast.makeText(SettingActivity.this, "Daily Notification : off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setReleaseAlarm(){
        switchRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchRelease.isChecked()){
                    notificationReleaseReminder.setReleaseNotification(SettingActivity.this);
                    Toast.makeText(SettingActivity.this, "Notification Release : on", Toast.LENGTH_SHORT).show();
                } else {
                    notificationReleaseReminder.cancelAlarm(SettingActivity.this);
                    Toast.makeText(SettingActivity.this, "Notification Release : off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}
