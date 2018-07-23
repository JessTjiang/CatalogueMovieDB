package com.jesslyntjiang.android.cataloguemovieuiux.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.jesslyntjiang.android.cataloguemovieuiux.R;

import java.util.ArrayList;

public class FragmentSearch extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieList>>, View.OnClickListener {

    ListView lv;
    EditText edtJudul;
    ImageView gambar;
    Button btnCari;
    MovieAdapter adapter;
    public final static String EXTRA_MOVIE = "extra_movie";
    private View view;

    public FragmentSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_search, container, false);
        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();
        lv = view.findViewById(R.id.listviewMovies);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                MovieList movieList = (MovieList)adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), DetailMovie.class);

                intent.putExtra(DetailMovie.EXTRA_JUDUL, movieList.getJudulFilm());
                intent.putExtra(DetailMovie.EXTRA_TANGGAL, movieList.getTanggalFilm());
                intent.putExtra(DetailMovie.EXTRA_RATING_ANGKA, movieList.getPerhitunganRating());
                intent.putExtra(DetailMovie.EXTRA_RATING_PEMIRSA, movieList.getRatingFilm());
                intent.putExtra(DetailMovie.EXTRA_DESKRIPSI, movieList.getDeskripsiFilm());
                intent.putExtra(DetailMovie.EXTRA_GAMBAR, movieList.getFotoFilm());

                startActivity(intent);
            }
        });

        edtJudul = view.findViewById(R.id.et_cari);
        gambar = view.findViewById(R.id.img_movie);
        btnCari = view.findViewById(R.id.btn_cari);

        btnCari.setOnClickListener(this);
        String judul_film = edtJudul.getText().toString();



        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE, judul_film);

        getLoaderManager().initLoader(0, bundle, FragmentSearch.this);

        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieList>> onCreateLoader(int i, Bundle bundle) {
        String judulFilm = "";
        if(bundle != null){
            judulFilm = bundle.getString(EXTRA_MOVIE);
        }
        return new MovieAsynctask(getActivity(), judulFilm);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieList>> loader, ArrayList<MovieList> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieList>> loader) {
        adapter.setData(null);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_cari){
            String judulFilm = edtJudul.getText().toString();
            if(TextUtils.isEmpty(judulFilm)){
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_MOVIE, judulFilm);
            getLoaderManager().restartLoader(0, bundle, FragmentSearch.this);
        }
    }

}
