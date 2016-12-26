package com.charryteam.charryproject.fragment.discover;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.charryteam.charryproject.MainActivity;
import com.charryteam.charryproject.PlayMusicActivity;
import com.charryteam.charryproject.R;
import com.charryteam.charryproject.fragment.discover.adapter.TopMusicsAdapter;
import com.charryteam.charryproject.javabean.TopMusics;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.charryteam.charryproject.utils.DoubleFormat;
import com.charryteam.charryproject.utils.ExternalStorageHelper;
import com.charryteam.charryproject.utils.FragmentChangeHelper;
import com.charryteam.charryproject.utils.HttpConnectionhelper;
import com.charryteam.charryproject.utils.JsonUtils_Discover;
import com.charryteam.charryproject.widget.CustomListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * discover详情页面
 * A simple {@link Fragment} subclass.
 */
public class DiscoverDetailsFragment extends Fragment {


    private CustomListView listview;
    private List<String> list;
    private Toolbar toolbar;
    private MainActivity activity;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView AlldownLoad;
    private FragmentChangeHelper helper;
    private int best_id;
    private String img;
    private String content;
    private String title;
    private ImageView iv_img;
    private TextView tv_summary;
    private BitmapSinglton singlton;
    private BitmapUtils bitmapUtils;
    private List<TopMusics> detialsList;
    private PlayMusicListener playMusicListener;
    private int currentItemMusic = 0;
    private int totalItemMusic = 0;

    private TopMusicsAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "请连接网络!", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String result = (String) msg.obj;
                    detialsList = JsonUtils_Discover.getDiscoverDetialsList(result);
                    totalItemMusic = detialsList.size();
                    adapter = new TopMusicsAdapter(getActivity(), detialsList, handler);
                    handler.sendEmptyMessage(3);
                    break;
                case 3:
                    listview.setAdapter(adapter);
                    break;
                case 4:
                    Toast.makeText(getActivity(), "你傻啊，登录后点赞", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_SHORT).show();

