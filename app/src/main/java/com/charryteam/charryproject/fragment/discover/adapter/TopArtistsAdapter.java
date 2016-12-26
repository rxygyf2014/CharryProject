package com.charryteam.charryproject.fragment.discover.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.javabean.TopArtists;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by xiabaikui on 2015/12/7.
 */
public class TopArtistsAdapter extends BaseAdapter {

    private final BitmapSinglton singlton;
    private final BitmapUtils bitmapUtils;
    private Context context;
    private List<TopArtists> list;
    private Handler handler;

    public TopArtistsAdapter(Context context, List<TopArtists> list, Handler handler) {

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
            Log.i("tag","--->ret"+ret);
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
        if (convertView == null) {
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.top_header_gridview_items,null);
            holder.imageView=((ImageView) convertView.findViewById(R.id.top_header_gl_items_iv));
            holder.textView=((TextView) convertView.findViewById(R.id.top_header_gl_items_tv));
            convertView.setTag(holder);
        } else {
            holder= ((ViewHolder) convertView.getTag());
        }
        TopArtists topArtists = list.get(position);
        holder.textView.setText(topArtists.getArtist()+"");
        int artist_id = topArtists.getArtist_id();
        String imgName=artist_id+"";
        Log.i("tag","---artist_id:"+imgName);
        String s1 = imgName.substring(imgName.length() - 1);
        String s2 = imgName.substring(imgName.length() - 2, imgName.length() - 1);
        String imgPath = Constants.URL_image02 + "/"+s2+"/"+s1+"/"+imgName+".jpg";
        Log.i("tag", "----->imgpath" + imgPath);
        bitmapUtils.display(holder.imageView,imgPath);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
