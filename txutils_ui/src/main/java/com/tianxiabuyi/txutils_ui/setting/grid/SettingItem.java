package com.tianxiabuyi.txutils_ui.setting.grid;

/**
 * Created by ASUS on 2016/12/5.
 */

public class SettingItem {
    private String name;
    private int icon;

    public SettingItem(String name, int icon){
        this.name = name;
        this.icon = icon;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
