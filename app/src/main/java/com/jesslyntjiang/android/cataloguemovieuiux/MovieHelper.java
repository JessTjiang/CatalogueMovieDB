package com.jesslyntjiang.android.cataloguemovieuiux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseHelper;
import com.jesslyntjiang.android.cataloguemovieuiux.Search.MovieList;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.FAVORITE_MOVIE;
import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.MovieColumns.IMAGE;
import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.MovieColumns.RATING;
import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.MovieColumns.RELEASE_YEAR;
import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.MovieColumns.TITLE;

public class MovieHelper {
    private static String DATABASE_TABLE = FAVORITE_MOVIE;

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MovieHelper(Context context){
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<MovieList> getMovieData(){
        ArrayList<MovieList> arrayList = new ArrayList<MovieList>();

        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, _ID+" ASC", null);
        cursor.moveToFirst();
        MovieList movieList;
        if(cursor.getCount()>0){
            do{
                movieList = new MovieList();
                movieList.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieList.setJudulFilm(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieList.setDeskripsiFilm(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movieList.setTanggalFilm(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_YEAR)));
                movieList.setFotoFilm(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                movieList.setRatingFilm(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                arrayList.add(movieList);
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction(){
        sqLiteDatabase.beginTransaction();
    }

    public void setTransactionSuccess(){
        sqLiteDatabase.setTransactionSuccessful();
    }

    public void endTransaction(){
        sqLiteDatabase.endTransaction();
    }

    public void insertTransaction(MovieList movieList){
        String sql = "INSERT INTO "+DATABASE_TABLE+" ("+TITLE+", "+OVERVIEW
                +", "+ RELEASE_YEAR+", "+IMAGE+", "+RATING+") VALUES (?,?,?,?,?)";
        SQLiteStatement stmt = sqLiteDatabase.compileStatement(sql);
        stmt.bindString(1, movieList.getJudulFilm());
        stmt.bindString(2, movieList.getDeskripsiFilm());
        stmt.bindString(3, movieList.getTanggalFilm());
        stmt.bindString(4, movieList.getFotoFilm());
        stmt.bindString(5, movieList.getRatingFilm());
        stmt.execute();
        stmt.clearBindings();
    }

    public boolean checkData(String title){
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE,null,TITLE+" LIKE ?",new String[]{title},null,null,_ID + " ASC",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }


    public int updateMovie(MovieList movieList){
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, movieList.getJudulFilm());
        initialValues.put(OVERVIEW, movieList.getDeskripsiFilm());
        initialValues.put(RELEASE_YEAR, movieList.getTanggalFilm());
        initialValues.put(IMAGE, movieList.getFotoFilm());
        initialValues.put(RATING, movieList.getRatingFilm());
        return sqLiteDatabase.update(DATABASE_TABLE, initialValues, _ID+"= '"+movieList.getId()+"'", null);
    }

    public int deleteMovie(int id){
        return sqLiteDatabase.delete(DATABASE_TABLE, _ID+" = '"+id+"'", null);
    }

    public Cursor queryByIdProvider(String id){
        return sqLiteDatabase.query(DATABASE_TABLE, null,
                _ID+" = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider(){
        return sqLiteDatabase.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID+" DESC");
    }

    public long insertProvider(ContentValues values){
        return sqLiteDatabase.insert(DATABASE_TABLE,null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return sqLiteDatabase.update(DATABASE_TABLE, values, _ID+" =?", new String[]{id});
    }

    public int deleteProvider(String id){
        return sqLiteDatabase.delete(DATABASE_TABLE, _ID+" = ?", new String[]{id});
    }
}
