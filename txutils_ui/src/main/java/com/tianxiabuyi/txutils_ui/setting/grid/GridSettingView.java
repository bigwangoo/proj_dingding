package com.tianxiabuyi.txutils_ui.setting.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import java.util.List;

/**
 * Created by ASUS on 2016/12/5.
 */

public class GridSettingView extends GridView {

    public GridSettingView(Context context) {
        super(context);
    }

    public GridSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridSettingView setItems(List<SettingItem> items, Context mContext){
        SettingItemAdapter adapter = new SettingItemAdapter(items,mContext);
        this.setAdapter(adapter);
        return this;
    }
}
