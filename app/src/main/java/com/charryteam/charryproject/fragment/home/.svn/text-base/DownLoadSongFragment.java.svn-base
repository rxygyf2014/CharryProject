package com.charryteam.charryproject.fragment.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.adapter.Home_Local_Music_Song_Adapter;
import com.charryteam.charryproject.javabean.Home_Local_Music;
import com.charryteam.charryproject.utils.Home_Local_MusicList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownLoadSongFragment extends Fragment {


    private ListView listview;

    public DownLoadSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_down_load_song, container, false);
        initView(ret);

        return ret;
    }

    private void initView(View ret) {
        listview = ((ListView) ret.findViewById(R.id.downloadsond_listview));
    }


}
