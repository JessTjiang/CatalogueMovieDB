package com.jesslyntjiang.android.cataloguemovieuiux.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String FAVORITE_MOVIE = "favorite_film";

    public static final class MovieColumns implements BaseColumns {
        public static String TITLE = "judul";
        public static String OVERVIEW = "deskripsi";
        public static String RELEASE_YEAR = "tahun";
        public static String IMAGE = "poster";
        public static String RATING = "rating";
    }

    public static final String AUTHORITY = "com.jesslyntjiang.android.cataloguemovieuiux";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(FAVORITE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
