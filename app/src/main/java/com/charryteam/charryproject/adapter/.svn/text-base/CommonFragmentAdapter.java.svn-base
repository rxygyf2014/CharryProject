package com.charryteam.charryproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 15-12-5.
 */
public class CommonFragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment> list;
    public CommonFragmentAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null) {
            ret=list.size();
        }
        return ret;
    }
}
