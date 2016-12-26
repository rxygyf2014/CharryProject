package com.charryteam.charryproject.fragment.discover.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.javabean.SongsEntity;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.charryteam.charryproject.widget.RoundImageView;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 *  Created by ChunLin on 2015/12/13.
 */
public class SongEntityAdapter extends BaseAdapter {
    private final BitmapSinglton singlton;
    private final BitmapUtils bitmapUtils;
    private Context context;
    private List<SongsEntity> list;
    private Handler handler;
    private View.OnClickListener listener;
    private boolean isCliclItem;


    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setIsClickItem(boolean isCliclItem) {
        this.isCliclItem = isCliclItem;
    }


    public SongEntityAdapter(Context context, List<SongsEntity> list, Handler handler) {
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
        TopMusicsViewHolder holder = null;
        if (convertView == null) {
            holder = new TopMusicsViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.topmusics_listview_items, null);
            holder.imageView = ((RoundImageView) convertView.findViewById(R.id.topmusic_listview_items_iv));
            holder.tv_artist_name = ((TextView) convertView.findViewById(R.id.topmusic_listview_items_artistname));
            holder.tv_music_name = ((TextView) convertView.findViewById(R.id.topmusic_listview_items_musicname));
            holder.music_selector = ((ImageView) convertView.findViewById(R.id.topmusic_select));
            convertView.setTag(holder);
        } else {
            holder = ((TopMusicsViewHolder) convertView.getTag());
        }
        SongsEntity topMusics = list.get(position);
        holder.tv_music_name.setText(topMusics.getMusic_name());
        holder.tv_artist_name.setText(topMusics.getArtist());
        holder.tv_artist_name.setTextColor(Color.WHITE);
        holder.tv_music_name.setTextColor(Color.WHITE);
        String cover_path = topMusics.getCover_path();
        String imgPath = Constants.URL_image02+ cover_path;
        Log.i("aaaaa","SongEntityAdapter:imgPath:"+imgPath);
        bitmapUtils.display(holder.imageView, imgPath);
        return convertView;
    }

    static class TopMusicsViewHolder {
        ImageView music_selector;
        RoundImageView imageView;
        TextView tv_artist_name, tv_music_name;
    }
}
