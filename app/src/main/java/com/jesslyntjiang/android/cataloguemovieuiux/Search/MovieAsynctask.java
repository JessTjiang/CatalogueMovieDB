package com.jesslyntjiang.android.cataloguemovieuiux.Search;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.jesslyntjiang.android.cataloguemovieuiux.BuildConfig;
import com.jesslyntjiang.android.cataloguemovieuiux.Search.MovieList;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieAsynctask extends AsyncTaskLoader<ArrayList<MovieList>> {
    ArrayList<MovieList> mData;
    private String judulFilm;
    private boolean mHasResult = false;

    public MovieAsynctask(final Context context, String judulFilm) {
        super(context);

        onContentChanged();
        this.judulFilm = judulFilm;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) forceLoad();
        else if (mHasResult) deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieList> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(mHasResult){
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;

    @Override
    public ArrayList<MovieList> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieList> movieListses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+judulFilm;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movies = list.getJSONObject(i);
                        MovieList movieList = new MovieList(movies);
                        movieListses.add(movieList);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //do nothing.
            }
        });
        return movieListses;
    }

    protected void onReleaseResources(ArrayList<MovieList> data){
        //nothing to do.
    }
}
