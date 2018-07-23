package com.jesslyntjiang.android.cataloguemovieuiux.Search;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jesslyntjiang.android.cataloguemovieuiux.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailMovie extends AppCompatActivity {

    public static String EXTRA_JUDUL            = "extra_judul";
    public static String EXTRA_DESKRIPSI        = "extra_deskripsi";
    public static String EXTRA_RATING_ANGKA     = "extra_rating_angka";
    public static String EXTRA_RATING_PEMIRSA   = "extra_rating_pemirsa";
    public static String EXTRA_TANGGAL          = "extra_tanggal";
    public static String EXTRA_GAMBAR           = "extra_gambar";

    private TextView tvJudul, tvDeskripsi, tvRating, tvTanggal;
    private ImageView gambar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvJudul = findViewById(R.id.tv_judul_film);
        tvDeskripsi = findViewById(R.id.tv_deskripsi_film);
        tvTanggal = findViewById(R.id.tv_tanggal_masuk);
        tvRating = findViewById(R.id.rating_pemirsa);
        gambar = findViewById(R.id.poster_movie);

        String judul = getIntent().getStringExtra(EXTRA_JUDUL);
        String rating = getIntent().getStringExtra(EXTRA_RATING_PEMIRSA);
        String rating_angka = getIntent().getStringExtra(EXTRA_RATING_ANGKA);
        String deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        String poster = getIntent().getStringExtra(EXTRA_GAMBAR);
        String tanggal = getIntent().getStringExtra(EXTRA_TANGGAL);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = dateFormat.parse(tanggal);
            SimpleDateFormat date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String tanggalRilis = date_format.format(date);
            tvTanggal.setText(tanggalRilis);
        }catch(ParseException e){
            e.printStackTrace();
        }

        tvJudul.setText(judul);
        tvDeskripsi.setText(deskripsi);
        tvRating.setText(rating+ " viewers ( Rating : " +rating_angka+"/10)");
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500/"+poster).into(gambar);

    }
}
