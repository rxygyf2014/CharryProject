package com.charryteam.charryproject.fragment.crossover;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.adapter.CrossoverInfoAdapter;
import com.charryteam.charryproject.javabean.Info;
import com.charryteam.charryproject.utils.Constants;
import com.charryteam.charryproject.utils.HttpConnectionhelper;
import com.charryteam.charryproject.utils.JsonUtils_Crossover;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {
    private View view;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "请连接网络", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "请求数据失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String result = (String) msg.obj;
                    crossoverHotNews.addAll(JsonUtils_Crossover.getCrossoverHotNew(result));
                    handler.sendEmptyMessage(5);
                    break;
                case 3:
                    Toast.makeText(getActivity(), "已经是最新数据", Toast.LENGTH_SHORT).show();
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }
                    break;

                case 4:
                    //加载新的数据
                    String result2 = (String) msg.obj;
                    List<Info> addList = JsonUtils_Crossover.getCrossoverHotNew(result2);
                    crossoverHotNews.addAll(addList);
                    handler.sendEmptyMessage(5);
                    break;
                case 5:
                    adapter.notifyDataSetChanged();
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }
                    break;
                case 6:
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(5);
                    break;
            }
        }
    };
    private PullToRefreshListView pullToRefreshListView;
    private ListView refreshableView;
    private List<Info> crossoverHotNews = new ArrayList<>();
    private CrossoverInfoAdapter adapter;
    private int currentItemNum = 0;//当前条目
    private DetailActivity activity;

    public HotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hot, container, false);

        initView(view);

        return view;
    }

    /**
     * 上网请求数据
     */
    private void initNetData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
                    String result = HttpConnectionhelper.doGetSumbit(Constants.URL_hotnew + "offset=" + currentItemNum + "&hot_new=1");
                    if (result != null && result.length() > 0) {
                        Message message = Message.obtain();
                        message.what = 2;
                        message.obj = result;
                        Log.i("flag", "--->result" + result);
                        handler.sendMessage(message);
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    /**
     * 初始化控件
     */
    private void initView(View view) {
        pullToRefreshListView =
                (PullToRefreshListView) view.findViewById(R.id.crossover_pulltorefreshlistview);
        refreshableView = pullToRefreshListView.getRefreshableView();
        //设置模式
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new CrossoverInfoAdapter(getActivity(), crossoverHotNews);
        refreshableView.setAdapter(adapter);

        //设置头部布局
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        View view_head = LayoutInflater.from(getActivity()).inflate(R.layout.crossover_head, null);
        view_head.setLayoutParams(layoutParams);
        refreshableView.addHeaderView(view_head);

        pullLayout();
        initlistener();
    }

    /**
     * 设置下拉，上拉监听
     */
    private void initlistener() {
        /**
         * 条目点击事件跳转到条目详细activity中
         * 页面跳转
         */
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info info = crossoverHotNews.get(position - 1);
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("uid", info.getUser_uid());
                bundle.putString("create_time", info.getCreate_time());
                bundle.putString("title", info.getTitle());
                bundle.putString("cover", info.getCover());
                bundle.putString("nick", info.getNick());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //设置上拉下拉事件
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                if (refreshView.isHeaderShown()) {
                    //  更改数据源
                    new Thread() {
                        @Override
                        public void run() {
                            if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
                                String result = HttpConnectionhelper.doGetSumbit(Constants.URL_hotnew + "offset=0" + "&hot_new=1");
                                if (result != null && result.length() > 0) {
                                    List<Info> scollList = JsonUtils_Crossover.getCrossoverHotNew(result);
                                    if (scollList.containsAll(crossoverHotNews)) {
                                        handler.sendEmptyMessage(3);
                                    } else {
                                        crossoverHotNews.clear();
                                        crossoverHotNews.addAll(scollList);

                                        Message message = Message.obtain();
                                        message.obj = result;
                                        message.what = 5;
                                        handler.sendMessage(message);
                                    }
                                } else {
                                    //请求数据失败
                                    handler.sendEmptyMessage(1);
                                }
                            } else {
                                //网络连接失败
                                handler.sendEmptyMessage(0);
                            }
                        }
                    }.start();
                } else {
                    //下拉加载
                    new Thread() {
                        @Override
                        public void run() {
                            if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
                                String result = HttpConnectionhelper.doGetSumbit(Constants.URL_hotnew + "offset=" + crossoverHotNews.size() + "&hot_new=1");
                                //分页加载数据
                                if (result != null && result.length() > 0) {
                                    Message message = Message.obtain();
                                    message.obj = result;
                                    message.what = 4;
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
            }
        });
    }

    /**
     * 设置下拉动画提醒
     */
    private void pullLayout() {
        ILoadingLayout loadingLayoutProxy = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        loadingLayoutProxy.setPullLabel("要刷新吗？");
        loadingLayoutProxy.setReleaseLabel("开始刷新");
        loadingLayoutProxy.setRefreshingLabel("拼命加载中......");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_cd);
        loadingLayoutProxy.setLoadingDrawable(drawable);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        loadingLayoutProxy.setLastUpdatedLabel(sdf.format(new Date()));
    }

}

