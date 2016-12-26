package com.charryteam.charryproject.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.charryteam.charryproject.MainActivity;
import com.charryteam.charryproject.R;
import com.charryteam.charryproject.fragment.discover.ConcentrationFragment;
import com.charryteam.charryproject.fragment.discover.RadioStationFragment;
import com.charryteam.charryproject.fragment.discover.TopFragment;
import com.charryteam.charryproject.service.PlayService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {


    private View view;
    private ImageView discover_iv_head;
    private ImageView discover_iv_search;
    private TabLayout discover_tablayout;
    private ViewPager discover_vp_fragment;
    private MainActivity activity;


    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = ((MainActivity) context);
        activity.startService(new Intent(getActivity(), PlayService.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_discover, container, false);
        initView(view);
        initDate();

        return view;
    }

    /**
     * 添加数据
     */
    private void initDate() {

        TabLayout.Tab tab1 = discover_tablayout.newTab();
        tab1.setText("精选");
        discover_tablayout.addTab(tab1);

        TabLayout.Tab tab2 = discover_tablayout.newTab();
        tab2.setText("电台");
        discover_tablayout.addTab(tab2);

        TabLayout.Tab tab3 = discover_tablayout.newTab();
        tab3.setText("排行");
        discover_tablayout.addTab(tab3);

        discover_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        final List<Fragment> list = new ArrayList<>();

        ConcentrationFragment concentrationFragment = new ConcentrationFragment();
        RadioStationFragment radioStationFragment = new RadioStationFragment();
        TopFragment topFragment = new TopFragment();

        list.add(concentrationFragment);
        list.add(radioStationFragment);
        list.add(topFragment);

        final MyDiscoverFragmentPagerAdapter adapter = new MyDiscoverFragmentPagerAdapter(getChildFragmentManager(), list);
        discover_vp_fragment.setAdapter(adapter);

        discover_vp_fragment.setCurrentItem(0);
        discover_vp_fragment.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(discover_tablayout));
        discover_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                discover_vp_fragment.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        discover_iv_head = ((ImageView) view.findViewById(R.id.discover_iv_head));
        discover_iv_search = ((ImageView) view.findViewById(R.id.discover_iv_search));
        discover_tablayout = (TabLayout) view.findViewById(R.id.discover_tablayout);
        discover_vp_fragment = ((ViewPager) view.findViewById(R.id.discover_vp_fragment));
    }

    /**
     * fragment的adapter
     */
    class MyDiscoverFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;

        public MyDiscoverFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
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
            return list.size();
        }
    }

}
