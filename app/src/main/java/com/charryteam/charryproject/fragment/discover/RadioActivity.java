package com.charryteam.charryproject.fragment.discover;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.charryteam.charryproject.MainActivity;
import com.charryteam.charryproject.R;
import com.charryteam.charryproject.javabean.RadioList;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.charryteam.charryproject.utils.HttpConnectionhelper;
import com.charryteam.charryproject.utils.JsonUtils_Discover;
import com.lidroid.xutils.BitmapUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RadioActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private String radio_id;
    private List<RadioList> radioList;
    private MediaPlayer player;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    Toast.makeText(RadioActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(RadioActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String json = (String) msg.obj;
                    radioList = JsonUtils_Discover.getRadioList(json);
                    //获取电台列表后，添加menu的监听
                    initMenuListener();
                    //默认打开第一个电台音乐
                    initFirstRadioStation();
                    break;
                case 3:
                    String result = (String) msg.obj;
                    String file_path = JsonUtils_Discover.getRadioPlayPath(result);
                    Log.i("tag", "---->file_path" + file_path);
                    initPlayer(file_path);
                    break;
                case 4:
                    int which = (int) msg.obj;
                    RadioList radioList1 = RadioActivity.this.radioList.get(which);
                    String music_id = radioList1.getMusic_id();
                    String music_type = radioList1.getMusic_type();
                    radio_path=  Constants.URL_RADIOMUSICSTATION + "music_id=" + music_id + "&music_type=" + music_type;
                    Log.i("tag", "---->play-radiopath---choose:" + radio_path);
                    player.reset();
                    startToPlay();
                    break;
                case 5:

                    break;
            }
        }
    };


    private String img;
    private BitmapSinglton singlton;
    private BitmapUtils bitmapUtils;
    private String imgPath;
    private ImageView iv_back;
    private ImageView iv_menu;
    private ImageView iv_start;
    private TextView iv_login;
    private ImageView iv_bg;
    private TextView tv_title;
    private String radio_path;
    private String[] radio_name_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        player = new MediaPlayer();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            radio_id = bundle.getString("radio_id");
            img = bundle.getString("img");
        } else {
            Log.i("tag", "---->bundle为空");
        }
        initView();
        initListener();
    }

    private void initListener() {

        //返回主页面
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                player.release();
                Intent myIntent = new Intent(RadioActivity.this, MainActivity.class);
                setResult(21, myIntent);
                startActivity(myIntent);
            }
        });
        //暂停和继续播放
        iv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    player.pause();
                } else if (!player.isPlaying()) {
                    //获得播放进度
                    int currentPosition = player.getCurrentPosition();
                    //继续播放
                    player.seekTo(currentPosition);
                    player.start();
                }
            }
        });
    }

    //menu的电台选择
    private void initMenuListener() {
        radio_name_list = new String[radioList.size()];
        for (int i = 0; i < radio_name_list.length; i++) {
            RadioList radioList1 = radioList.get(i);
            String music_name = radioList1.getMusic_name();
            radio_name_list[i] = music_name;
        }
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RadioActivity.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("选择电台");
                //    指定下拉列表的显示数据
                //    设置一个下拉的列表选择项
                builder.setItems(radio_name_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_title.setText(radio_name_list[which]);
                        Log.i("tag", "--------选择的:" + radio_name_list[which]);
                        Message message = Message.obtain();
                        message.obj = which;
                        message.what = 4;
                        handler.sendMessage(message);
                    }
                });
                builder.create().show();
            }
        });


    }


    /**
     * 初始化控件
     */
    private void initView() {
        singlton = BitmapSinglton.getSinglton();
        bitmapUtils = singlton.getBitmapUtils();
        radioList = new ArrayList<>();
        iv_bg = ((ImageView) findViewById(R.id.radio_iv_bg));
        iv_back = ((ImageView) findViewById(R.id.radio_back));
        iv_menu = ((ImageView) findViewById(R.id.radio_iv_menu));
        iv_start = ((ImageView) findViewById(R.id.radio_iv_start));
        iv_login = ((TextView) findViewById(R.id.radio_tv_login_tanmu));
        tv_title = ((TextView) findViewById(R.id.radio_tv_title));
        initRadioListData();
    }

    private void initFirstRadioStation() {
        imgPath = Constants.URL_image01 + img;
        bitmapUtils.display(iv_bg, imgPath);
        RadioList radiofirst = radioList.get(0);
        Log.i("tag", "---->radiofirst.toString()" + radiofirst.toString());
        String music_id = radiofirst.getMusic_id();
        String music_name = radiofirst.getMusic_name();
        String music_type = radiofirst.getMusic_type();

        tv_title.setText(music_name);
        radio_path = Constants.URL_RADIOMUSICSTATION + "music_id=" + music_id + "&music_type=" + music_type;
        Log.i("tag", "---->play-radiopath" + radio_path);
        startToPlay();
    }

    private void startToPlay() {
        new Thread() {
            @Override
            public void run() {

                if (HttpConnectionhelper.isNetWorkConntected(RadioActivity.this)) {

                    String result = HttpConnectionhelper.doGetSumbit(radio_path);
                    Log.i("tag", "------>result" + result);
                    if (result != null) {
                        Message message = Message.obtain();
                        message.obj = result;
                        message.what = 3;
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


    /**
     * 联网请求
     */
    private void initRadioListData() {
        new Thread() {
            @Override
            public void run() {
                if (HttpConnectionhelper.isNetWorkConntected(RadioActivity.this)) {
                    String path = Constants.URL_RADIOMUSIC + radio_id;
                    Log.i("tag", "------>path" + path);
                    String result = HttpConnectionhelper.doGetSumbit(path);
                    if (result != null) {
                        Message message = Message.obtain();
                        message.obj = result;
                        message.what = 2;
                        Log.i("tag", "------>result" + result.toString());
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

    private void initPlayer(String file_path) {

        //设置播放流媒体文件
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //循环
        player.setLooping(false);
        //设置监听
        player.setOnPreparedListener(this);
        player.setOnBufferingUpdateListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

        /**
         * 调用setDataSource();方法，并传入想要播放的音频文件的HTTP位置
         */
        Log.i("tag", "----->path_play" + file_path);
        try {
            player.setDataSource(file_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 调用prepareAsync方法，它将在后台开始缓冲音频文件并返回。
         * 当点击播放时调用
         */
        player.prepareAsync();
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
     * 返回
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            player.stop();
            player.release();
            Intent myIntent = new Intent(RadioActivity.this, MainActivity.class);
            setResult(21, myIntent);
            startActivity(myIntent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
