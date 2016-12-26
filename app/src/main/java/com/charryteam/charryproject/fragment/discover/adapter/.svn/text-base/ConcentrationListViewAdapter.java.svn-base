package com.charryteam.charryproject.fragment.discover.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.javabean.DiscoverBestList;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by xiabaikui on 2015/12/6.
 */
public class ConcentrationListViewAdapter extends BaseAdapter {

    private final BitmapSinglton singlton;
    private final BitmapUtils bitmapUtils;
    private Context context;
    private List<DiscoverBestList> list;
    private Handler handler;

    public ConcentrationListViewAdapter(Context context, List<DiscoverBestList> list, Handler handler) {
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
        ViewHolder holder = null;
        if (convertView != null) {
            holder = ((ViewHolder) convertView.getTag());
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.concentration_listview_items, null);
            holder.imageView = ((ImageView) convertView.findViewById(R.id.concentration_lv_items_iv));
            holder.tv_summary = ((TextView) convertView.findViewById(R.id.concentration_lv_items_tvsummary));
            holder.tv_count = ((TextView) convertView.findViewById(R.id.concentration_lv_items_tvcount));
            convertView.setTag(holder);
        }
        DiscoverBestList discoverBestList = list.get(position);
        holder.tv_summary.setText(discoverBestList.getContent());
        holder.tv_count.setText(discoverBestList.getListen_count()+"");
        String img = discoverBestList.getImg();
        //图片三级缓存
        String imgPath = Constants.URL_image01 + img;
        bitmapUtils.display(holder.imageView, imgPath);
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView tv_summary, tv_count;
    }
}
