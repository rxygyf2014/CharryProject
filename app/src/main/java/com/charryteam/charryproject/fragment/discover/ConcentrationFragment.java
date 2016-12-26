package com.charryteam.charryproject.fragment.discover;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.charryteam.charryproject.MainActivity;
import com.charryteam.charryproject.R;
import com.charryteam.charryproject.fragment.discover.adapter.ConcentrationListViewAdapter;
import com.charryteam.charryproject.fragment.discover.adapter.DiscoverViewPagerAdapter;
import com.charryteam.charryproject.javabean.DiscoverBestList;
import com.charryteam.charryproject.utils.Constants;
import com.charryteam.charryproject.utils.FragmentChangeHelper;
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
 * 精选页面
 * A simple {@link Fragment} subclass.
 */
public class ConcentrationFragment extends Fragment implements DbUtils.DbUpgradeListener {


    private int[] images = {R.mipmap.discover_vp1, R.mipmap.discover_vp2, R.mipmap.discover_vp3, R.mipmap.discover_vp1, R.mipmap.discover_vp2, R.mipmap.discover_vp3};
    private View view;
    private ViewPager viewPager;

    private ImageButton ib_left;
    private ImageButton ib_right;
    private ImageView iv_musicColumn;
    private ImageView iv_musicGenre;
    private LinearLayout linearLayout;
    private boolean isRunning = true;
    private int currentItemNum = 0;//当前条目
    private List<DiscoverBestList> discoverBestLists;
    private ConcentrationListViewAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    if (isRunning) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        handler.sendEmptyMessageDelayed(0, 2000);
                    }
                    break;
                case 1:
                    Toast.makeText(getActivity(), "请连接网络!", Toast.LENGTH_SHORT).show();
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }
                    break;
                case 2:
                    Toast.makeText(getActivity(), "网络请求超时!", Toast.LENGTH_SHORT).show();
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }
                    break;
                case 3:
                    String result = (String) msg.obj;
                    discoverBestLists = JsonUtils_Discover.getDiscoverBestList(result);
                    try {
                        dbUtils.saveAll(discoverBestLists);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    Log.i("tag", "---->" + discoverBestLists.toString());
                    handler.sendEmptyMessage(6);
                    break;
                case 4:
                    Toast.makeText(getActivity(), "已是最新数据", Toast.LENGTH_SHORT).show();
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }
                    break;
                case 5:
                    adapter.notifyDataSetChanged();
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    adapter = new ConcentrationListViewAdapter(getActivity(), discoverBestLists, handler);
                    pullToRefreshListView.setAdapter(adapter);
                    break;
                case 7:
                    //保存新数据到数据库
                    String obj = (String) msg.obj;
                    List<DiscoverBestList> onScrollList = JsonUtils_Discover.getDiscoverBestList(obj);
                    try {
                        dbUtils.saveAll(onScrollList);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    discoverBestLists.addAll(onScrollList);
                    handler.sendEmptyMessage(8);
                    break;
                case 8:
                    //通知adapter刷新适配器
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    if (pullToRefreshListView.isRefreshing()) {
                        pullToRefreshListView.onRefreshComplete();
                    }

                    break;
                case 9:
                    //更新数据库
                    try {
                        dbUtils.saveAll(discoverBestLists);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(8);
                    break;
            }
        }
    };
    private ImageView[] arr_icon;
    private List<ImageView> list;
    private DbUtils dbUtils;
    private PullToRefreshListView pullToRefreshListView;
    private ListView lv;
    private boolean isDivPage;
    private MainActivity activity;
    private FragmentChangeHelper helper;

    public ConcentrationFragment() {
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
        //创建数据库
        dbUtils = DbUtils.create(getActivity(), Constants.DBNAME, Constants.DB_VERSION, this);
        //创建表
        try {
            dbUtils.createTableIfNotExist(DiscoverBestList.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        helper = new FragmentChangeHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_concentration, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initNetData();
    }

    /**
     * 下载listview的数据
     */
    private void initNetData() {
        try {
            List<DiscoverBestList> dbUtilsAll = dbUtils.findAll(DiscoverBestList.class);
            if (dbUtilsAll != null && dbUtilsAll.size() > 0) {
                discoverBestLists.addAll(dbUtilsAll);
                handler.sendEmptyMessage(6);
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
                            String result = HttpConnectionhelper.doGetSumbit(Constants.URL_bestlist + "offset=" + currentItemNum);
                            if (result != null && result.length() > 0) {
                                Message message = Message.obtain();
                                message.what = 3;
                                message.obj = result;
                                Log.i("tag", "--->result" + result);
                                handler.sendMessage(message);
                            } else {
                                handler.sendEmptyMessage(2);
                            }
                        } else {
                            handler.sendEmptyMessage(1);
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

        pullToRefreshListView = ((PullToRefreshListView) view.findViewById(R.id.concentration_pulltorefreshlistview));
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        lv = pullToRefreshListView.getRefreshableView();
        //设置头部布局
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        View view_head = LayoutInflater.from(getActivity()).inflate(R.layout.concentration_head, null);
        viewPager = ((ViewPager) view_head.findViewById(R.id.concentration_viewpager));
        ib_left = ((ImageButton) view_head.findViewById(R.id.concentration_ib_left));
        ib_right = ((ImageButton) view_head.findViewById(R.id.concentration_ib_right));
        linearLayout = ((LinearLayout) view_head.findViewById(R.id.concentration_ll_point));
        iv_musicColumn = ((ImageView) view_head.findViewById(R.id.concentration_iv_music_column));
        iv_musicGenre = ((ImageView) view_head.findViewById(R.id.concentration_iv_music_genre));
        view_head.setLayoutParams(layoutParams);
        lv.addHeaderView(view_head);
        list = new ArrayList<>();
        discoverBestLists = new ArrayList<>();
        initData();
        //pulltoRefresh的监听
        initlistener();
        pullLayout();
        //开启viewPager图片轮转
        handler.sendEmptyMessageDelayed(0, 2000);
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

    //设置刷新监听
    private void initlistener() {
        /**
         * 条目点击事件跳转到条目详细列表
         * 页面跳转
         */
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("tag", "--->" + discoverBestLists.get(position - 2).toString());
                Log.i("tag", (position - 2) + "条目点击了！");
                DiscoverDetailsFragment fragment = new DiscoverDetailsFragment();
                DiscoverBestList bestList = discoverBestLists.get(position-2);
                int best_id = bestList.getBest_id();
                String content = bestList.getContent();
                String img = bestList.getImg();
                String title = bestList.getTitle();
                Bundle bundle = new Bundle();
                bundle.putInt("best_id", best_id);
                bundle.putString("img", img);
                bundle.putString("content", content);
                bundle.putString("title", title);
                fragment.setArguments(bundle);
                helper.setBundle(bundle);
                helper.setIsClearBackStack(false);
                helper.setTagFragment(fragment.toString());

                helper.setTargetFragment(fragment);
                activity.changeFragment(helper);
            }
        });

        /**
         * 分页
         */
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                /**
                 * 当屏幕停止滚动时为
                 * SCROLL_STATE_IDLE = 0
                 * 当屏幕滚动且用户使用的触碰或手指还在屏幕上时
                 * SCROLL_STATE_TOUCH_SCROLL = 1
                 * 由于用户的操作，屏幕产生惯性滑动时
                 * SCROLL_STATE_FLING = 2
                 */
                if (isDivPage && SCROLL_STATE_IDLE == scrollState) {
                    //  分页请求
                    new Thread() {
                        @Override
                        public void run() {

                            if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {

                                String result = HttpConnectionhelper.doGetSumbit(Constants.URL_bestlist + "offset=" + discoverBestLists.size());
                                //分页新数据
                                if (result != null && result.length() > 0) {
                                    Message message = Message.obtain();
                                    message.obj = result;
                                    message.what = 7;
                                    handler.sendMessage(message);
                                } else {
                                    handler.sendEmptyMessage(2);
                                }
                            } else {
                                handler.sendEmptyMessage(1);
                            }
                        }
                    }.start();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDivPage = ((firstVisibleItem + visibleItemCount) == totalItemCount);
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

                            String result = HttpConnectionhelper.doGetSumbit(Constants.URL_bestlist + "offset=0");
                            List<DiscoverBestList> pulltorefresh_list = JsonUtils_Discover.getDiscoverBestList(result);
                            if (discoverBestLists.containsAll(pulltorefresh_list)) {
                                handler.sendEmptyMessage(4);
                            } else {
                                try {
                                    dbUtils.delete(DiscoverBestList.class, null);
                                    discoverBestLists.clear();
                                    discoverBestLists.addAll(pulltorefresh_list);
                                    handler.sendEmptyMessage(9);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
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
     * 填充数据信息
     */
    private void initData() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(images[i]);
            list.add(imageView);
        }
        DiscoverViewPagerAdapter adapter1 = new DiscoverViewPagerAdapter(getActivity(), list);
        viewPager.setAdapter(adapter1);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % list.size());
        initPoint();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % (list.size() / 2);
                for (int i = 0; i < list.size() / 2; i++) {
                    arr_icon[i].setEnabled(true);
                }
                arr_icon[position].setEnabled(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 添加小圆点
     */
    private void initPoint() {
        arr_icon = new ImageView[images.length / 2];
        for (int i = 0; i < arr_icon.length; i++) {
            ImageView icon = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(5, 5, 5, 5);
            icon.setLayoutParams(params);
            icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            icon.setBackgroundResource(R.drawable.guide_point_selector);
            icon.setEnabled(true);
            arr_icon[i] = icon;
            linearLayout.addView(icon);
            arr_icon[i].setTag(i);
        }
        arr_icon[0].setEnabled(false);
    }

    /**
     * 当fragment切换时，停止图片轮转
     */
    @Override
    public void onStop() {
        super.onStop();
        isRunning = false;
    }

    /**
     * 数据库版本更新时，改变数据库版本
     *
     * @param dbUtils
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(DbUtils dbUtils, int i, int i1) {
        DbUtils.create(getActivity(), Constants.DBNAME, Constants.DB_VERSION + 1, this);
    }
}
