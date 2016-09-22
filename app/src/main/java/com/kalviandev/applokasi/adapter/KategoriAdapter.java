package com.kalviandev.applokasi.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalviandev.applokasi.R;
import com.kalviandev.applokasi.model.Kategori;

import java.util.ArrayList;

/**
 * Created by root on 21/09/16.
 */
public class KategoriAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Kategori> data;
    private Context c;

    public KategoriAdapter(Context c,ArrayList<Kategori> data){
        this.data=data;
        this.c=c;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.row_kategori,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //masukan masing2 data ke dalam item
        Kategori x= data.get(position);
        MyViewHolder mHolder=(MyViewHolder)holder;
        mHolder.txtJudul.setText(x.getKategoriNama());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    //siapkan viewholder

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView txtJudul;
        RelativeLayout container;

        public MyViewHolder(View v) {
            super(v);
            imageView=(ImageView)v.findViewById(R.id.imageKategori);
            txtJudul=(TextView)v.findViewById(R.id.txtTitle);
            container=(RelativeLayout)v.findViewById(R.id.container);

        }
    }





}
