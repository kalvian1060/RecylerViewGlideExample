package com.kalviandev.applokasi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kalviandev.applokasi.R;
import com.kalviandev.applokasi.helpers.RbHelper;
import com.kalviandev.applokasi.model.Lokasi;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by root on 22/09/16.
 */
public class LokasiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Lokasi> mData;
        private Context mC;

    public LokasiAdapter(Context c,ArrayList<Lokasi> data){
        mData=data;
        mC=c;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mC).inflate(R.layout.row_lokasi,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Lokasi lokasi=mData.get(position);
        MyViewHolder mHolder= (MyViewHolder) holder;

        mHolder.lokasiNama.setText(lokasi.getLokasiNama());
        mHolder.jenisNama.setText(lokasi.getJenisNama());
        mHolder.desaNama.setText(lokasi.getDesanNama()+" - ");
        mHolder.kecamatanNama.setText(lokasi.getKecamatanNama());
        mHolder.lokasiSee.setText("Seen " + lokasi.getLokasiSee());

        RbHelper.downloadImage(mC,RbHelper.BASE_URL_IMAGE+lokasi.getLokasiGambar(),mHolder.lokasiGambar);


    }

    @Override
    public int getItemCount() {
        return mData== null ? 0 : mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView lokasiGambar;
        private TextView lokasiNama,jenisNama,desaNama,kecamatanNama,lokasiSee;
        private LinearLayout container;

        public MyViewHolder(View v) {
            super(v);
            lokasiGambar=(ImageView)v.findViewById(R.id.lokasiGambar);
            lokasiNama=(TextView)v.findViewById(R.id.lokasiNama);
            jenisNama=(TextView)v.findViewById(R.id.jenisNama);
            desaNama=(TextView)v.findViewById(R.id.desaNama);
            kecamatanNama=(TextView)v.findViewById(R.id.kecamatanNama);
            lokasiSee=(TextView)v.findViewById(R.id.lokasiSee);
            container=(LinearLayout)v.findViewById(R.id.container);

        }




    }
}
