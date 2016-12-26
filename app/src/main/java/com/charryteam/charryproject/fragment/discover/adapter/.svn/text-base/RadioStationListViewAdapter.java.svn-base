package com.charryteam.charryproject.fragment.discover.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.javabean.RadioStation;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by xiabaikui on 2015/12/6.
 */
public class RadioStationListViewAdapter extends BaseAdapter {
    private final BitmapSinglton singlton;
    private final BitmapUtils bitmapUtils;
    private Context context;
    private List<RadioStation> list;
    private Handler handler;

    public RadioStationListViewAdapter(Context context, List<RadioStation> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
        singlton = BitmapSinglton.getSinglton();
        bitmapUtils = singlton.getBitmapUtils();
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null) {
            ret = list.size();
        }
        return ret;
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = null;
        if (convertView != null) {
            iv = ((ImageView) convertView.getTag());
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.radiostation_listview_item, null);
            iv = ((ImageView) convertView.findViewById(R.id.radiostation_listview_item_iv));
            convertView.setTag(iv);
        }
        RadioStation radioStation = list.get(position);
        String img = radioStation.getImg();
        //图片三级缓存
        String imgPath = Constants.URL_image01 + img;
        bitmapUtils.display(iv, imgPath);

        return convertView;
    }
}
