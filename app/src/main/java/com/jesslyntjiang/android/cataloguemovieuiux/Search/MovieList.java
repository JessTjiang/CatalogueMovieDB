package com.jesslyntjiang.android.cataloguemovieuiux.Search;

import android.database.Cursor;

import com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.getColumnInt;
import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.getColumnString;

public class MovieList {
    String judulFilm, deskripsiFilm, tanggalFilm, fotoFilm, ratingFilm, perhitunganRating;
    private int id;

    public MovieList(JSONObject jsonObject){
        try{
            String title = jsonObject.getString("title");
            String overview = jsonObject.getString("overview");
            String release_date = jsonObject.getString("release_date");
            String poster_path = jsonObject.getString("poster_path");
            String vote_count = jsonObject.getString("vote_count");
            String vote_average = jsonObject.getString("vote_average");

            this.judulFilm = title;
            this.deskripsiFilm = overview;
            this.tanggalFilm = release_date;
            this.fotoFilm = poster_path;
            this.ratingFilm = vote_count;
            this.perhitunganRating = vote_average;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public MovieList(){}

    public MovieList(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.judulFilm = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.ratingFilm = getColumnString(cursor, DatabaseContract.MovieColumns.RATING);
        this.tanggalFilm = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_YEAR);
        this.fotoFilm = getColumnString(cursor, DatabaseContract.MovieColumns.IMAGE);
        this.deskripsiFilm = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
    }

    public int getId(){ return id;}
    public void setId(int id){this.id = id;}

    public String getJudulFilm(){
        return judulFilm;
    }
    public void setJudulFilm(String judulFilm){
        this.judulFilm = judulFilm;
    }

    public String getDeskripsiFilm(){
        return deskripsiFilm;
    }
    public void setDeskripsiFilm(String deskripsiFilm){
        this.deskripsiFilm = deskripsiFilm;
    }

    public String getTanggalFilm(){
        return tanggalFilm;
    }
    public void setTanggalFilm(String tanggalFilm){
        this.tanggalFilm = tanggalFilm;
    }

    public String getFotoFilm(){
        return fotoFilm;
    }
    public void setFotoFilm(String fotoFilm){
        this.fotoFilm = fotoFilm;
    }

    public String getRatingFilm(){
        return ratingFilm;
    }
    public void setRatingFilm(String ratingFilm){
        this.ratingFilm = ratingFilm;
    }

    public String getPerhitunganRating(){
        return perhitunganRating;
    }
    public void setPerhitunganRating(String perhitunganRating){
        this.perhitunganRating= perhitunganRating;
    }

}
