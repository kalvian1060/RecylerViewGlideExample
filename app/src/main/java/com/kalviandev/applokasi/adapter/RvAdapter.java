package com.kalviandev.applokasi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalviandev.applokasi.R;
import com.kalviandev.applokasi.model.Kategori;

import java.util.List;

/**
 * Created by root on 21/09/16.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.KategoriHolder>{

    private List<Kategori> kategoriList;

    public RvAdapter(List<Kategori> list){
        this.kategoriList=list;

    }

    @Override
    public KategoriHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_kategori,parent,false);
        KategoriHolder holder=new KategoriHolder(viewItem);
        return holder;
    }

    @Override
    public void onBindViewHolder(KategoriHolder holder, int position) {
        Kategori kategori=kategoriList.get(position);
        holder.txtTitle.setText(kategori.getKategoriNama());



    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public class KategoriHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView txtTitle;



        public KategoriHolder(View view){
            super(view);
            imageView=(ImageView)view.findViewById(R.id.imageKategori);
            txtTitle=(TextView)view.findViewById(R.id.txtTitle);

        }


    }


}
