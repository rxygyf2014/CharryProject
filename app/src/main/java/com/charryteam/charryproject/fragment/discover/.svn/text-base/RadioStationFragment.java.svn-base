package com.charryteam.charryproject.fragment.discover;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.fragment.discover.adapter.RadioStationListViewAdapter;
import com.charryteam.charryproject.javabean.RadioStation;
import com.charryteam.charryproject.utils.Constants;
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

/**
 * 电台页面
 * A simple {@link Fragment} subclass.
 */
public class RadioStationFragment extends Fragment implements DbUtils.DbUpgradeListener {
    private PullToRefreshListView pullToRefreshListView;
    private List<RadioStation> list;
    private RadioStationListViewAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "请连接网络!", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }
                    Toast.makeText(getActivity(), "网络异常!", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String result = (String) msg.obj;
                    list = JsonUtils_Discover.getDiscoverRadioStation(result);
                    //保存数据到数据库表中
                    try {
                        dbUtils.saveAll(list);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(5);
                    break;
                case 3:
                    adapter.notifyDataSetChanged();
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }
                    Toast.makeText(getActivity(), "已是最新数据", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    adapter = new RadioStationListViewAdapter(getActivity(), list, handler);
                    pullToRefreshListView.setAdapter(adapter);
                    break;
            }
        }
    };
    private DbUtils dbUtils;

    public RadioStationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建数据库
        dbUtils = DbUtils.create(getActivity(), Constants.DBNAME, Constants.DB_VERSION, this);
        //创建表
        try {
            dbUtils.createTableIfNotExist(RadioStation.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_radio_station, container, false);
        initView(view);
        initData();
        return view;
    }

    /**
     * 加载电台封面图片展示
     */
    private void initData() {
        try {
            List<RadioStation> dbUtilsAll = dbUtils.findAll(RadioStation.class);
            if (dbUtilsAll != null & dbUtilsAll.size() > 0) {
                list.addAll(dbUtilsAll);
                handler.sendEmptyMessage(5);
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
                            String result = HttpConnectionhelper.doGetSumbit(Constants.URL_radiolist);
                            if (result != null && result.length() > 0) {
                                Message message = Message.obtain();
                                message.what = 2;
                                message.obj = result;
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

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        list = new ArrayList<>();
        pullToRefreshListView = ((PullToRefreshListView) view.findViewById(R.id.radiostation_pulltorefresh));
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullLayout();
        initlistener();
    }

    /**
     * 设置刷新监听
     */
    private void initlistener() {

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RadioStation radioStation = list.get(position);
                int radio_id = radioStation.getRadio_id();
                String img = radioStation.getImg();
                Bundle bundle = new Bundle();
                bundle.putString("img",img);
                bundle.putString("radio_id", radio_id + "");
                Intent intent = new Intent(getActivity(), RadioActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //绑定刷新监听事件
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //  更改数据源
                new Thread() {
                    @Override
                    public void run() {

                        if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
                            String result = HttpConnectionhelper.doGetSumbit(Constants.URL_radiolist);
                            List<RadioStation> radioStationList = JsonUtils_Discover.getDiscoverRadioStation(result);
                            if (list.containsAll(radioStationList)) {
                                handler.sendEmptyMessage(4);
                            } else {
                                list.clear();
                                list.addAll(radioStationList);
                                handler.sendEmptyMessage(3);
                            }
                        } else {
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
            }
        });
    }

    /**
     * 设置下拉显示动画
     */
    private void pullLayout() {
        ILoadingLayout loadingLayoutProxy = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        loadingLayoutProxy.setPullLabel("要刷新吗？");
        loadingLayoutProxy.setReleaseLabel("开始刷新");
        loadingLayoutProxy.setRefreshingLabel("拼命加载中......");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_cd);
        loadingLayoutProxy.setLoadingDrawable(drawable);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        loadingLayoutProxy.setLastUpdatedLabel(sdf.format(new Date()));
    }

    @Override
    public void onUpgrade(DbUtils dbUtils, int i, int i1) {
        DbUtils.create(getActivity(), Constants.DBNAME, Constants.DB_VERSION + 1, this);
    }
}
