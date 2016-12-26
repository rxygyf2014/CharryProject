package com.charryteam.charryproject.utils;

import android.util.Log;

import com.charryteam.charryproject.javabean.DiscoverBestList;
import com.charryteam.charryproject.javabean.RadioList;
import com.charryteam.charryproject.javabean.RadioStation;
import com.charryteam.charryproject.javabean.TopArtists;
import com.charryteam.charryproject.javabean.TopMusics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiabaikui on 2015/12/6.
 */
public class JsonUtils_Discover {

    /**
     * 解析发现页面的精选列表
     * <p/>
     * "state": 0,
     * "data": {
     * "count": 21,
     * "list": [
     * {
     * "best_id": 1369,
     * "content": "抖腿+静电=整个冬天！",
     * "img": "/best/g/Q/001A4NgQ.png",
     * "listen_count": 160,
     * "title": "【电子】独自躲棉被里听的歌单"
     * },
     *
     * @param json
     * @return
     */
    public static List<DiscoverBestList> getDiscoverBestList(String json) {
        List<DiscoverBestList> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject data = object.getJSONObject("data");
            JSONArray list1 = data.getJSONArray("list");
            for (int i = 0; i < list1.length(); i++) {
                JSONObject object1 = list1.getJSONObject(i);
                DiscoverBestList discoverBestList = new DiscoverBestList();
                discoverBestList.setBest_id(object1.getInt("best_id"));
                discoverBestList.setContent(object1.getString("content"));
                discoverBestList.setImg(object1.getString("img"));
                discoverBestList.setListen_count(object1.getInt("listen_count"));
                discoverBestList.setTitle(object1.getString("title"));
                list.add(discoverBestList);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析电台页面信息
     * "state": 0,
     * "data": {
     * "count": 15,
     * "list": [
     * {
     * "img": "/radio/0/1/001z5aMq.png",
     * "listen_count": 26,
     * "nick": "叶子",
     * "radio_id": 10001,
     * "radio_name": "你好，陌生人",
     * "radio_type": 1
     * },
     *
     * @param json
     * @return
     */
    public static List<RadioStation> getDiscoverRadioStation(String json) {
        List<RadioStation> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject data = object.getJSONObject("data");
            JSONArray list1 = data.getJSONArray("list");
            for (int i = 0; i < list1.length(); i++) {
                JSONObject object1 = list1.getJSONObject(i);
                RadioStation radioStation = new RadioStation();
                radioStation.setImg(object1.getString("img"));
                radioStation.setListen_count(object1.getInt("listen_count"));
                radioStation.setNick(object1.getString("nick"));
                radioStation.setRadio_id(object1.getInt("radio_id"));
                radioStation.setRadio_name(object1.getString("radio_name"));
                radioStation.setRadio_type(object1.getInt("radio_type"));
                list.add(radioStation);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析排行页面的列表信息
     * -------热门歌曲列表------
     * {
     * "state": 0,
     * "data": {
     * "musics": [
     * {
     * "cover_path": "/N/Z/003JgO5e3q3ENZ.jpg",
     * "artist": "Justin Bieber",
     * "music_id": 10375195016,
     * "artist_id": 43516,
     * "hascopyright": 1,
     * "music_name": "What Do You Mean?"
     * },
     * --------歌手列表---------
     * {
     * "state": 0,
     * "data": {
     * "musics": [],
     * "artists": [
     * {
     * "artist_id": 10285,
     * "artist": "G.E.M. 邓紫棋"
     * },
     * {
     * "artist_id": 20793,
     * "artist": "BIGBANG (빅뱅)"
     * },
     *
     * @param json
     * @return
     */
    public static List<TopArtists> getDiscoverTopArtistsList(String json) {

        List<TopArtists> list = new ArrayList<TopArtists>();
        Log.i("tag", "----->list--->getDiscoverTopArtistsList");
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONObject("data").getJSONArray("artists");
            for (int i = 0; i < array.length(); i++) {
                JSONObject result = array.getJSONObject(i);
                TopArtists topArtists = new TopArtists();
                topArtists.setArtist_id(result.getInt("artist_id"));
                topArtists.setArtist(result.getString("artist"));
                list.add(topArtists);
            }
            Log.i("tag", "----->" + list.toString());
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TopMusics> getDiscoverTopMusicsList(String json) {
        List<TopMusics> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONObject("data").getJSONArray("musics");
            for (int i = 0; i < array.length(); i++) {
                JSONObject result = array.getJSONObject(i);
                TopMusics topMusics = new TopMusics();
                topMusics.setHascopyright(result.getInt("hascopyright"));
                topMusics.setArtist_id(result.getInt("artist_id"));
                topMusics.setArtist(result.getString("artist"));
                topMusics.setCover_path(result.getString("cover_path"));
                topMusics.setMusic_id(result.getString("music_id"));
                topMusics.setMusic_name(result.getString("music_name"));
                list.add(topMusics);
            }
            Log.i("tag", "----->" + list.toString());
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析详情列表歌曲信息
     * {
     * "state": 0,
     * "data": {
     * "count": 12,
     * "music": [
     * {
     * "artist": "周迅",
     * "artist_id": 16400,
     * "cover_path": "/3/q/004PQlK81Kz63q.jpg",
     * "hascopyright": 1,
     * "music_id": "62484700",
     * "music_name": "爱恨恢恢"
     * },
     *
     * @param json
     */
    public static List<TopMusics> getDiscoverDetialsList(String json) {
        List<TopMusics> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONObject("data").getJSONArray("music");
            for (int i = 0; i < array.length(); i++) {
                JSONObject result = array.getJSONObject(i);
                TopMusics topMusics = new TopMusics();
                topMusics.setHascopyright(result.getInt("hascopyright"));
                topMusics.setArtist_id(result.getInt("artist_id"));
                topMusics.setArtist(result.getString("artist"));
                topMusics.setCover_path(result.getString("cover_path"));
                topMusics.setMusic_id(result.getString("music_id"));
                topMusics.setMusic_name(result.getString("music_name"));
                list.add(topMusics);
            }
            Log.i("tag", "----->" + list.toString());
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * {
     * "state": 0,
     * "data": {
     * "artist_id": 22287,
     * "cover_path": "/d/r/003CBBE534GCdr.jpg",
     * "create_time": "1435766400",
     * "file_path": "http://stream03.chrrs.com/7/1/3/102806713.mp3",
     * "has_copyright": 1,
     * "is_translated": 0,
     * "lang_id": "1",
     * "listen_count": "500330",
     * "lrc_path": "/7/1/3/102806713.lrc",
     * "server_host": 3,
     * "title": "吻得太逼真 (Live)",
     * "trans_path": "",
     * "listen_time": 0
     * }
     * }
     *
     * @param json
     * @return
     */
    public static List<Map<String, String>> getSongJson(String json) {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject data = object.getJSONObject("data");
            String artist_id = data.getString("artist_id");
            String cover_path = data.getString("cover_path");
            String create_time = data.getString("create_time");
            String file_path = data.getString("file_path");
            String listen_count = data.getString("listen_count");
            String lrc_path = data.getString("lrc_path");
            String title = data.getString("title");
            Map<String, String> map = new HashMap<>();
            map.put("title", title);
            map.put("listen_count", listen_count);
            map.put("lrc_path", lrc_path);
            map.put("artist_id", artist_id);
            map.put("create_time", create_time);
            map.put("cover_path", cover_path);
            map.put("file_path", file_path);
            list.add(map);
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * {
     * "state": 0,
     * "data": {
     * "count": 100,
     * "top": [
     * {
     * "artist": "测试小子",
     * "artist_id": 1001,
     * "music_id": "103171",
     * "music_name": "117.我喜欢你，但是我更喜欢自己",
     * "music_type": 1
     * },
     *
     * @param json
     * @return
     */
    public static List<RadioList> getRadioList(String json) {
        List<RadioList> list = new ArrayList<>();
        try {
            JSONArray top = new JSONObject(json).getJSONObject("data").getJSONArray("top");
            for (int i = 0; i < top.length(); i++) {
                JSONObject object = top.getJSONObject(i);
                RadioList radioList = new RadioList();
                radioList.setArtist(object.getString("artist"));
                radioList.setArtist_id(object.getString("artist_id"));
                radioList.setMusic_id(object.getString("music_id"));
                radioList.setMusic_name(object.getString("music_name"));
                radioList.setMusic_type(object.getString("music_type"));
                list.add(radioList);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {
     * "state": 0,
     * "data": {
     * "file_path": "http://stream01.chrrs.com/rs/3/6/bed9a37edce32f5eb5fc457e155a17817bc1d836.mp3",
     * "lrc_path": "",
     * "cover_path": "",
     * "lang_id": 0,
     * "trans_path": "",
     * "listen_time": 0,
     * "has_copyright": 1,
     * "server_host": 1
     * }
     * }
     *
     * @param json
     * @return
     */
    public static String getRadioPlayPath(String json) {

        try {
            JSONObject object = new JSONObject(json);
            JSONObject data = object.getJSONObject("data");
            String file_path = data.getString("file_path");
            Log.i("tag", "----file_path" + file_path);
            return file_path;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
