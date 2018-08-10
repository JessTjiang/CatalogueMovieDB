package com.jesslyntjiang.android.favmovie;

import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailMovie extends AppCompatActivity {
    TextView tvTitle, tvDate, tvOverview, tvRating;
    ImageView posterMovie;

    MovieList movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvTitle = findViewById(R.id.tv_judul_film);
        tvDate = findViewById(R.id.tv_tanggal_masuk);
        tvOverview = findViewById(R.id.tv_deskripsi_film);
        posterMovie = findViewById(R.id.poster_movie);

        Uri uri = getIntent().getData();

        if(uri!= null){
            Cursor cursor = getContentResolver().query(uri, null, null, null,null);

            if (cursor!=null){
                if (cursor.moveToFirst())
                    movieList = new MovieList(cursor);
                cursor.close();
            }
        }

        if (movieList != null){
            tvTitle.setText(movieList.getJudulFilm());
            tvOverview.setText(movieList.getDeskripsiFilm());
            String tanggal = movieList.getTanggalFilm();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date date = dateFormat.parse(tanggal);
                SimpleDateFormat date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                String tanggalRilis = date_format.format(date);
                tvDate.setText(tanggalRilis);
            }catch(ParseException e){
                e.printStackTrace();
            }
            Picasso.with(this)
                    .load("http://image.tmdb.org/t/p/w500/"+movieList.getFotoFilm())
                    .into(posterMovie);
        }
    }
}
