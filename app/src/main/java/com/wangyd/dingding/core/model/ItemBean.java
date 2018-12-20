package com.wangyd.dingding.core.model;

import android.support.annotation.DrawableRes;

/**
 * @author wangyd
 * @date 2018/12/21
 * @description
 */
public class ItemBean {

    private String title;
    private int drawableId;

    public ItemBean(String title, @DrawableRes int drawableId) {
        this.title = title;
        this.drawableId = drawableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

}
