package com.DoAn_Mobile.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.DoAn_Mobile.Fragments.HomeFragment;
import com.DoAn_Mobile.Fragments.ProfileFragment;
import com.DoAn_Mobile.Fragments.FindFragment;
import com.DoAn_Mobile.Fragments.WatchFragment;

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
                return new WatchFragment();
            case 2:
                return new FindFragment();
            case 3:
                return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
