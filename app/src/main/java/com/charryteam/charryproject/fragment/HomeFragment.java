package com.charryteam.charryproject.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.adapter.HomeAdapter;
import com.charryteam.charryproject.fragment.home.CollectFragment;
import com.charryteam.charryproject.fragment.home.DownLoadFragment;
import com.charryteam.charryproject.fragment.home.LocalFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private View ret;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ret = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return ret;
    }

    private void initView() {
        TabLayout tabLayout = ((TabLayout) ret.findViewById(R.id.tabLayout));
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("下载");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("收藏");
        tabLayout.addTab(tab2);
        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("本机");
        tabLayout.addTab(tab3);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        final ViewPager viewPager = ((ViewPager) ret.findViewById(R.id.viewPager));
        List<Fragment> list = new ArrayList<>();
        DownLoadFragment downLoadFragment = new DownLoadFragment();
        list.add(downLoadFragment);
        CollectFragment collectFragment = new CollectFragment();
        list.add(collectFragment);
        LocalFragment localFragment = new LocalFragment();
        list.add(localFragment);
        HomeAdapter adapter = new HomeAdapter(getChildFragmentManager(),list);
        viewPager.setAdapter(adapter);
        //这里是viewpager滑动时，上面的tablayout也跟着滑动
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //选中tab时，viewpager跟着进行相应的切换
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
