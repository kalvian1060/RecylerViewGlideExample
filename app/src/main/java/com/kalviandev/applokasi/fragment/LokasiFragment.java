package com.kalviandev.applokasi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalviandev.applokasi.R;
import com.kalviandev.applokasi.adapter.LokasiAdapter;
import com.kalviandev.applokasi.helpers.RbHelper;
import com.kalviandev.applokasi.model.Lokasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by root on 21/09/16.
 */
public class LokasiFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Lokasi> data;
    private RecyclerView rvLokasi;
    private LokasiAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.lokasifragment,container,false);
        return view;

    }

    private void setupView(View view){
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(false);
                        getData();
                    }
                });
            }
        });
        rvLokasi=(RecyclerView)view.findViewById(R.id.rvLokasi);
        rvLokasi.setHasFixedSize(true);
        rvLokasi.setLayoutManager(new LinearLayoutManager(getActivity()));
        data=new ArrayList<>();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
        getData();

    }
    private void getData(){
        data.clear();

        String url= RbHelper.BASE_URL + "lokasi/popular";

        //create request okhttp3
        final Request request=new Request.Builder()
                .url(url)
                .build();

        //degub URL
        RbHelper.pre("url : " + url);

        //swipe refresh
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //Run swipeRefresh to UiThread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });

                        //Display Message Error to User
                        RbHelper.pesan(getActivity(),"Error " +e.getMessage());

                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                //respone from server
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


                //checking respond result
                if(!response.isSuccessful()){
                    throw new IOException("unexcepted code " + response);

                }

                //read data
                final  String responeData=response.body().string();
                RbHelper.pre("respone " + responeData);



                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject json=new JSONObject(responeData);

                            //check result
                            boolean hasil=json.getBoolean("result");
                            String msg=json.getString("msg");


                           if(hasil){
                            JSONArray jsonArray=json.getJSONArray("data");

                            for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj=jsonArray.getJSONObject(i);

                                    Lokasi l=new Lokasi();
                                    l.setIdLokasi(jObj.getString("id_lokasi"));
                                    l.setLokasiNama(jObj.getString("lokasi_nama"));
                                    l.setLokasiSee(jObj.getString("lokasi_see"));
                                    l.setLokasiGambar(jObj.getString("lokasi_gambar"));
                                    l.setDesanNama(jObj.getString("desaNama"));
                                    l.setKecamatanNama(jObj.getString("kecamatanNama"));
                                    l.setLokasiLatitude(jObj.getString("lokasi_latitude"));
                                    l.setLokasiLongtitude(jObj.getString("lokasi_longitude"));
                                    l.setJenisNama(jObj.getString("jenisNama"));
                                    l.setLokasiAlamat(jObj.getString("lokasi_alamat"));
                                    l.setLokasiFasilitas(jObj.getString("lokasi_fasilitas"));



                                    //add to arraylist
                                    data.add(l);

                                }

                            }
                           adapter=new LokasiAdapter(getActivity(),data);
                            rvLokasi.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            RbHelper.pesan(getActivity(),"Error parsing JSON");
                        }catch (Exception e){
                            e.printStackTrace();
                            RbHelper.pesan(getActivity(),"Error Ambil Data");
                        }
                    }
                });

            }
        });


    }


}
