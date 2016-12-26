package com.charryteam.charryproject.fragment.crossover;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class DetailContentFragment extends Fragment {


    private View view;
    private ImageView imageTitle;
    private TextView textTitle;
    private TextView textCounte;
    private Bundle bundle;
    private final BitmapSinglton singlton;
    private final BitmapUtils bitmapUtils;
    private String pic;
    private String pic_text;
    private String pic_path;

    public DetailContentFragment() {
        // Required empty public constructor
        singlton = BitmapSinglton.getSinglton();
        bitmapUtils = singlton.getBitmapUtils();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        pic = bundle.getString("pic");
        pic_text = bundle.getString("pic_text");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_content, container, false);
        initView();
        initDate();
        return view;
    }

    /**
     * 数据填充
     */
    private void initDate() {
        pic_path = Constants.URL_image03 + pic;
        if (bundle != null) {
            bitmapUtils.display(imageTitle, pic_path);
            textTitle.setText(pic_text);
            textCounte.setText(bundle.getInt("index") + "/" + bundle.getInt("size"));
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        imageTitle = ((ImageView) view.findViewById(R.id.detatil_imageCrossover));//图片路径
        textTitle = ((TextView) view.findViewById(R.id.detail_titleCrossover)); //图片标题
        textCounte = ((TextView) view.findViewById(R.id.detail_imageCount)); //图片个数
    }


}
