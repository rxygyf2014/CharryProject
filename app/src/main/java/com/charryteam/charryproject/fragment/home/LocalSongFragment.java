package com.charryteam.charryproject.fragment.home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.charryteam.charryproject.MainActivity;
import com.charryteam.charryproject.R;
import com.charryteam.charryproject.adapter.Home_Local_Music_Song_Adapter;
import com.charryteam.charryproject.javabean.Home_Local_Music;
import com.charryteam.charryproject.utils.FragmentChangeHelper;
import com.charryteam.charryproject.utils.Home_Local_MusicList;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalSongFragment extends Fragment {
    private ListView listview;
    private TextView musicNumber;
    private MainActivity activity;
    private Button localSong_Scann;
    private FragmentChangeHelper helper;

    public LocalSongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = ((MainActivity) context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FragmentChangeHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_local_song, container, false);
        initView(ret);
        initListener();
        initData();
        return ret;
    }


    private void initData() {
        List<Home_Local_Music> songData = Home_Local_MusicList.getMusicData(getActivity());
        Home_Local_Music_Song_Adapter adapter = new Home_Local_Music_Song_Adapter(songData, getActivity());
        listview.setAdapter(adapter);
        musicNumber.setText("共" + adapter.getCount() + "首");
        //将数据存入数据库中方便使用
    }

    /**
     * 添加监听
     */
    private void initListener() {
        localSong_Scann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalSongScanFragment localSongScanFragment = new LocalSongScanFragment();
                helper.setTargetFragment(localSongScanFragment);
                helper.setTagFragment(localSongScanFragment.toString());
                helper.setIsClearBackStack(false);
                activity.changeFragment(helper);
            }
        });
    }


    private void initView(View ret) {
        listview = ((ListView) ret.findViewById(R.id.localsong_listview));
        musicNumber = ((TextView) ret.findViewById(R.id.local_song_num_txt));
        localSong_Scann = ((Button) ret.findViewById(R.id.localsong_btn_scann));
    }
}
