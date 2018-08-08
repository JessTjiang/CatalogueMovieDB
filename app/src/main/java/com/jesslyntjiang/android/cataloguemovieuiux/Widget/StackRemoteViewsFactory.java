package com.jesslyntjiang.android.cataloguemovieuiux.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jesslyntjiang.android.cataloguemovieuiux.Database.MovieHelper;
import com.jesslyntjiang.android.cataloguemovieuiux.R;
import com.jesslyntjiang.android.cataloguemovieuiux.Search.MovieList;
import com.jesslyntjiang.android.cataloguemovieuiux.Widget.ImageBannerWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String URL = "https://image.tmdb.org/t/p/w500/";
    private int mAppWidgetId;
    private Cursor cursor;
    private Context mContext;
    private List<Bitmap> mWidgetItems = new ArrayList<>();
    public static ArrayList<MovieList> movie = new ArrayList<>();

    private void getDataMovie(Context context){
        MovieHelper movieHelper = new MovieHelper(context);
        movieHelper.open();
        movie = movieHelper.getMovieData();
        movieHelper.close();
    }

    public StackRemoteViewsFactory(Context context, Intent intent){
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        getDataMovie(mContext);
    }

    @Override
    public void onDataSetChanged() {
        getDataMovie(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movie.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bitmap = null;

        try{
            bitmap = Glide.with(mContext)
                    .load(URL+movie.get(i).getFotoFilm())
                    .asBitmap()
                    .error(new ColorDrawable(mContext.getResources().getColor(R.color.colorAccent)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);
        Bundle bundle = new Bundle();
        bundle.putInt(ImageBannerWidget.EXTRA_ITEM, i);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundle);

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
