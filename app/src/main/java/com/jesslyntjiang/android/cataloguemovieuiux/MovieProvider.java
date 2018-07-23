package com.jesslyntjiang.android.cataloguemovieuiux;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract;

import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.AUTHORITY;
import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.CONTENT_URI;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.FAVORITE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.FAVORITE_MOVIE+"/#", ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor!=null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long added;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
