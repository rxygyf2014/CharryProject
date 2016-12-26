package com.charryteam.charryproject.fragment.discover.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by xiabaikui on 2015/12/5.
 */
public class DiscoverViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<ImageView> list;

    public DiscoverViewPagerAdapter(Context context, List<ImageView> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % list.size();
        ViewGroup parent = (ViewGroup) list.get(position).getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(list.get(position));

        return list.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        position=position%list.size();
        container.removeView(list.get(position));
    }
}
