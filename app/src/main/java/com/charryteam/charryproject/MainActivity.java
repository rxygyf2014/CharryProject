package com.charryteam.charryproject;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.charryteam.charryproject.fragment.CrossoverFragment;
import com.charryteam.charryproject.fragment.DiscoverFragment;
import com.charryteam.charryproject.fragment.HomeFragment;
import com.charryteam.charryproject.fragment.SettingFragment;
import com.charryteam.charryproject.fragment.discover.DiscoverDetailsFragment;
import com.charryteam.charryproject.service.PlayService;
import com.charryteam.charryproject.tencentlogin.Util;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.FragmentChangeHelper;
import com.charryteam.charryproject.utils.QRCodeUtils;
import com.charryteam.charryproject.widget.RoundImageView;
import com.lidroid.xutils.BitmapUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

//import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 实现底部导航功能
 */
public class MainActivity extends AppCompatActivity implements DiscoverDetailsFragment.PlayMusicListener {

    private static Tencent mTencent;
    private RadioGroup radioGroup1;
    private FragmentChangeHelper helper;
    private FragmentManager manager;
    private RoundImageView round_iv_play;
    private BitmapSinglton singlton;
    private BitmapUtils bitmapUtils;
    private NavigationView navigationView;
    private View view;
    private UserInfo mInfo;
    private RoundImageView navg_iv_img;
    private TextView nav_header_tv_name;
    private static boolean isServerSideLogin = false;

