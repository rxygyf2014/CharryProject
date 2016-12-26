package com.charryteam.charryproject.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.charryteam.charryproject.javabean.Home_Local_Music;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lenovo on 2015/12/6.
 */
public class Home_Local_MusicList {
    public static List<Home_Local_Music> getMusicData(Context context){
        List<Home_Local_Music> musicList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver != null) {
            //获取所有歌曲
            Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if (null == cursor) {
                return null;
            }
            if (cursor.moveToFirst()){
                do {
                    Home_Local_Music m = new Home_Local_Music();
                    int _id = cursor.getInt( cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    if ("<unknown>".equals(singer)){
                        singer = "未知艺术家";
                    }
                    String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                    long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String sbr = name.substring(name.length()-3,name.length());
                    if (sbr.equals("mp3")){
                        m.set_id(_id);
                        m.setTitle(title);
                        m.setSinger(singer);
                        m.setAlbum(album);
                        m.setSize(size);
                        m.setTime(time);
                        m.setUrl(url);
                        m.setName(name);
                        musicList.add(m);
                    }

                }while (cursor.moveToNext());
            }
        }
        return musicList;
    }
}
