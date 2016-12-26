package com.charryteam.charryproject.fragment.crossover;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.lidroid.xutils.BitmapUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHeadFragment extends Fragment {
    private View view;
    private BitmapSinglton singlton;
    private BitmapUtils bitmapUtils;
    private ImageView imageTitle;
    private TextView textTitle;
    private String cover;
    private String title;

    public DetailHeadFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            cover = bundle.getString("cover");
            Log.i("tag","---------------------------------->bundle不为空"+title+cover);
        } else {

            Log.i("tag","---------------------------------->bundle为空");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_head, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    /**
     * 填充数据
     */
    private void initDate() {
        String path = Constants.URL_image03 + cover;
        Log.i("tag", "--->detialAcvtivity" + path);
        textTitle.setText(title);
        bitmapUtils.display(imageTitle, path);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        singlton = BitmapSinglton.getSinglton();
        bitmapUtils = singlton.getBitmapUtils();
        imageTitle = ((ImageView) view.findViewById(R.id.detatil_imageCrossover));//图片路径
        textTitle = ((TextView) view.findViewById(R.id.detail_titleCrossover)); //图片标题
    }
}
