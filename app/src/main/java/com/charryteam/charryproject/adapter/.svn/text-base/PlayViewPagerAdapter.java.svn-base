package com.charryteam.charryproject.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ChunLin on 2015/12/5.
 */
public class PlayViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> playViewpagerList;

    public PlayViewPagerAdapter(FragmentManager fm, Context context, List<Fragment> playViewpagerList) {
        super(fm);
        this.context = context;
        this.playViewpagerList = playViewpagerList;
    }

    public PlayViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return playViewpagerList.get(position);
    }

    @Override
    public int getCount() {
        return playViewpagerList.size();
    }
}
