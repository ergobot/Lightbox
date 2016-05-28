package com.photonic3d.lightbox.fragments.sliceview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SliceViewPagerAdapter extends FragmentPagerAdapter{
    Context context = null;

    public SliceViewPagerAdapter(Context context, FragmentManager fragmentManager, int totalSlices){
        super(fragmentManager);
        this.context = context;
        this.totalSlices = totalSlices;
    }

    public SliceViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    int totalSlices = 0;

    @Override
    public Fragment getItem(int position) {
        return SliceFragment.newInstance(position);
    }


    @Override
    public int getCount() {
        return totalSlices;
    }
}
