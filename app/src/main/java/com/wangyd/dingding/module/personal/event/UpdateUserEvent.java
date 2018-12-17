package com.wangyd.dingding.module.personal.event;

/**
 * @author wangyd
 * @date 2018/6/20
 * @description 更新头像
 */
public class UpdateUserEvent {
    private String avatar;

    public UpdateUserEvent(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
