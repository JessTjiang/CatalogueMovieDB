package com.jesslyntjiang.android.favmovie;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.jesslyntjiang.android.favmovie.DatabaseContract.MovieColumns.IMAGE;
import static com.jesslyntjiang.android.favmovie.DatabaseContract.MovieColumns.OVERVIEW;
import static com.jesslyntjiang.android.favmovie.DatabaseContract.MovieColumns.RELEASE_YEAR;
import static com.jesslyntjiang.android.favmovie.DatabaseContract.MovieColumns.TITLE;
import static com.jesslyntjiang.android.favmovie.DatabaseContract.getColumnString;


public class MovieAdapter extends CursorAdapter{

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_now_playing, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor!=null){
            TextView tvTitle = view.findViewById(R.id.tv_item_name);
            TextView tvOverview = view.findViewById(R.id.tv_item_overview);
            TextView tvDate = view.findViewById(R.id.tv_item_date);
            ImageView imgPoster = view.findViewById(R.id.img_item_photo);

            tvTitle.setText(getColumnString(cursor, TITLE));
            tvOverview.setText(getColumnString(cursor,OVERVIEW));
            tvDate.setText(getColumnString(cursor,RELEASE_YEAR));
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w500/"+getColumnString(cursor,IMAGE))
                    .into(imgPoster);
        }
    }
}
