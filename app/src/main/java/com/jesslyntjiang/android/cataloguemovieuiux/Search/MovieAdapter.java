package com.jesslyntjiang.android.cataloguemovieuiux.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jesslyntjiang.android.cataloguemovieuiux.R;
import com.jesslyntjiang.android.cataloguemovieuiux.Search.MovieList;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<MovieList> listMovies = new ArrayList<>();

    public MovieAdapter(Context context){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setData(ArrayList<MovieList> items){
        listMovies = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieList item){
        listMovies.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        listMovies.clear();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }


    @Override
    public int getCount() {
        if (listMovies == null){
            return 0;
        }
        return listMovies.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MovieList getItem(int position) {
        return listMovies.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_row_movies, null);
            holder.tvJudul = convertView.findViewById(R.id.tv_judul_film);
            holder.tvDesc = convertView.findViewById(R.id.tv_deskripsi_film);
            holder.tvTanggal = convertView.findViewById(R.id.tv_tanggal_film);
            holder.gambarFilm = convertView.findViewById(R.id.img_movie);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvJudul.setText(listMovies.get(position).getJudulFilm());
        holder.tvDesc.setText(listMovies.get(position).getDeskripsiFilm());
        String Tanggal = listMovies.get(position).getTanggalFilm();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = dateFormat.parse(Tanggal);
            SimpleDateFormat date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String tanggalRilis = date_format.format(date);
            holder.tvTanggal.setText(tanggalRilis);
        }catch(Exception e){
            e.printStackTrace();
        }
        Picasso.with(context).load("http://image.tmdb.org/t/p/w154/"+listMovies.get(position).getFotoFilm()).placeholder(context.getResources().getDrawable(R.drawable.ic_image_black_24dp)).error(context.getResources().getDrawable(R.drawable.ic_image_black_24dp)).into(holder.gambarFilm);
        return convertView;
    }


    static class ViewHolder{
        public TextView tvJudul;
        public TextView tvDesc;
        public TextView tvTanggal;
        public ImageView gambarFilm;
    }
}
