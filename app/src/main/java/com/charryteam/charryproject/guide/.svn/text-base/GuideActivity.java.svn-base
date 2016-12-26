package com.charryteam.charryproject.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.charryteam.charryproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 欢迎引导页面
 */
public class GuideActivity extends AppCompatActivity {

    private LinearLayout linearlayout;
    private Button guide_start;
    private ViewPager viewPager;
    private List<Fragment> list;
    private ImageView[] arr_point;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //添加引导图片
        initView();
    }

    private void initView() {
        linearlayout = ((LinearLayout) findViewById(R.id.guide_linear_point));
        guide_start = ((Button) findViewById(R.id.guide_tv_start));
        viewPager = ((ViewPager) (findViewById(R.id.guide_vp_images)));
        list = new ArrayList<>();
        Guide1Fragment fragment1 = new Guide1Fragment();
        Guide2Fragment fragment2 = new Guide2Fragment();
        Guide3Fragment fragment3 = new Guide3Fragment();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        initPoint();
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                for (int i = 0; i < list.size(); i++) {
                    arr_point[i].setEnabled(true);
                }
                arr_point[position].setEnabled(false);
                if (position==list.size()-1){
                    linearlayout.setVisibility(View.GONE);
                }else{
                    linearlayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);

    }

    private void initPoint() {
        arr_point = new ImageView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ImageView point = new ImageButton(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            point.setLayoutParams(params);
            point.setScaleType(ImageView.ScaleType.FIT_XY);
            point.setPadding(10, 5, 10, 5);
            point.setBackgroundResource(R.drawable.guide_point_selector);
            linearlayout.addView(point);
            point.setTag(i);
            arr_point[i] = point;
        }
        arr_point[0].setEnabled(false);
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }


        @Override
        public int getCount() {
            return list.size();
        }
    }
}
