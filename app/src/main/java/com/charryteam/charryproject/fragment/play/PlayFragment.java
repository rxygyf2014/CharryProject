package com.charryteam.charryproject.fragment.play;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.charryteam.charryproject.PlayMusicActivity;
import com.charryteam.charryproject.R;
import com.charryteam.charryproject.javabean.PlaySong;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.charryteam.charryproject.widget.ArtistRotateImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment implements DbUtils.DbUpgradeListener {
    private DbUtils dbUtils;
    private PlayMusicActivity playMusicActivity;
    private String artist;
    private String cover_path;
    private String title;
    private ArtistRotateImageView iv_rotate;
    private TextView tv_songer;
    private TextView tv_singer;
    private BitmapSinglton singlton;
    private BitmapUtils bitmapUtils;
    private Animation operatingAnim;

    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        playMusicActivity = ((PlayMusicActivity) context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbUtils = DbUtils.create(getActivity(), Constants.DBNAME, Constants.DB_VERSION, this);
        try {
            dbUtils.createTableIfNotExist(PlaySong.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            artist = bundle.getString("artist");
            cover_path = bundle.getString("cover_path");
            Log.i("tag", "------>" + title + artist + cover_path);
        } else {
            Log.i("tag", "----------->playFragmentbundle");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play2, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        iv_rotate = ((ArtistRotateImageView) view.findViewById(R.id.play_iv_mirror));
        tv_songer = ((TextView) view.findViewById(R.id.play_tv_songname));
        tv_singer = ((TextView) view.findViewById(R.id.play_tv_singer));
        singlton = BitmapSinglton.getSinglton();
        bitmapUtils = singlton.getBitmapUtils();
        initData();
    }

    //添加数据
    private void initData() {
        String path = Constants.URL_image02 + cover_path;
        Log.i("tga", "------->" + path);
        bitmapUtils.display(iv_rotate, path);
        tv_singer.setText(artist);
        tv_songer.setText(title);
        operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.image_ratate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (operatingAnim != null) {
            iv_rotate.startAnimation(operatingAnim);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (operatingAnim != null) {
            iv_rotate.startAnimation(operatingAnim);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        iv_rotate.clearAnimation();
    }

    @Override
    public void onUpgrade(DbUtils dbUtils, int i, int i1) {
        dbUtils = DbUtils.create(getActivity(), Constants.DBNAME, Constants.DB_VERSION + 1, this);
    }
}
