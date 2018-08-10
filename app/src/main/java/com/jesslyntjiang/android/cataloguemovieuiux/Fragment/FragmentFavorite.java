package com.jesslyntjiang.android.cataloguemovieuiux.Fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jesslyntjiang.android.cataloguemovieuiux.Adapter.CursorAdapter;
import com.jesslyntjiang.android.cataloguemovieuiux.R;

import static com.jesslyntjiang.android.cataloguemovieuiux.Database.DatabaseContract.CONTENT_URI;

public class FragmentFavorite extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    RecyclerView recyclerView_fav;
    private Cursor cursorr;
    private CursorAdapter cursorAdapter;
    private FragmentHome fragmentHome;
    private View view;
    private Bundle bundle;


    public FragmentFavorite(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView_fav = view.findViewById(R.id.rv_list_favorite);
        recyclerView_fav.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView_fav.setHasFixedSize(true);

        cursorAdapter = new CursorAdapter(this.getActivity());
        cursorAdapter.setMovieList(cursorr);
        recyclerView_fav.setAdapter(cursorAdapter);

        new loadDataMovie().execute();

        update(view);

    }


    public void update(View view){
        swipeContainer = view.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new loadDataMovie().execute();
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }



    class loadDataMovie extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(
                    CONTENT_URI, null, null, null, null);
        }


        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            cursorr = cursor;
            cursorAdapter.setMovieList(cursorr);
            cursorAdapter.notifyDataSetChanged();
        }
    }
}
