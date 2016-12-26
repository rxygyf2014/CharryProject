package com.charryteam.charryproject.fragment.crossover;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charryteam.charryproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFootFragment extends Fragment {


    private View view;
    private TextView textLastTitle;
    private TextView textNick;
    private Bundle bundle;
    private String title;
    private String nick;

    public DetailFootFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {
            Log.i("tag","-------->bundle不为空");
            title = bundle.getString("title");
            nick = bundle.getString("nick");
        }else{
            Log.i("tag","bundle为空");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_foot, container, false);
        initView();
        initDate();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        textLastTitle.setText(title);
        textNick.setText(nick);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        textLastTitle = ((TextView) view.findViewById(R.id.detail_lastTitle));//最后一张图片的标题
        textNick = ((TextView) view.findViewById(R.id.detail_nick)); //最后一张图的昵称
    }


}
