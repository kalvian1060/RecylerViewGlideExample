package com.kalviandev.applokasi;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kalviandev.applokasi.adapter.FragmentAdapter;
import com.kalviandev.applokasi.adapter.RvAdapter;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {
    private MaterialTabHost tabHost;
    private ViewPager viewPager;
    private ArrayList<String> data;
    private FragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView(){
        tabHost=(MaterialTabHost)findViewById(R.id.materialTabHost);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    tabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //mengisi
        data=new ArrayList<String>();
        data.add("Kategori");
        data.add("Lokasi");
        data.add("Peta");


        //koneki dengan adapter
        adapter=new FragmentAdapter(getSupportFragmentManager(),data);
        viewPager.setAdapter(adapter);

        //tambahkan judul
        for(int i=0; i<adapter.getCount(); i++){
            tabHost.addTab(tabHost.newTab()
            .setText(adapter.getPageTitle(i))
            .setTabListener(this));
        }

    }


    @Override
    public void onTabSelected(MaterialTab tab) {
            viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }


}
