package com.kalviandev.applokasi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kalviandev.applokasi.fragment.KategoriFragment;
import com.kalviandev.applokasi.fragment.LokasiFragment;
import com.kalviandev.applokasi.fragment.MapFragment;

import java.util.ArrayList;

/**
 * Created by root on 21/09/16.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> data;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentAdapter(FragmentManager fm,ArrayList<String> data){
        super(fm);
        this.data=data;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new KategoriFragment();
            case 1 :
                return new LokasiFragment();
            case 2 :
                return new MapFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position);
    }
}
