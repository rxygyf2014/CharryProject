package com.charryteam.charryproject.utils;

import com.charryteam.charryproject.javabean.SongsEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChunLin on 2015/12/13.
 *
 * {
 "state": 0,
 "data": {
 "songs": [{
 "cover_path": "\/5\/X\/004aNzlM4H7G5X.jpg",
 "music_id": 3426938,
 "music_name": "I Could Be The One",
 "artist_id": 209038,
 "artist": "Donna Lewis",
 "hascopyright": 1
 }, {
 "cover_path": "\/u\/I\/004ck5kq1wzwuI.jpg",
 "music_id": 68475156,
 "music_name": "Everybody",
 "artist_id": 212956,
 "artist": "Ingrid Michaelson",
 "hascopyright": 1
 }, {
 "cover_path": "\/E\/r\/0005mM0D0BclEr.jpg",
 "music_id": 61390643,
 "music_name": "Gotta Have You",
 "artist_id": 256943,
 "artist": "The Weepies",
 "hascopyright": 1
 }, {
 "cover_path": "\/e\/7\/000T2qou2Ri2e7.jpg",
 "music_id": 44282663,
 "music_name": "The Show",
 "artist_id": 214763,
 "artist": "Lenka",
 "hascopyright": 1
 }, {
 "cover_path": "\/R\/i\/002w0gjU3zZWRi.jpg",
 "music_id": 104501255,
 "music_name": "Walk Away",
 "artist_id": 209055,
 "artist": "Dia Frampton",
 "hascopyright": 1
 }],
 */
public class JsonUtils_similarSong {
    public static List<SongsEntity> parseForSimilarSong(String path){
        List<SongsEntity> list=new ArrayList<>();
        try {
            JSONObject object=new JSONObject(path);
            JSONObject data = object.getJSONObject("data");
            JSONArray songs = data.getJSONArray("songs");
            for (int i = 0; i < songs.length(); i++) {
                JSONObject obj = songs.getJSONObject(i);
                String cover_path = obj.getString("cover_path");
                int music_id = obj.getInt("music_id");
                String music_name = obj.getString("music_name");
                int artist_id = obj.getInt("artist_id");
                String artist = obj.getString("artist");
                int hascopyright = obj.getInt("hascopyright");
                SongsEntity songsEntity=new SongsEntity(cover_path,music_id,music_name,artist_id,artist,hascopyright);
                list.add(songsEntity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;

    }
}

