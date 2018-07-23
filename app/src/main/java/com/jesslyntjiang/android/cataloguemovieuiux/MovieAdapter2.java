package com.jesslyntjiang.android.cataloguemovieuiux;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jesslyntjiang.android.cataloguemovieuiux.Search.DetailMovie;
import com.jesslyntjiang.android.cataloguemovieuiux.Search.MovieList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieAdapter2 extends RecyclerView.Adapter<MovieAdapter2.ViewHolder>{

    private List<MovieList> movieLists;
    private Context context;
    private MovieHelper movieHelper;

    private ArrayList<MovieList> movieListses;

    public ArrayList<MovieList> getMovieLists(){
        return movieListses;
    }
    public void setMovieLists(ArrayList<MovieList> movieLists){
        this.movieListses = movieLists;
    }


    public MovieAdapter2(List<MovieList> movieLists, Context context) {
        // generate constructors to initialise the List and Context objects
        this.movieLists = movieLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_now_playing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // this method will bind the data to the ViewHolder from hence it'll be shown to other Views
        final MovieList movList = movieLists.get(position);
        holder.title.setText(movList.getJudulFilm());
        holder.overview.setText(movList.getDeskripsiFilm());

        String release_date = movList.getTanggalFilm();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("E, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            holder.date.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500/"+movList.getFotoFilm())
                .into(holder.poster);

        holder.btnFavorite.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                movieHelper = new MovieHelper(context);
                MovieList movieList = getMovieLists().get(position);

                movieHelper.open();

                movieHelper.beginTransaction();
                if (!movieHelper.checkData(movieList.getJudulFilm())){
                    try{
                        movieHelper.insertTransaction(movieList);
                        movieHelper.setTransactionSuccess();
                        Toast.makeText(context, context.getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        String TAG = "TAG";
                        Log.e(TAG, "onClick: Exception");
                    }
                } else{
                    Toast.makeText(context, context.getResources().getString(R.string.available), Toast.LENGTH_SHORT).show();
                }
                movieHelper.endTransaction();

                movieHelper.close();
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share: "+movList.getJudulFilm(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.cardViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieList movieList = movieLists.get(position);
                Intent Intent = new Intent(context, DetailMovie.class);
                Intent.putExtra(DetailMovie.EXTRA_JUDUL, movieList.getJudulFilm());
                Intent.putExtra(DetailMovie.EXTRA_DESKRIPSI, movieList.getDeskripsiFilm());
                Intent.putExtra(DetailMovie.EXTRA_GAMBAR, movieList.getFotoFilm());
                Intent.putExtra(DetailMovie.EXTRA_TANGGAL, movieList.getTanggalFilm());
                Intent.putExtra(DetailMovie.EXTRA_RATING_ANGKA, movieList.getRatingFilm());
                Intent.putExtra(DetailMovie.EXTRA_RATING_PEMIRSA, movieList.getPerhitunganRating());
                context.startActivity(Intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView title, overview, date;
        public ImageView poster;
        public Button btnFavorite, btnShare;
        public LinearLayout cardViewDetail;

        public ViewHolder(View itemView) {
            super(itemView);

            title       = itemView.findViewById(R.id.tv_item_name);
            poster      = itemView.findViewById(R.id.img_item_photo);
            overview    = itemView.findViewById(R.id.tv_item_overview);
            date        = itemView.findViewById(R.id.tv_item_date);
            btnFavorite = itemView.findViewById(R.id.btn_set_favorite);
            btnShare    = itemView.findViewById(R.id.btn_set_share);
            cardViewDetail    = itemView.findViewById(R.id.card_view_movie);
        }

    }

}
