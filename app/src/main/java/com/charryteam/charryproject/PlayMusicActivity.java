package com.charryteam.charryproject;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.charryteam.charryproject.adapter.PlayViewPagerAdapter;
import com.charryteam.charryproject.fragment.play.PlayFragment;
import com.charryteam.charryproject.fragment.play.PlayListAndHistoryFragment;
import com.charryteam.charryproject.fragment.play.PlaySimilarSongFragment;
import com.charryteam.charryproject.service.PlayService;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_pointsLayout;
    private ViewPager play_viewpager;
    private List<Fragment> playViewpagerList;
    private ImageView[] arr_points = null;
    private ImageView iv_points;
    private Toolbar toolbar;
    private PlayViewPagerAdapter adapter;
    private ImageView iv_PlayOrPause;
    private boolean isPlaying;
    private PlayService playService;
    private SeekBar play_seekBar;
    //    定义本地的广播
    private LocalBroadcastManager localBroadcastManager;
    private TextView tv_totalTime;
    private TextView tv_currentTime;
    //    广播接收器
    private TimePeceiver timePeceiver;
    private String artist;
    private String title;
    private String music_id;
    private String cover_path;
    private String mp3Name;
    private String file_path;
    private File downloadingMediaFile;
    private ImageView iv_back;
    private ImageView iv_share;
    private ImageView iv_shoucang;
    private String img_path;
    private BitmapSinglton singlton;
    private BitmapUtils bitmapUtils;
    private CoordinatorLayout coordinatorLayout;
private ReceiverFromSimilarSongPragment receiverFromSimilarSongPragment;
    private PlayFragment playFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
//        本地广播接收器
        localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());

        initView();

        initService();
        timePeceiver = new TimePeceiver();
        localBroadcastManager.registerReceiver(timePeceiver, new IntentFilter(Constants.ACTION_SEEKBAR_PROGRESS));
        receiverFromSimilarSongPragment=new ReceiverFromSimilarSongPragment();
    localBroadcastManager.registerReceiver(receiverFromSimilarSongPragment,new IntentFilter(Constants.SIMLIARTOPLAYMUSIC));
    }

    //这是在launchMode为singleTask的时候，替代了oncreate的方法时候执行了的，但是服务会被系统杀死，所以即使我是singleTask
