package com.tianxiabuyi.txutils_ui.setting.grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.tianxiabuyi.txutils_ui.R;

import java.util.List;

/**
 * Created by ASUS on 2016/12/5.
 */

public class SettingItemAdapter extends BaseAdapter {

    private List<SettingItem> itemList;
    private Context context;
    private LayoutInflater inflater;

    public SettingItemAdapter(List<SettingItem> data, Context context){
        this.itemList = data;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.tx_grid_setting_item,null);
            holder.icon = (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageResource(itemList.get(position).getIcon());
        holder.tvName.setText(itemList.get(position).getName());

        return convertView;
    }

    class ViewHolder{
        public ImageView icon;
        public TextView tvName;
    }
}
