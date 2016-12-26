package com.charryteam.charryproject.fragment.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.adapter.DownLoadAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownLoadFragment extends Fragment {


    public DownLoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_down_load, container, false);
        initView(ret);
        return ret;
    }

    private void initView(View ret) {
        TabLayout tabLayout = ((TabLayout) ret.findViewById(R.id.downloae_tabLayout));
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("歌曲");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("歌手");
        tabLayout.addTab(tab2);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        final ViewPager viewPager = ((ViewPager) ret.findViewById(R.id.download_viewPager));
        List<Fragment> list = new ArrayList<>();
        DownLoadSongFragment downLoadSongFragment = new DownLoadSongFragment();
        list.add(downLoadSongFragment);
        DownLoadSingerFragment downLoadSingerFragment = new DownLoadSingerFragment();
        list.add(downLoadSingerFragment);
        DownLoadAdapter adapter = new DownLoadAdapter(getChildFragmentManager(),list);
        viewPager.setAdapter(adapter);
        //这里是viewpager滑动时，tab也跟着滑动
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //选中tab时，viewpager也跟着变动
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
