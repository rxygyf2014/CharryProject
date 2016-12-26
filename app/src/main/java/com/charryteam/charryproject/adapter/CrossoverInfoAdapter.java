package com.charryteam.charryproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charryteam.charryproject.R;
import com.charryteam.charryproject.javabean.Crossover_person;
import com.charryteam.charryproject.javabean.Info;
import com.charryteam.charryproject.utils.BitmapSinglton;
import com.charryteam.charryproject.utils.Constants;
import com.lidroid.xutils.BitmapUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 15-12-7.
 */
public class CrossoverInfoAdapter extends BaseAdapter {
    private Context context;
    private List<Info> list;
    private BitmapSinglton singlton;
    private BitmapUtils bitmapUtils;

    public CrossoverInfoAdapter(Context context, List<Info> list) {
        this.list = list;
        this.context = context;
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_crossover_recycler, null);
            holder.item_iv_head_crossover = (ImageView) convertView.findViewById(R.id.item_iv_head_crossover);

            holder.item_tv_title_crossover = (TextView) convertView.findViewById(R.id.item_tv_title_crossover);
            holder.item_tv_readNum_crossover = (TextView) convertView.findViewById(R.id.item_tv_readNum_crossover);
            holder.item_usrer_nick_crossover = (TextView) convertView.findViewById(R.id.item_usrer_nick_crossover);
            holder.item_user_head_crossover = (ImageView) convertView.findViewById(R.id.item_user_head_crossover);
            holder.item_creat_time_crossover = (TextView) convertView.findViewById(R.id.item_creat_time_crossover);
            holder.item_zan_names_crossover = (TextView) convertView.findViewById(R.id.item_zan_names_crossover);
            holder.item_iv_zan_crossover = (ImageView) convertView.findViewById(R.id.item_iv_zan_crossover);
            holder.item_iv_zan_crossover.setVisibility(View.VISIBLE);
            convertView.setTag(holder);
        } else {
            holder= ((ViewHolder) convertView.getTag());
        }

        //填充数据
        Info info = list.get(position);
        //上传的标题
        String title = info.getTitle();
        if (title != null) {
            holder.item_tv_title_crossover.setText(title);
        }
        //浏览量
        String read_count = info.getRead_count();
        if (read_count != null) {
            holder.item_tv_readNum_crossover.setText(read_count);
        }

        //上传人的昵称
        String nick = info.getNick();
        if (nick != null) {
            holder.item_usrer_nick_crossover.setText(nick);
        }

        //上传的时间
        String createTime = info.getCreate_time();
        if (createTime != null && !createTime.equals("")) {
            long milliseconds = Long.valueOf(createTime);
            Date date = new Date(milliseconds * 1000);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(date);
            holder.item_creat_time_crossover.setText(time);
        }

        //填充点赞的人数和人名
        StringBuffer buffer = new StringBuffer();
        String name=null;
        List<Crossover_person> zan_array =info.getList();
        int count = zan_array.size();
//        Toast.makeText(context,"赞的人信息"+zan_array.toString(),Toast.LENGTH_LONG).show();

        for (int i = 0; i < count; i++) {
            name = zan_array.get(i).getUser_nick();
            buffer.append(name + "; ");
        }
        if(buffer!=null){
            holder.item_zan_names_crossover.setText(count+"人点赞:"+buffer);
        }else{
            holder.item_zan_names_crossover.setText("0 人点赞");
        }

        //获取本人点赞状态
        String user_uid = info.getUser_uid();
//        if(isZan){
//            holder.item_iv_zan_crossover.setImageResource(R.drawable.crossover_good_selector);
//        }

        //封面图片
        String cover = info.getCover();
        if (cover != null) {
            String imagePath = Constants.URL_image03 + cover;
            bitmapUtils.display(holder.item_iv_head_crossover, imagePath);
        }else{
            holder.item_iv_head_crossover.setImageResource(R.mipmap.bg_column);
        }
        return convertView;
    }

    static class ViewHolder {
        private ImageView item_iv_head_crossover, item_user_head_crossover, item_iv_zan_crossover;
        private TextView item_tv_title_crossover, item_tv_readNum_crossover,
                item_usrer_nick_crossover, item_creat_time_crossover,
                item_zan_names_crossover;
    }
}
