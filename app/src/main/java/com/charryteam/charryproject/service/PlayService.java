package com.charryteam.charryproject.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.charryteam.charryproject.utils.Constants;

import java.io.IOException;

/**
 * Created by ChunLin on 2015/12/10.
 */
public class PlayService extends Service {
    public MediaPlayer mediaPlayer = null;
    private boolean isStopped = false;
    private LocalBroadcastManager lbm;
    private boolean isStarted = false;
    private SeekToReceiver seekToReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder();
    }

    public class MyBinder extends Binder {
        //        自定义一个获取服务的方法
        public PlayService getPlayMusicBindService() {
            return PlayService.this;
        }
    }

    // 准备工作
    @Override
    public void onCreate() {
        super.onCreate();
        lbm = LocalBroadcastManager.getInstance(getApplicationContext());
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        //改变的进度条
        seekToReceiver = new SeekToReceiver();
        lbm.registerReceiver(seekToReceiver, new IntentFilter(Constants.ACTION_SEEKBAR_SEEKTO));
    }


    // 播放音乐的准备工作
    public void preparePlayMusic(String path) {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
                new Progress().start();
                mediaPlayer.setLooping(false);
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetMusic() {
        mediaPlayer.reset();
    }

    //    // 表示开始唱歌
    public void startMusic() {
        Log.i("aaaa", "--------->开始播放！");
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    /*
    开启线程计算进度条
    * */
    class Progress extends Thread {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                Intent intent = new Intent(Constants.ACTION_SEEKBAR_PROGRESS);
//           总进度
                int duration = mediaPlayer.getDuration();
                intent.putExtra(Constants.ACTION_SEEKBAR_PROGRESS_MAX, duration);
                //获取进度条的进度
                intent.putExtra(Constants.ACTION_SEEKBAR_PROGRESS_CUR, currentPosition);
                lbm.sendBroadcast(intent);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isPlayingMusic() {
        Log.i("tag", "-----serviceIsPlaying" + isStarted);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            Log.i("tag", "-----mediaPlayer" + isStarted);
            return true;
        }
        return false;
    }

    // 停止
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isStopped = true;
        }
    }

    /*暂停音乐*/
    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying())

            mediaPlayer.pause();
    }

    //PlayMusicActivity中控制播放或者停止
    public void isPlayOrPause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        } else {
            Log.i("PlayService", "PlayService是空的");
        }
    }

    public boolean isMusicPlaying() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                return true;
            }
            return false;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
//        取消进度条的广播
        lbm.unregisterReceiver(seekToReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    /*接受PlayMusic中seekBar拖动后改变的进度*/
    class SeekToReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int seekPosition = intent.getIntExtra(Constants.ACTION_SEEKBAR_PROGRESS_CUR, 0);
//           设置播放的进度
            mediaPlayer.seekTo(seekPosition);
        }
    }
}