package com.charryteam.charryproject.fragment.discover;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.charryteam.charryproject.MainActivity;
import com.charryteam.charryproject.PlayMusicActivity;
import com.charryteam.charryproject.R;
import com.charryteam.charryproject.fragment.discover.adapter.TopArtistsAdapter;
import com.charryteam.charryproject.fragment.discover.adapter.TopMusicsAdapter;
import com.charryteam.charryproject.javabean.TopArtists;
import com.charryteam.charryproject.javabean.TopMusics;
import com.charryteam.charryproject.utils.Constants;
import com.charryteam.charryproject.utils.ExternalStorageHelper;
import com.charryteam.charryproject.utils.HttpConnectionhelper;
import com.charryteam.charryproject.utils.JsonUtils_Discover;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 排行页面
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment implements DbUtils.DbUpgradeListener {


    private View view;
    private View view_head;
    private RelativeLayout top_musicPeople;
    private RelativeLayout top_hotMusic;
    private GridView top_gridView;
    private PullToRefreshListView top_pullToRefresh;
    private ListView lv;
    private DbUtils dbUtils;
    private List<TopArtists> artistsList;
    private List<TopMusics> topMusicsList;
    private TopArtistsAdapter adapter;
    private TopMusicsAdapter topMusicsAdapter;
    private PlayMusicListener playMusicListener;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "请连接网络!", Toast.LENGTH_SHORT).show();
                    if (top_pullToRefresh.isRefreshing()) {
                        top_pullToRefresh.onRefreshComplete();
                    }
                    break;
                case 1:
                    Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
                    if (top_pullToRefresh.isRefreshing()) {
                        top_pullToRefresh.onRefreshComplete();
                    }
                    break;
                case 2:
                    String json = (String) msg.obj;
                    artistsList = JsonUtils_Discover.getDiscoverTopArtistsList(json);
                    try {
                        dbUtils.saveAll(artistsList);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    topMusicsList = JsonUtils_Discover.getDiscoverTopMusicsList(json);
                    try {
                        dbUtils.saveAll(topMusicsList);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(3);
                    break;
                case 3:
                    Log.i("tag", "----list.size()：" + artistsList.size());
                    adapter = new TopArtistsAdapter(getActivity(), artistsList, handler);
                    topMusicsAdapter = new TopMusicsAdapter(getActivity(), topMusicsList, handler);
                    top_gridView.setAdapter(adapter);
                    top_pullToRefresh.setAdapter(topMusicsAdapter);
                    if (top_pullToRefresh.isRefreshing()) {
                        top_pullToRefresh.onRefreshComplete();
                    }
                    break;
                case 4:
                    adapter.notifyDataSetChanged();
                    topMusicsAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    if (top_pullToRefresh.isRefreshing()) {
                        top_pullToRefresh.onRefreshComplete();
                    }
                    break;
                case 5:
                    if (top_pullToRefresh.isRefreshing()) {
                        top_pullToRefresh.onRefreshComplete();
                    }
                    Toast.makeText(getActivity(), "已更新至最新", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(getActivity(), "SDCard状态错误，保存到手机", Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    Toast.makeText(getActivity(), "下载成功", Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    Toast.makeText(getActivity(), "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private String rootPath;
    private MainActivity activity;


    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = ((MainActivity) context);
        //no use
        if (activity instanceof PlayMusicListener) {
            playMusicListener = (PlayMusicListener) activity;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建数据库
        dbUtils = DbUtils.create(getActivity(), Constants.DBNAME, Constants.DB_VERSION, this);
        //创建表
        try {
            dbUtils.createTableIfNotExist(TopMusics.class);
            dbUtils.createTableIfNotExist(TopArtists.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_top, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化控件，加载头部布局
     *
     * @param view
     */
    private void initView(View view) {
        artistsList = new ArrayList<>();
        topMusicsList = new ArrayList<>();
        top_pullToRefresh = ((PullToRefreshListView) view.findViewById(R.id.top_pulltorefresh));
        lv = top_pullToRefresh.getRefreshableView();
        //设置头部布局
        view_head = LayoutInflater.from(getActivity()).inflate(R.layout.top_header, null);
        top_musicPeople = ((RelativeLayout) view_head.findViewById(R.id.top_header_re_musicpeople));
        top_hotMusic = ((RelativeLayout) view_head.findViewById(R.id.top_header_re_hotmusic));
        top_gridView = ((GridView) view_head.findViewById(R.id.top_header_gv));
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        view_head.setLayoutParams(layoutParams);
        lv.addHeaderView(view_head);
        //下载存储路径
        rootPath = ExternalStorageHelper.getDirectroy(getActivity());
        pullLayout();
        initListener();
    }

    /**
     * 添加监听
     */
    private void initListener() {
        //点击条目开始下载
        top_pullToRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //选中条目标记
                ImageView iv_music_select = (ImageView) view.findViewById(R.id.topmusic_select);
                iv_music_select.setVisibility(View.VISIBLE);
                Log.i("tag", "position-->" + position);
                position = position - 2;
                TopMusics topMusics = topMusicsList.get(position);
                final String music_id = topMusics.getMusic_id();
                final String artist = topMusics.getArtist();
                final String path = Constants.URL_Song_JSON + music_id;
                if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
                    new Thread() {
                        @Override
                        public void run() {
                            String result = HttpConnectionhelper.doGetSumbit(path);
                            if (result != null) {
                                Log.i("tag", "---->result----songdownload-->" + result.toString());
                                List<Map<String, String>> songJson = JsonUtils_Discover.getSongJson(result);
                                Map<String, String> song_map = songJson.get(0);
                                final String title = song_map.get("title");
                                final String file_path = song_map.get("file_path");
                                final String cover_path = song_map.get("cover_path");
                                Log.i("tag", "file_path--->" + file_path);
                                String mp3Name = title + ".mp3";
                                Intent intent = new Intent(getActivity().getApplicationContext(),
                                        PlayMusicActivity.class);
                                intent.putExtra("artist", artist);
                                intent.putExtra("title", title);
                                intent.putExtra("music_id", music_id);
                                intent.putExtra("cover_path", cover_path);
                                intent.putExtra("mp3Name",mp3Name);
                                intent.putExtra("file_path",file_path);
                                getActivity().startActivity(intent);
                            } else {
                                handler.sendEmptyMessage(1);
                            }
                        }
                    }.start();

                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        });

        //绑定刷新监听事件
        top_pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //  更改数据源
                new Thread() {
                    @Override
                    public void run() {

                        if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {

                            String result = HttpConnectionhelper.doGetSumbit(Constants.URL_rank_main);
                            List<TopArtists> pulltorefresh_list = JsonUtils_Discover.getDiscoverTopArtistsList(result);
                            List<TopMusics> pullToRefresh_musicList = JsonUtils_Discover.getDiscoverTopMusicsList(result);
                            if (artistsList.containsAll(pulltorefresh_list) && topMusicsList.containsAll(pullToRefresh_musicList)) {
                                handler.sendEmptyMessage(5);
                            } else {
                                artistsList.clear();
                                artistsList.addAll(pulltorefresh_list);
                                handler.sendEmptyMessage(4);
                            }
                        } else {
                            handler.sendEmptyMessage(0);
                        }
                    }
                }.start();

            }
        });

    }

    /**
     * 设置下拉动画提醒
     */
    private void pullLayout() {
        ILoadingLayout loadingLayoutProxy = top_pullToRefresh.getLoadingLayoutProxy(true, false);
        loadingLayoutProxy.setPullLabel("要刷新吗？");
        loadingLayoutProxy.setReleaseLabel("开始刷新");
        loadingLayoutProxy.setRefreshingLabel("拼命加载中......");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_cd);
        loadingLayoutProxy.setLoadingDrawable(drawable);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        loadingLayoutProxy.setLastUpdatedLabel(sdf.format(new Date()));
    }

    /**
     * 加载数据
     */
    private void initData() {
        try {
            List<TopMusics> dbUtilsAll_TopMusics = dbUtils.findAll(TopMusics.class);
            List<TopArtists> dbUtilsAll_TopArtists = dbUtils.findAll(TopArtists.class);
            if (dbUtilsAll_TopMusics != null && dbUtilsAll_TopArtists != null && dbUtilsAll_TopMusics.size() > 0 && dbUtilsAll_TopArtists.size() > 0) {
                artistsList.addAll(dbUtilsAll_TopArtists);
                topMusicsList.addAll(dbUtilsAll_TopMusics);
                handler.sendEmptyMessage(3);
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
                            String result = HttpConnectionhelper.doGetSumbit(Constants.URL_rank_main);
                            if (result != null && result.length() > 0) {
                                Message message = Message.obtain();
                                message.obj = result;
                                message.what = 2;
                                Log.i("tag", "---->result" + result);
                                handler.sendMessage(message);
                            } else {
                                handler.sendEmptyMessage(1);
                            }
                        } else {
                            handler.sendEmptyMessage(0);
                        }
                    }
                }.start();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(DbUtils dbUtils, int i, int i1) {
        DbUtils.create(getActivity(), Constants.DBNAME, Constants.DB_VERSION + 1, this);
    }

    public interface PlayMusicListener {
        void play(String path);
    }
}
