package com.charryteam.charryproject.fragment.crossover;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.charryteam.charryproject.MainActivity;
import com.charryteam.charryproject.R;
import com.charryteam.charryproject.javabean.Crossover_picsInfo;
import com.charryteam.charryproject.utils.Constants;
import com.charryteam.charryproject.utils.HttpConnectionhelper;
import com.charryteam.charryproject.utils.JsonUtils_Crossover;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private String uid;
    private String create_time;

    private String title;
    private String cover;
    private String nick;

    private ImageView ImageBack;
    private ImageView ImageShare;
    private ImageView ImageLike;
    private ViewPager viewPager;
    private ImageView ImagePlay;
    private TextView mesicName;
    private TextView mesicArtist;
    private String music_path;

    //存放中间图片的信息集合
    private List<Crossover_picsInfo> crossoverPicsList;

    //viewpager中填充的fragment
    private List<Fragment> list;

    private List<Map<String, String>> mapList;
    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(DetailActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(DetailActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String result = (String) msg.obj;
                    mapList = JsonUtils_Crossover.getCrossoverMusic(result);
                    crossoverPicsList = JsonUtils_Crossover.getCrossoverPics(result);
                    handler.sendEmptyMessage(3);
                    break;
                case 3: //头

                    DetailHeadFragment fragment1 = new DetailHeadFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("title", title);
                    bundle1.putString("cover", cover);
                    fragment1.setArguments(bundle1);
                    list.add(fragment1);
                    initDate();
                    break;
                case 4:
                    Toast.makeText(DetailActivity.this, "加载失败！", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    adapter = new DetailActivityFragmentAdapter(getSupportFragmentManager(), list);
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(0);
                    break;
            }
        }
    };
    private DetailActivityFragmentAdapter adapter;
    private MediaPlayer player;
    private boolean isStatePlaying = false;
    private String cover_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        player = new MediaPlayer();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            uid = bundle.getString("uid");
            create_time = bundle.getString("create_time");
            title = bundle.getString("title");//第一个fragment标题
            cover = bundle.getString("cover");//第一个fragment图片
            nick = bundle.getString("nick");//在最后一个fragment音乐信息中显示
            Log.i("tag", "--->DetialActivity:uid:" + uid + "-->create_time" + create_time + title + cover + nick);
        } else {
            Log.d("flag", "--->bundle为空");
        }
        initView();
        initNetDate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent myIntent = new Intent(DetailActivity.this, MainActivity.class);
            setResult(21, myIntent);
            startActivity(myIntent);
        }
        return true;
    }

    private void initPlayer() {

        //设置播放流媒体文件
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //循环
        player.setLooping(true);

        //设置监听
        player.setOnPreparedListener(this);
        player.setOnBufferingUpdateListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

        /**
         * 调用setDataSource();方法，并传入想要播放的音频文件的HTTP位置
         */
        try {
            player.setDataSource(music_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 调用prepareAsync方法，它将在后台开始缓冲音频文件并返回。
         * 当点击播放时调用
         */
        player.prepareAsync();
        ImagePlay.setSelected(true);
    }

    /**
     * 音乐的数据
     */
    private void initDate() {
        if (mapList != null && crossoverPicsList != null && crossoverPicsList.size() > 0) {
            mesicName.setText(mapList.get(0).get("music_name"));
            mesicArtist.setText(mapList.get(0).get("artist"));
            music_path = Constants.URL_MUSIC + mapList.get(0).get("file_path");
            cover_path = mapList.get(0).get("cover_path");
            Log.i("tag", "=====musicPath" + music_path);
            initPlayer();
            initViewPager();
        } else {
            handler.sendEmptyMessage(4);
        }
    }

    /**
     * 网络请求数据
     */
    private void initNetDate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = Constants.URL_INFO + "uid=" + uid + "&create_time=" + create_time;
                Log.i("tag", "=====path" + path);
                //====path:http://cross.chrrs.com:8122/com/hotnew?uid=24430994&create_time1447524947
                if (HttpConnectionhelper.isNetWorkConntected(DetailActivity.this)) {
                    String result = HttpConnectionhelper.doGetSumbit(path);
                    Log.i("tag", "-----DetialActivity---result:" + result);
                    if (result != null) {
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
        }).start();
    }

    /**
     * 初始化iniViewPagre
     */
    private void initViewPager() {

        //中间的
        for (int i = 0; i < crossoverPicsList.size(); i++) {
            DetailContentFragment fragment = new DetailContentFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putInt("size", crossoverPicsList.size());
            bundle3.putInt("index", i + 1);
            bundle3.putString("pic", crossoverPicsList.get(i).getPic());
            bundle3.putString("pic_text", crossoverPicsList.get(i).getPic_text());
            fragment.setArguments(bundle3);
            list.add(fragment);
        }

        //尾
        DetailFootFragment fragment2 = new DetailFootFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("title", title);
        bundle2.putString("nick", nick);
        fragment2.setArguments(bundle2);
        list.add(fragment2);
        handler.sendEmptyMessage(5);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.pause();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        crossoverPicsList = new ArrayList<>();
        list = new ArrayList<>();
        ImageBack = ((ImageView) findViewById(R.id.detail_backCrossover));
        ImageShare = ((ImageView) findViewById(R.id.detail_shareCrossover));
        ImageLike = ((ImageView) findViewById(R.id.detail_likeCrossover));
        viewPager = ((ViewPager) findViewById(R.id.detail_viewpagerCrossover));
        ImagePlay = ((ImageView) findViewById(R.id.detail_playCrossover));
        mesicName = ((TextView) findViewById(R.id.detail_mesicNameCrossover));
        mesicArtist = ((TextView) findViewById(R.id.detail_artistCrossover));
        ImageBack.setOnClickListener(this);
        ImageShare.setOnClickListener(this);
        ImageLike.setOnClickListener(this);
        ImagePlay.setOnClickListener(this);
    }

    /**
     * 控件的监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_backCrossover:
                Intent myIntent = new Intent(DetailActivity.this, MainActivity.class);
                setResult(21, myIntent);
                startActivity(myIntent);
                break;
            case R.id.detail_shareCrossover:

                break;
            case R.id.detail_likeCrossover:

                break;
            case R.id.detail_playCrossover:
                if (player.isPlaying()) {
                    Log.i("music--->", "pause");
                    player.pause();
                    ImagePlay.setSelected(false);
                } else {
                    if (isStatePlaying) {
                        Log.i("music--->", "reset");
                        int currentPosition = player.getCurrentPosition();
                        player.seekTo(currentPosition);
                        player.start();
                        ImagePlay.setSelected(true);
                    } else {
//                        player.prepareAsync();
                        player.start();
                        Log.i("music--->", "start");
                        isStatePlaying = true;
                        ImagePlay.setSelected(true);
                    }
                }
                break;
        }
    }

    /**
     * 当MediaPlayer正在缓冲时，将调用该Activity的onBufferingUpdate方法。
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//        tv.setText("正在缓冲中..."+percent+"%");
    }


    /**
     * 当MediaPlayer完成播放音频文件时，将调用onCompletion方法。
     * 此时设置“播放”按钮可点击，“暂停”按钮不可点击（表示可以再次播放）。
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        ImagePlay.setSelected(false);
    }

    /**
     * 出错时执行的回调方法:进行错误处理
     *
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        Log.i("GFC", "错误码：" + what + "," + extra);
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.i("WH", "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.i("WH", "MEDIA_ERROR_SERVER_DIED");
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.i("WH", "MEDIA_ERROR_UNKNOWN");
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 当完成prepareAsync方法时，将调用onPrepared方法，表明音频准备播放。
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        if (!mp.isPlaying()) {
            mp.start();
        }
    }


    /**
     * fragmentViewPageradapter
     */
    private class DetailActivityFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;

        public DetailActivityFragmentAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;

        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            int ret = 0;
            if (list != null) {
                ret = list.size();
            }
            return ret;
        }
    }

    /**
     * 返回
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent myIntent = new Intent(DetailActivity.this, MainActivity.class);
            setResult(21, myIntent);
            startActivity(myIntent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
