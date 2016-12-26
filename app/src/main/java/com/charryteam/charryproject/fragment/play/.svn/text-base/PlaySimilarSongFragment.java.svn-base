package com.charryteam.charryproject.fragment.play;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.charryteam.charryproject.PlayMusicActivity;
import com.charryteam.charryproject.R;
import com.charryteam.charryproject.fragment.discover.adapter.SongEntityAdapter;
import com.charryteam.charryproject.javabean.SongsEntity;
import com.charryteam.charryproject.utils.Constants;
import com.charryteam.charryproject.utils.HttpConnectionhelper;
import com.charryteam.charryproject.utils.JsonUtils_Discover;
import com.charryteam.charryproject.utils.JsonUtils_similarSong;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaySimilarSongFragment extends Fragment {

    private ListView lv_similar;
    private PlayMusicActivity activity;
    private String music_id;
//    数据请求成功
    private final int RESULT_SUCCESS=2;
    private final int RESULT_NULL=1;
    private final int NetWorkDisconnected=0;
    private List<SongsEntity> list;
private LocalBroadcastManager localBroadcastManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playsilimarsong, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            music_id = bundle.getString("music_id");
            Log.i("aaaaa", "PlaySimilarSongFragment:music_id" + music_id);
        } else {
            Log.i("aaaaa", "PlaySimilarSongFragment没有拿到music_id");
        }
        super.onActivityCreated(savedInstanceState);
        initView(view);
        return view;
    }
    private void initView(View view) {
        lv_similar = ((ListView) view.findViewById(R.id.lv_similar));
//        加载数据
        initData();
    }



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    String result = (String) msg.obj;
                    list = JsonUtils_similarSong.parseForSimilarSong(result);
                    SongEntityAdapter adapter=new SongEntityAdapter(activity, list,handler);
                    lv_similar.setAdapter(adapter);
//                    lv.similar的点击事件
                    listViewClickEvent();
                    break;
                case 0:
                    Toast.makeText(getActivity(), "网络没有连接", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(),"获取数据为空",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void listViewClickEvent() {
        lv_similar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView iv_select = (ImageView) view.findViewById(R.id.topmusic_select);
                iv_select.setVisibility(View.VISIBLE);
                SongsEntity songsEntity = list.get(position);
               final String artist = songsEntity.getArtist();
                int music_id = songsEntity.getMusic_id();//为了获取file的路径
                final String file_path = Constants.URL_Song_JSON + music_id;
                //
                if(HttpConnectionhelper.isNetWorkConntected(getActivity())){
                    new Thread(){
                        @Override
                        public void run() {
                            String result = HttpConnectionhelper.doGetSumbit(file_path);//在网上的数据本来就是一个字符串
                          if (result!=null){
                              List<Map<String, String>> songJson = JsonUtils_Discover.getSongJson(result);
                              Map<String, String> map = songJson.get(0);
                              String filePath = map.get("file_path");
                              String cover_path = map.get("cover_path");
                              String lrc_path = map.get("lrc_path");
                              String title = map.get("title");
                              String artist_id = map.get("artist_id");
                              String mp3Name = title + ".mp3";
                              Intent intent=new Intent(Constants.SIMLIARTOPLAYMUSIC);
                              intent.putExtra("filePath",filePath);
                              intent.putExtra("artist",artist);
                              intent.putExtra("cover_path",cover_path);
                              intent.putExtra("lrc_path",lrc_path);
                              intent.putExtra("title", title);
                              intent.putExtra("artist_id",artist_id);
                              intent.putExtra("mp3Name", mp3Name);
//                              startActivity(intent);
                              localBroadcastManager.sendBroadcast(intent);
                          }else{
                              handler.sendEmptyMessage(RESULT_NULL);
                          }
                        }
                    }.start();
                }else{
                    handler.sendEmptyMessage(NetWorkDisconnected);
                }
            }
        });
    }

    //加载数据
    private void initData() {
        final String path = Constants.URL_RELACTIVE + music_id;
        new Thread(){
            @Override
            public void run() {
                if (HttpConnectionhelper.isNetWorkConntected(activity)){
                    String result = HttpConnectionhelper.doGetSumbit(path);
                    if (result!=null&&result.length()>0){
                        Message message = Message.obtain();
                        message.obj=result;
                        message.what=RESULT_SUCCESS;
                        handler.sendMessage(message);
                    }else {
                        handler.sendEmptyMessage(RESULT_NULL);
                    }
                }else {
                    handler.sendEmptyMessage(NetWorkDisconnected);
                }
            }
        }.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (PlayMusicActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastManager=LocalBroadcastManager.getInstance(getActivity());

    }
}