//    还是写上吧，保险起见
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);

    }


    @Override
    protected void onResume() {
        super.onResume();
        play();
    }

    private void play() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            artist = intent.getExtras().getString("artist");
            title = intent.getExtras().getString("title");
            music_id = intent.getExtras().getString("music_id");
            cover_path = intent.getExtras().getString("cover_path");
            mp3Name = intent.getExtras().getString("mp3Name");
            file_path = intent.getExtras().getString("file_path");
            img_path = Constants.URL_image02 + cover_path;
            bitmapUtils.display(coordinatorLayout, img_path);
            new Thread() {
                @Override
                public void run() {
                    downloadMusic(file_path, mp3Name);
                }
            }.start();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    String play_path = (String) msg.obj;
                    if (playService != null) {
                        if (play_viewpager!=null) {
                            play_viewpager.clearOnPageChangeListeners();
                        }
                        inintViewPager();
                        //viewpager到的监听
                        initListener();
                        //该方法就执行了MediaPlayer的声明周期了
                        if (playService.isPlayingMusic()) {
                            playService.resetMusic();
                            iv_PlayOrPause.setSelected(true);
                            playService.preparePlayMusic(play_path);
                        } else {
                            iv_PlayOrPause.setSelected(true);
                            playService.preparePlayMusic(play_path);
                        }
                    }
                    break;
                case 2:
                    Toast.makeText(PlayMusicActivity.this,"点赞",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private MyServiceConnection serviceConnection;

    private void initService() {
        final Intent intent = new Intent(this, PlayService.class);
         /*  startService(intent);*/
        serviceConnection = new MyServiceConnection();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean b = PlayMusicActivity.this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                if (b) {
                    Log.i("tag", "----------------------->service启动成功");
                } else {
                    Log.i("tag", "------------------------>service启动失败");
                }
            }
        }, 3000);

    }

    /*控制音乐播放或者暂停*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.paly_ivPlayOrPause:
                boolean b = playService.isMusicPlaying();
                if (b) {
                    playService.pauseMusic();
                    iv_PlayOrPause.setSelected(false);
                } else {
                    playService.startMusic();
                    iv_PlayOrPause.setSelected(true);
                }
                break;
        }
    }

    /**
     * 服务连接
     */
    public class MyServiceConnection implements ServiceConnection {

        /**
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayService.MyBinder binder = (PlayService.MyBinder) service;
            playService = binder.getPlayMusicBindService();
            play();
        }

        /**
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

    }

    private void initListener() {
        play_viewpager.setCurrentItem(1);
        //小圆点完了之后，才添加监听
        play_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < arr_points.length; i++) {
                    arr_points[i].setEnabled(true);
                }
                arr_points[position].setEnabled(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        singlton = BitmapSinglton.getSinglton();
        bitmapUtils = singlton.getBitmapUtils();
        coordinatorLayout = ((CoordinatorLayout) findViewById(R.id.play_root_bg));
        play_viewpager = ((ViewPager) findViewById(R.id.play_viewpager));
        ll_pointsLayout = ((LinearLayout) findViewById(R.id.ll_points));
        iv_PlayOrPause = ((ImageView) findViewById(R.id.paly_ivPlayOrPause));
        play_seekBar = ((SeekBar) findViewById(R.id.play_seekBar));
        tv_totalTime = (TextView) findViewById(R.id.tv_totalTime);
        tv_currentTime = (TextView) findViewById(R.id.tv_currentTime);
        iv_back = ((ImageView) findViewById(R.id.play_iv_back));
        iv_share = ((ImageView) findViewById(R.id.play_iv_share));
        iv_shoucang = ((ImageView) findViewById(R.id.play_iv_shoucang));
        if (isPlaying) {
            iv_PlayOrPause.setSelected(true);//需要暂停
        } else {
            iv_PlayOrPause.setSelected(false);
        }
        iv_PlayOrPause.setOnClickListener(this);
        //进度条的拖拉拽事件
        seekBarEvent();
        //返回主页面
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PlayMusicActivity.this, MainActivity.class);
                setResult(1111, myIntent);
                startActivity(myIntent);
            }
        });
        //点赞收藏
        iv_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(2);
            }
        });
        //分享
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initPoints();
    }

    /*  进度条的拖拉拽事件*/
    private void seekBarEvent() {
        play_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //拖动结束，获取当前的位置
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = play_seekBar.getProgress();
                Intent intent = new Intent(Constants.ACTION_SEEKBAR_SEEKTO);
                //携带当前的进度
                intent.putExtra(Constants.ACTION_SEEKBAR_PROGRESS_CUR, progress);
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    private void inintViewPager() {
        playViewpagerList = new ArrayList<>();
        PlaySimilarSongFragment playSimilarSongFragment = new PlaySimilarSongFragment();
        Bundle bundle0 = new Bundle();
        bundle0.putString("music_id", music_id);
        playSimilarSongFragment.setArguments(bundle0);
        playViewpagerList.add(playSimilarSongFragment);
        playFragment = new PlayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("artist", artist);
        bundle.putString("cover_path", cover_path);
        playFragment.setArguments(bundle);
        playViewpagerList.add(playFragment);
        PlayListAndHistoryFragment playListAndHistoryFragment = new PlayListAndHistoryFragment();
        playViewpagerList.add(playListAndHistoryFragment);
        adapter = new PlayViewPagerAdapter(getSupportFragmentManager(), this, playViewpagerList);
        play_viewpager.setAdapter(adapter);
    }

    private void initPoints() {
        arr_points = new ImageView[3];
        for (int i = 0; i < arr_points.length; i++) {
            iv_points = new ImageView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(10, 10);
            iv_points.setPadding(10, 10, 10, 10);
            iv_points.setLayoutParams(layoutParams);
            iv_points.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv_points.setBackgroundResource(R.drawable.guide_point_selector);
            ll_pointsLayout.addView(iv_points);
            arr_points[i] = iv_points;
        }
        arr_points[1].setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent myIntent = new Intent(PlayMusicActivity.this, MainActivity.class);
            setResult(1111, myIntent);
            startActivity(myIntent);
            return true;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent myIntent = new Intent(PlayMusicActivity.this, MainActivity.class);
            setResult(1111, myIntent);
            startActivity(myIntent);
        }
        return super.onKeyDown(keyCode, event);
    }

    //    格式化时间
    public String formatTime(int s) {//传递进来是毫秒
        StringBuffer builder = new StringBuffer();
        s = s / 1000;//秒
        int m = s / 60;//分钟
        s = s % 60;//秒数
        builder.append(m / 10).append(m % 10).append(":").append(s / 10).append(s % 10);
        return builder.toString();

    }

    //广播接收器：接受总时长和当前的时长，
    class TimePeceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int cur = intent.getIntExtra(Constants.ACTION_SEEKBAR_PROGRESS_CUR, 0);
            int max = intent.getIntExtra(Constants.ACTION_SEEKBAR_PROGRESS_MAX, 0);
//        设置最大进度
            play_seekBar.setMax(max);
//        设置当前的进度
            play_seekBar.setProgress(cur);
            Log.i("-------", "当前进度：" + cur);
            tv_currentTime.setText(formatTime(cur));
            tv_totalTime.setText(formatTime(max));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消广播
        localBroadcastManager.unregisterReceiver(timePeceiver);
        localBroadcastManager.unregisterReceiver(receiverFromSimilarSongPragment);
    }

    //首先通过这个方法取得流
    private InputStream getDataFromURL(String url) {
        URLConnection connection = null;
        try {
            connection = new URL(url).openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(10000);
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //然后开始缓冲数据
    private void downloadMusic(String mediaUrl, String fileName) {
        InputStream is = getDataFromURL(mediaUrl);
        int totalKbRead = 0;
        if (is == null) {
            return;
        }
        //创建音乐文件，如果已经有了，把之前的删掉
        downloadingMediaFile = new File(PlayMusicActivity.this.getCacheDir(), fileName);
        if (downloadingMediaFile.exists()) {
            String absolutePath = downloadingMediaFile.getAbsolutePath();
            Message message = Message.obtain();
            message.what = 0;
            message.obj = absolutePath;
            mHandler.sendMessage(message);
        } else {
            //下面就是从网络获取数据写到文件中，下载到200Kb以后开始播放
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(downloadingMediaFile);
                byte buf[] = new byte[10 * 1024];
                int totalBytesRead = 0;
                int len;
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    totalBytesRead += len;
                    totalKbRead = totalBytesRead / 1024;
                    //缓冲到200Kb以后开始播放,边下载边播放
                    if (totalKbRead >= 200) {
                    }
                }
                Log.i("tag", "-----------下载完成");
                String absolutePath = downloadingMediaFile.getAbsolutePath();
                Message message = Message.obtain();
                message.what = 0;
                message.obj = absolutePath;
                mHandler.sendMessage(message);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class ReceiverFromSimilarSongPragment extends BroadcastReceiver{
/*
*     intent.putExtra("filePath",filePath);
                              intent.putExtra("cover_path",cover_path);
                              intent.putExtra("lrc_path",lrc_path);
                              intent.putExtra("title", title);
                              intent.putExtra("artist_id",artist_id);
                              intent.putExtra("mp3Name", mp3Name);*/
        @Override
        public void onReceive(Context context, Intent intent) {
            final String filePath = intent.getStringExtra("filePath");
           cover_path = intent.getStringExtra("cover_path");
              title = intent.getStringExtra("title");
            artist = intent.getStringExtra("artist");
            String artist_id = intent.getStringExtra("artist_id");
            final String mp3Name = intent.getStringExtra("mp3Name");
            img_path=Constants.URL_image02+cover_path;
            bitmapUtils.display(coordinatorLayout, img_path);
            if (filePath!=null&&mp3Name!=null&&!filePath.equals("")&&!mp3Name.equals("")){
                new Thread(){
                    @Override
                    public void run() {
                        downloadMusic(filePath,mp3Name);
                    }
                }.start();
            }

        }
    }
}
