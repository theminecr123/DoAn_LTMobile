package com.DoAn_Mobile.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.DoAn_Mobile.Fragments.HomeFragment;
import com.DoAn_Mobile.Fragments.SongFragment;
import com.DoAn_Mobile.Fragments.KhoahocFragment;

public class VpagerAdapter extends FragmentStatePagerAdapter{


    public VpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new KhoahocFragment();
            case 2:
                return new SongFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
