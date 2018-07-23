package com.jesslyntjiang.android.cataloguemovieuiux;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    SharedPreferences sharedPreferences;
    Context context;

    public AppPreference(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = context.getResources().getString(R.string.first_run);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public boolean getFirstRun(){
        String key = context.getResources().getString(R.string.first_run);
        return  sharedPreferences.getBoolean(key, true);
    }
}
