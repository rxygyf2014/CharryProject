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
public class Home_Local_Music_Song_Adapter extends BaseAdapter {
    private List<Home_Local_Music> listMusic ;
    private Context context ;

    public Home_Local_Music_Song_Adapter(List<Home_Local_Music> listMusic, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.music_song_item,null);
        }
        Home_Local_Music m = listMusic.get(position);
        //音乐名
        TextView textMusicName = ((TextView) convertView.findViewById(R.id.music_item_name));
        textMusicName.setText(m.getName());
        //歌手
//        TextView textMusicSinger = ((TextView) convertView.findViewById(R.id.music_item_singer));
//        textMusicSinger.setText(m.getSinger());
        //持续时间
        TextView textMusicTime= ((TextView) convertView.findViewById(R.id.music_item_time));
        textMusicTime.setText(toTime((int)m.getTime()));
        return convertView;
    }

    /**
     * 时间格式转化
     * @param time
     * @return
     */
    public String toTime(int time){
        time/=1000;
        int minute = time/60;
        int hour = minute/60;
        int second = time%60;
        minute%=60;
        return String.format("%02d:%02d",minute,second);
    }
}
