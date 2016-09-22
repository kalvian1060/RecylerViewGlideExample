package com.kalviandev.applokasi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalviandev.applokasi.R;
import com.kalviandev.applokasi.adapter.KategoriAdapter;
import com.kalviandev.applokasi.helpers.RbHelper;
import com.kalviandev.applokasi.model.Kategori;

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
public class KategoriFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvKategori;
    private ArrayList<Kategori> data;
    private KategoriAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.kategorifragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
        getData();
    }
    private void setupView(View view){
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                //set refresh
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(false);
                        getData();
                    }
                });

            }
        });
        rvKategori= (RecyclerView) view.findViewById(R.id.rvKategori);
        rvKategori.setHasFixedSize(true);
        rvKategori.setLayoutManager(new LinearLayoutManager(getActivity()));
        data=new ArrayList<>();

    }

    public void getData(){
        data.clear();
        String url= RbHelper.BASE_URL + "kategori";

        //create request okhttp3
        final Request request=new Request.Builder()
                .url(url)
                .build();

        //debug URL
        RbHelper.pre("url  : " + url);

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
                //Run swipeRefresh To UiThread
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
            public void onResponse(Call call, final Response response) throws IOException {
                //respone from server
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

                //checking response result
                if(!response.isSuccessful()){
                    throw new IOException("Unexcepted code" + response);
                }


                //read data
                final String responeData=response.body().string();
                RbHelper.pre("response : " + responeData);



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

                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jObj=jsonArray.getJSONObject(i);

                                    Kategori k=new Kategori();
                                    k.setIdKategori(jObj.getString("id_kategori"));
                                    k.setKategoriNama(jObj.getString("kategori_nama"));
                                    k.setKategoriImage(jObj.getString("kategori_image"));

                                    //add model data to arraylist
                                    data.add(k);

                                }

                            }


                            adapter=new KategoriAdapter(getActivity(),data);
                            rvKategori.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            RbHelper.pesan(getActivity(),"Error parsing JSON");
                        }catch (Exception e){
                            e.printStackTrace();
                            RbHelper.pesan(getActivity(),"Error ambil data");
                        }
                    }
                });



            }
        });


    }


}
