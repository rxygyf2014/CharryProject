package com.charryteam.charryproject.fragment.home;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.adapter.Home_Local_Music_Song_Adapter;
import com.charryteam.charryproject.adapter.LocalAdapter;
import com.charryteam.charryproject.javabean.Home_Local_Music;
import com.charryteam.charryproject.utils.Home_Local_MusicList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalFragment extends Fragment {


    private ListView local_listview;
    private TextView local_song_txt;
    private TextView local_singer_txt;

    public LocalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_local, container, false);
        initView(ret);
        return ret;
    }

    private void initView(View ret) {
        TabLayout tabLayout = ((TabLayout) ret.findViewById(R.id.local_tabLayout));
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("歌手");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("歌曲");
        tabLayout.addTab(tab2);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        final ViewPager viewPager = ((ViewPager) ret.findViewById(R.id.local_viewPager));
        List<Fragment> list = new ArrayList<>();
        LocalSongFragment localSongFragment = new LocalSongFragment();
        list.add(localSongFragment);
        LocalSingerFragment localSingerFragment = new LocalSingerFragment();
        list.add(localSingerFragment);
        LocalAdapter adapter = new LocalAdapter(getChildFragmentManager(),list);
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
