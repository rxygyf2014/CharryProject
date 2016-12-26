package com.charryteam.charryproject.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.charryteam.charryproject.MainActivity;
import com.charryteam.charryproject.R;

/**
 * app换一个页面,运行的首页
 */
public class SplashActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initGuide();
    }

   /**
     * 开始动画
     */
    private void initGuide() {
        preferences = getSharedPreferences("Welcome", MODE_PRIVATE);
        final boolean first = preferences.getBoolean("first", true);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if (first) {
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putBoolean("first", false);
                        edit.commit();
                        handler.sendEmptyMessage(0);
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
