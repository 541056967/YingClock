package com.example.yingclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter {

    // 填充数据的list
    private ArrayList<HashMap<String, String>> list;
    // 上下文
    private Context context;
    // 用来导入布局
    private LayoutInflater inflater = null;
    private Context mContext;

    // 构造器
    public MyAdapter(ArrayList<HashMap<String, String>> list, Context context){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // 获得ViewHolder对象
            holder = new ViewHolder();
            // 导入布局并赋值给convertview
            convertView = inflater.inflate(R.layout.item_memo, null);

            holder.title = (TextView) convertView.findViewById(R.id.note_title);
            holder.context = (TextView) convertView.findViewById(R.id.note_content);
            holder.time = (TextView) convertView.findViewById(R.id.note_time);
            holder.card_view = convertView.findViewById(R.id.card_view);

            if(list.get(position).get("compare").equals("1")){
                holder.card_view.setCardBackgroundColor(0x9FE8F0FD);
            }else if(list.get(position).get("compare").equals("0")){
                holder.card_view.setCardBackgroundColor(0xF0FCEDE6);
            }
            // 为view设置标签
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置list中TextView的显示
        holder.title.setText(list.get(position).get("title").toString());
        holder.time.setText(list.get(position).get("time"));

        return convertView;
    }

    public final class ViewHolder{
        public  TextView title,context,time;
        public CardView card_view;
    }
}