                    break;
                case 6:
                    Toast.makeText(getActivity(), "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    Toast.makeText(getActivity(), "下载完成第" + currentItemMusic + "首歌", Toast.LENGTH_SHORT).show();
                    startDownLoadAllMUsic();
                    break;
                case 8:
                    Toast.makeText(getActivity(), "全部下载完成", Toast.LENGTH_SHORT).show();
                    break;
                case 9:
                    Toast.makeText(getActivity(), "下载失败,下载下一首", Toast.LENGTH_SHORT).show();
                    startDownLoadAllMUsic();
                    break;
            }
        }
    };
    private ImageView iv_like_unlike;
    private String rootPath;

    public DiscoverDetailsFragment() {
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
        helper = new FragmentChangeHelper();
        singlton = BitmapSinglton.getSinglton();
        bitmapUtils = singlton.getBitmapUtils();
        Bundle bundle = getArguments();
        best_id = bundle.getInt("best_id");
        img = bundle.getString("img");
        content = bundle.getString("content");
        title = bundle.getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover_details, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        listview = ((CustomListView) view.findViewById(R.id.concentration_detials_listview));
        collapsingToolbarLayout = ((CollapsingToolbarLayout) view.findViewById(R.id.collapsingtoolbarLayout));
        //设置title
        collapsingToolbarLayout.setTitle(title);
        //设置ToolBar
        toolbar = ((Toolbar) view.findViewById(R.id.concentration_detials_toolbar));
        activity.setSupportActionBar(toolbar);
        iv_img = ((ImageView) view.findViewById(R.id.concentration_detials_iv_img));
        tv_summary = ((TextView) view.findViewById(R.id.concentration_detials_tv_summary));
        //点赞和下载全部歌曲
        iv_like_unlike = ((ImageView) view.findViewById(R.id.concentration_detials_iv_like_unlike));
        AlldownLoad = ((TextView) view.findViewById(R.id.concentration_detials_tv_alldownload));
        //获得存储路径
        rootPath = ExternalStorageHelper.getDirectroy(getActivity());
        list = new ArrayList<>();
        setHasOptionsMenu(true);
        initData();
        initListener();
    }

    /**
     * 添加点击事件的监听
     */
    private void initListener() {
        //暂时不可使用
        iv_like_unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (false) {
                } else {
                    handler.sendEmptyMessage(4);
                }
            }
        });
        //下载全部歌曲
        AlldownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        startDownLoadAllMUsic();
                    }
                }.start();
            }
        });
        //listview条目监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                ImageView iv_music_select = (ImageView) view.findViewById(R.id.topmusic_select);
                iv_music_select.setVisibility(View.VISIBLE);
                TopMusics topMusics = detialsList.get(position);
                final String music_id = topMusics.getMusic_id();
                final String artist = topMusics.getArtist();
                final String path = Constants.URL_Song_JSON + music_id;
                if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
                    new Thread() {
                        @Override
                        public void run() {
                            String result = HttpConnectionhelper.doGetSumbit(path);
                            if (result != null) {
                                List<Map<String, String>> songJson = JsonUtils_Discover.getSongJson(result);
                                Map<String, String> song_map = songJson.get(0);
                                final String file_path = song_map.get("file_path");
                                final String title = song_map.get("title");
                                final String cover_path = song_map.get("cover_path");
                                String mp3Name = title + ".mp3";
                                Intent intent = new Intent(getActivity().getApplicationContext(), PlayMusicActivity.class);
                                intent.putExtra("artist", artist);
                                intent.putExtra("title", title);
                                intent.putExtra("music_id", music_id);
                                intent.putExtra("cover_path", cover_path);
                                intent.putExtra("mp3Name", mp3Name);
                                intent.putExtra("file_path", file_path);
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
    }


    /**
     * 设置返回按钮
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return true;
    }


    private void initData() {

        //设置内容文字
        tv_summary.setText(content);
        //设置图片
        String imgPath = Constants.URL_image01 + img;
        bitmapUtils.display(iv_img, imgPath);
        new Thread() {
            @Override
            public void run() {

                if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {

                    String itemsPath = Constants.URL_bestlist_item + best_id;
                    String result = HttpConnectionhelper.doGetSumbit(itemsPath);
                    if (result != null && result.length() > 0) {
                        Message message = Message.obtain();
                        message.obj = result;
                        message.what = 2;
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

    public interface PlayMusicListener {
        void play(String path);
    }

    private void startDownLoadAllMUsic() {
        if (currentItemMusic == totalItemMusic) {
            handler.sendEmptyMessage(8);
        } else {
            TopMusics topMusics = detialsList.get(currentItemMusic++);
            String music_id = topMusics.getMusic_id();
            final String path = Constants.URL_Song_JSON + music_id;
            Log.i("tag", "-----songjsonPath" + path);
            downLoadAllMusic(path);
        }
    }

    /**
     * 下载所有歌曲
     *
     * @param downloadpath
     */
    private void downLoadAllMusic(final String downloadpath) {
        if (HttpConnectionhelper.isNetWorkConntected(getActivity())) {
            new Thread() {
                @Override
                public void run() {
                    String result = HttpConnectionhelper.doGetSumbit(downloadpath);
                    if (result != null) {
                        List<Map<String, String>> songJson = JsonUtils_Discover.getSongJson(result);
                        Map<String, String> song_map = songJson.get(0);
                        String file_path = song_map.get("file_path");
                        String title = song_map.get("title");
                        Log.i("tag", "file_path--->" + file_path + "----title" + title);
                        HttpUtils httpUtils = new HttpUtils();
                        String mp3Name = title + ".mp3";
                        String storagePath = rootPath + File.separator + mp3Name;
                        Log.i("tag", "mp3path--->" + storagePath);
                        httpUtils.download(file_path, storagePath, true, false, new RequestCallBack<File>() {
                            @Override
                            public void onSuccess(ResponseInfo<File> responseInfo) {
                                Log.i("tag", "----->onSuccess");
                                handler.sendEmptyMessage(7);
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Log.i("tag", "----->onFailure");
                                handler.sendEmptyMessage(9);
                            }

                            @Override
                            public void onLoading(long total, long current, boolean isUploading) {
                                super.onLoading(total, current, isUploading);
                                double d = (total / (1024 * 1024.0));
                                String format = DoubleFormat.getDecimalFormat().format(d);
                                double d1 = (current / (1024 * 1024.0));
                                String curr = DoubleFormat.getDecimalFormat().format(d1);
                                Log.i("tag", "---->文件大小：" + format + "Mb,---->下载进度：" + curr + "Mb");
                            }
                        });
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                }
            }.start();
        } else {
            handler.sendEmptyMessage(0);
        }
    }
}
