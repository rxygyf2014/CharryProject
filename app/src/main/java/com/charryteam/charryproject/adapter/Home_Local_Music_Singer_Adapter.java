package com.charryteam.charryproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.javabean.Home_Local_Music;

import java.util.List;


/**
 * Created by Lenovo on 2015/12/6.
 */
public class Home_Local_Music_Singer_Adapter extends BaseAdapter {
    private List<Home_Local_Music> listMusic ;
    private Context context ;

    public Home_Local_Music_Singer_Adapter(List<Home_Local_Music> listMusic, Context context) {
        this.listMusic = listMusic;
        this.context = context;
    }
    public void setListItem(List<Home_Local_Music> listMusic){
        this.listMusic = listMusic;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (listMusic != null) {
            ret = listMusic.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return listMusic.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.music_singer_item,null);
        }
        Home_Local_Music m = listMusic.get(position);

        //歌手
        TextView textMusicSinger = ((TextView) convertView.findViewById(R.id.music_item_singer));
        textMusicSinger.setText(m.getSinger());

        return convertView;
    }


}
