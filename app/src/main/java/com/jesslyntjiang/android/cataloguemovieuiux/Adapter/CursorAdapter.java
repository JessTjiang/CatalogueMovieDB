package com.jesslyntjiang.android.cataloguemovieuiux.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jesslyntjiang.android.cataloguemovieuiux.R;
import com.jesslyntjiang.android.cataloguemovieuiux.Search.DetailMovie;
import com.jesslyntjiang.android.cataloguemovieuiux.Search.MovieList;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CursorAdapter extends RecyclerView.Adapter<CursorAdapter.CategoryViewHolder>{
    private Cursor movieList;
    private List<MovieList> movieLists;
    private Context context;
    private ArrayList<MovieList> movieListses;
    public CursorAdapter(Context context){
        this.context = context;
    }
    public void setMovieList(Cursor movieList){
        this.movieList = movieList;
    }

    public ArrayList<MovieList> getMovieLists(){
        return movieListses;
    }
    public void setMovieLists(ArrayList<MovieList> movieLists){
        this.movieListses = movieLists;
    }


    public CursorAdapter(List<MovieList> movieLists, Context context) {
        // generate constructors to initialise the List and Context objects
        this.movieLists = movieLists;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_now_playing, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    public MovieList getItem(int position){
        if (!movieList.moveToPosition(position)){
            throw new IllegalStateException("Invalid Position");
        }
        return new MovieList(movieList);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        final MovieList movList = getItem(position);
        holder.tvJudul.setText(movList.getJudulFilm());
        holder.tvDeskripsi.setText(movList.getDeskripsiFilm());
        String release_date = movList.getTanggalFilm();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("E, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            holder.tvTanggal.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(context, DetailMovie.class);
                Intent.putExtra(DetailMovie.EXTRA_JUDUL, movList.getJudulFilm());
                Intent.putExtra(DetailMovie.EXTRA_DESKRIPSI, movList.getDeskripsiFilm());
                Intent.putExtra(DetailMovie.EXTRA_GAMBAR, movList.getFotoFilm());
                Intent.putExtra(DetailMovie.EXTRA_TANGGAL, movList.getTanggalFilm());
                Intent.putExtra(DetailMovie.EXTRA_RATING_ANGKA, movList.getRatingFilm());
                Intent.putExtra(DetailMovie.EXTRA_RATING_PEMIRSA, movList.getPerhitunganRating());
                context.startActivity(Intent);
            }
        });

        Picasso.with(context.getApplicationContext()).load("http://image.tmdb.org/t/p/w500/"+movList.getFotoFilm()).into(holder.imgGambar);


    }

    @Override
    public int getItemCount() {
        if (movieList==null) return 0;
        return movieList.getCount();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        CardView cardview;
        TextView tvJudul;
        TextView tvTanggal;
        TextView tvDeskripsi;
        ImageView imgGambar;
        TextView tvRating;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.card_view);
            tvJudul = itemView.findViewById(R.id.tv_item_name);
            tvTanggal = itemView.findViewById(R.id.tv_item_date);
            tvDeskripsi = itemView.findViewById(R.id.tv_item_overview);
            imgGambar = itemView.findViewById(R.id.img_item_photo);
        }
    }

    
}
