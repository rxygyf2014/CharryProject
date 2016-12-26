package com.charryteam.charryproject.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.adapter.CommonFragmentAdapter;
import com.charryteam.charryproject.fragment.crossover.HotFragment;
import com.charryteam.charryproject.fragment.crossover.LatestFragment;
import com.charryteam.charryproject.fragment.crossover.MyselfFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrossoverFragment extends Fragment implements TabLayout.OnTabSelectedListener {


    private View view;
    private ImageView crossover_iv_head;
    private TabLayout crossover_tablayout;
    private TextView crossover_create;
    private ViewPager crossover_vp_fragment;
    private List<Fragment> list;

    public CrossoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_crossover, container, false);

        initView();
        initData();
        return view;
    }

    /**
     * 填充数据
     */
    private void initData() {
        crossover_tablayout.addTab(crossover_tablayout.newTab().setText("热门"));
        crossover_tablayout.addTab(crossover_tablayout.newTab().setText("最新"));
        crossover_tablayout.addTab(crossover_tablayout.newTab().setText("我的"));
        crossover_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        crossover_tablayout.setOnTabSelectedListener(this);

        list = new ArrayList<Fragment>();
        HotFragment hotFragment= new HotFragment();
        LatestFragment latestFragment=new LatestFragment();
        MyselfFragment myselfFragment=new MyselfFragment();
        list.add(hotFragment);
        list.add(latestFragment);
        list.add(myselfFragment);

        CommonFragmentAdapter adapter=new CommonFragmentAdapter(getChildFragmentManager(),list);
        crossover_vp_fragment.setAdapter(adapter);
        //viewpager的滑动监听
        crossover_vp_fragment.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(crossover_tablayout));

    }

    /**
     * 初始化控件
     */
    private void initView() {
        crossover_iv_head = (ImageView) view.findViewById(R.id.crossover_iv_head);
        crossover_tablayout = (TabLayout) view.findViewById(R.id.crossover_tablayout);
        crossover_create = (TextView) view.findViewById(R.id.crossover_create);
        crossover_vp_fragment = (ViewPager) view.findViewById(R.id.crossover_vp_fragment);
    }

    //tablayout的点击事件
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        crossover_vp_fragment.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