    private PlayService playMusicBindService;
    private MyServiceConnection serviceConnection;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        nav_header_tv_name.setVisibility(android.view.View.VISIBLE);
                        nav_header_tv_name.setText(response.getString("nickname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 1) {
                Bitmap bitmap = (Bitmap) msg.obj;
                navg_iv_img.setImageBitmap(bitmap);
                navg_iv_img.setVisibility(android.view.View.VISIBLE);
            }
        }
    };
    private ImageView qrcode_iv_img;
    private boolean b;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = ((DrawerLayout) findViewById(R.id.main_drawerlayout));
        navigationView = ((NavigationView) findViewById(R.id.main_navigation));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                item.setChecked(true);
                Log.i("tag", "--------->点击了");
                Toast.makeText(MainActivity.this, "--->" + itemId, Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("1105012660", getApplicationContext());
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        helper = new FragmentChangeHelper();
        //初始化布局控件
        initView();
        //添加监听
        initListener();
    }


    @Override
    protected void onStart() {
        DiscoverFragment fragment = new DiscoverFragment();
        helper.setTargetFragment(fragment);
        helper.setIsClearBackStack(false);
        this.changeFragment(helper);
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void initListener() {

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment ft = null;
                Bundle bundle = new Bundle();
                switch (checkedId) {
                    case R.id.main_rb_tab1:
                        ft = new DiscoverFragment();
                        helper.setBundle(bundle);
                        helper.setTargetFragment(ft);
                        changeFragment(helper);
                        break;
                    case R.id.main_rb_tab2:
                        ft = new CrossoverFragment();
                        helper.setBundle(bundle);
                        helper.setTargetFragment(ft);
                        changeFragment(helper);
                        break;
                    case R.id.main_rb_tab4:
                        ft = new HomeFragment();
                        helper.setBundle(bundle);
                        helper.setTargetFragment(ft);
                        changeFragment(helper);
                        break;
                    case R.id.main_rb_tab5:
                        ft = new SettingFragment();
                        helper.setBundle(bundle);
                        helper.setTargetFragment(ft);
                        changeFragment(helper);
                        break;
                }
            }
        });

        bitmapUtils.configDefaultAutoRotation(true);
        bitmapUtils.configMemoryCacheEnabled(true);
        bitmapUtils.display(round_iv_play, "http://image02.cdn.chrrs.com/8/5/10285.jpg");
        round_iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
                startActivityForResult(intent, 1111);
            }
        });
        navg_iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tag", "----->onClickLogin()");
                onClickLogin();
            }
        });
    }

    /**
     * paly页面
     * Intent it = new Intent(this, MainActivity2.class);
     * startActivity(it);
     * overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
     */


    private void initView() {
        radioGroup1 = ((RadioGroup) findViewById(R.id.main_radio_group1));
        round_iv_play = ((RoundImageView) findViewById(R.id.main_rb_tab3Play));
        view = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        navg_iv_img = ((RoundImageView) view.findViewById(R.id.nav_header_img));
        navg_iv_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        nav_header_tv_name = ((TextView) view.findViewById(R.id.nav_header_tv_name));
        qrcode_iv_img = ((ImageView) view.findViewById(R.id.qrcode_iv_img));
        singlton = BitmapSinglton.getSinglton();
        bitmapUtils = singlton.getBitmapUtils();
        initQRCodeImg();
        /**
         * 头像和qq昵称的监听，调用tencent的API方法
         */
//        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.nav_relativelayout);
//        View.OnClickListener listener = new NewClickListener();
//        for (int i = 0; i < linearLayout.getChildCount(); i++) {
//            View view = linearLayout.getChildAt(i);
//            if (view instanceof Button) {
//                view.setOnClickListener(listener);
//            }
//        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        } else if (requestCode == 21) {
            Fragment ft = null;
            Bundle bundle = new Bundle();
            ft = new DiscoverFragment();
            helper.setBundle(bundle);
            helper.setTargetFragment(ft);
            changeFragment(helper);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 点击登录
     */
    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
            isServerSideLogin = false;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(this);
            updateUserInfo();
        }
    }

    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };

    @Override
    public void play(String path) {
        playMusicBindService.preparePlayMusic(path);
    }


    /**
     * qq登录的回调接口
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(MainActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(MainActivity.this, "返回为空", "登录失败");
                return;
            }
            Util.showResultDialog(MainActivity.this, response.toString(), "登录成功");
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(MainActivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(MainActivity.this, "onCancel: ");
            Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }

    /**
     * 生成二维码
     */
    private void initQRCodeImg() {
        Bitmap bitmap = QRCodeUtils.creatCodeBitmap("http://www.baidu.com", 80, 80, this);
        if (bitmap != null) {
            Log.i("tag", "qrImg--->success");
            qrcode_iv_img.setImageBitmap(bitmap);
        } else {
            Log.i("tag", "failed---->qrImg");
            qrcode_iv_img.setImageResource(R.mipmap.qrcode_img);
        }
    }


    /**
     * 登录成功返回name和头像
     */
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread() {
                        @Override
                        public void run() {
                            JSONObject json = (JSONObject) response;
                            if (json.has("figureurl")) {
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                } catch (JSONException e) {

                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }

                    }.start();
                }

                @Override
                public void onCancel() {

                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        } else {
            nav_header_tv_name.setText("未登录");
            nav_header_tv_name.setVisibility(View.GONE);
            navg_iv_img.setVisibility(View.GONE);
        }
    }


    public void changeFragment(FragmentChangeHelper helper) {
        Fragment fragment = helper.getTargetFragment();
        Bundle bundle = helper.getBundle();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        boolean clearBackStack = helper.isClearBackStack();
        String tagFragment = helper.getTagFragment();
        manager = getSupportFragmentManager();
        if (clearBackStack) {
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = manager.beginTransaction();
        if (tagFragment != null) {
            transaction.addToBackStack(tagFragment);
        }
        transaction.replace(R.id.fragment_choose, fragment);
        transaction.commit();
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
            playMusicBindService = binder.getPlayMusicBindService();
        }

        /**
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

    }


    private int times = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            times++;
            if (times == 2) {
                finish();
            } else if (times == 1) {
                Toast.makeText(MainActivity.this, "再一次点击退出应用", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 监听事件
     */
//    class NewClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            Context context = v.getContext();
//            Class<?> cls = null;
//            boolean isAppbar = false;
//            switch (v.getId()) {
//                //点击跳转大登陆页面，但是现在不知道为什么整个navigationView获取不到焦点
//                case R.id.nav_header_img:
//                    onClickLogin();
//                    return;
//            }
//            if (cls != null) {
//                Intent intent = new Intent(context, cls);
//                if (isAppbar) { // APP内应用吧登录需接收登录结果
//                    startActivityForResult(intent, Constants.REQUEST_APPBAR);
//                } else {
//                    context.startActivity(intent);
//                }
//            }
//        }
//    }
}
