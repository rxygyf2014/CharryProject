package com.charryteam.charryproject.utils;

import android.util.Log;

import com.charryteam.charryproject.javabean.Crossover_musicInfo;
import com.charryteam.charryproject.javabean.Crossover_person;
import com.charryteam.charryproject.javabean.Crossover_picsInfo;
import com.charryteam.charryproject.javabean.Info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-12-6.
 */
public class JsonUtils_Crossover {

    /**
     * 解析详情中用户上传音乐的信息
     * "music_info":{
     * "artist_id":249548,
     * "can_listen_time":0,
     * image02
     * "cover_path":"/I/3/001DzC8D2PhMI3.jpg",
     * "create_time":"1415863364",
     * "file_path":"/9/8/7/30705987.mp3",
     * "has_copyright":1,
     * "is_translated":0,
     * "lang_id":"4",
     * "listen_count":"2943024",
     * "lrc_path":"/9/8/7/30705987.lrc",
     * "server_host":1,
     * "music_name":"My Love Will Clap Its Hands For You",
     * "music_id":70598748,
     * "artist":"The Candle Thieves"
     * }
     *
     * @param json
     * @return
     */
    public static List<Map<String, String>> getCrossoverMusic(String json) {
        List<Map<String, String>> list = new ArrayList<>();
        Crossover_musicInfo musicInfo = new Crossover_musicInfo();
        try {
            JSONObject jsonObject = new JSONObject(json).getJSONObject("data").getJSONObject("music_info");
            Map<String, String> map = new HashMap<>();
            String cover_path = jsonObject.getString("cover_path");
            map.put("cover_path", cover_path);
            musicInfo.setCover_path(cover_path);
            String music_name = jsonObject.getString("music_name");
            map.put("music_name", music_name);
            musicInfo.setMusic_name(music_name);
            String lrc_path = jsonObject.getString("lrc_path");
            musicInfo.setLrc_path(lrc_path);
            map.put("lrc_path", lrc_path);
            String artist = jsonObject.getString("artist");
            musicInfo.setArtist(artist);
            map.put("artist", artist);
            String file_path = jsonObject.getString("file_path");
            musicInfo.setFile_path(file_path);
            map.put("file_path", file_path);
            list.add(map);
            Log.i("tag", "-======topMusic" + list.toString());
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析详情中用户添加图片的信息
     * "pics":[
     * {
     * "pic":"/47/9/3/8cb8575cc09c58436f2e5afba0323b93.jpg",
     * "pic_text":"还在纠结，要不要去找你，多想跟你说声“我们和好吧”"
     * },
     * {
     * "pic":"/47/e/d/58aa52d7c7701387ea5b51ab9175bfed.jpg",
     * "pic_text":"一切都明明白白，但我们还是匆匆错过"
     * },
     *
     * @param json
     * @return
     */
    public static List<Crossover_picsInfo> getCrossoverPics(String json) {
        List<Crossover_picsInfo> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONObject(json).getJSONObject("data").getJSONArray("pics");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Crossover_picsInfo picsInfo = new Crossover_picsInfo();
                String pic = object.getString("pic");
                String pic_text = object.getString("pic_text");
                picsInfo.setPic(pic);
                picsInfo.setPic_text(pic_text);
                list.add(picsInfo);
            }
            Log.i("tag", "======>" + list.toString());
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析crossover的热门页面
     * "zan":{
     * "zan_count":"14",
     * "zan_array":[
     * {
     * "uid":"14434911",
     * "nick":"萌萌哒的花爷"
     * },
     * {
     * "uid":"24432601",
     * "nick":"看到你哭泣，我怎会狠开心i"
     * },
     */
    public static List<Info> getCrossoverHotNew(String json) {
        List<Info> list = new ArrayList<Info>();

        try {
            JSONArray jsonArray = new JSONObject(json).getJSONObject("data").getJSONArray("list");
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                Info info = new Info();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                info.setCover(jsonObject.getString("cover"));
                info.setTitle(jsonObject.getString("title"));
                info.setRead_count(jsonObject.getString("read_count"));
                info.setNick(jsonObject.getString("nick"));
                info.setCreate_time(jsonObject.getString("create_time"));
                info.setUser_uid(jsonObject.getString("uid"));

                //获取点赞的人
                JSONArray jsonArray1 = jsonObject.getJSONObject("zan").getJSONArray("zan_array");
                List<Crossover_person> crossover_persons = new ArrayList<>();
                for (int i1 = 0; i1 < jsonArray1.length(); i1++) {
                    Crossover_person crossover_person = new Crossover_person();
                    JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i1);
                    crossover_person.setUser_nick(jsonObject1.getString("nick"));
                    crossover_persons.add(crossover_person);
                }
                info.setList(crossover_persons);
                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
