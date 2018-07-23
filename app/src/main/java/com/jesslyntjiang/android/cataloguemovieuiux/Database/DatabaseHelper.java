package com.jesslyntjiang.android.cataloguemovieuiux.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.FAVORITE_MOVIE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "favoritemovie";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_FAV = String.format("CREATE_TABLE %s"
                    +" (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," + " %s TEXT NOT NULL" +
                    " %s TEXT NOT NULL" + " %s TEXT NOT NULL" +  "%s TEXT NOT NULL)",
            DatabaseContract.FAVORITE_MOVIE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.OVERVIEW,
            DatabaseContract.MovieColumns.RELEASE_YEAR,
            DatabaseContract.MovieColumns.IMAGE,
            DatabaseContract.MovieColumns.RATING);


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+FAVORITE_MOVIE);
        onCreate(db);
    }
}
