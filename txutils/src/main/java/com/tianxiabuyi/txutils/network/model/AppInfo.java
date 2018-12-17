package com.tianxiabuyi.txutils.network.model;

import android.view.View;

import com.tianxiabuyi.txutils.util.IntentUtils;
import com.tianxiabuyi.txutils.util.ToastUtils;

/**
 * @author xjh1994
 * @date 2017/4/27
 * @description 公司信息
 */
public class AppInfo {

    /**
     * mail : dev01@tianxiabuyi.com
     * logo : http://manage.eeesys.com/news/attached/image/20170407/20170407131940_90.jpg
     * phone : 0512-69356650
     * mpweixin : 苏州天下布医
     * website : http://www.tianxiabuyi.com
     * address : 苏州市高新区竹园路209号
     * name : 苏州天下布医信息科技有限公司
     * introduce : 苏州天下布医，布医天下
     * weibo : 布医头条
     * post_code : 215000
     */

    private String mail = "dev01@tianxiabuyi.com";
    private String logo = "http://manage.eeesys.com/news/attached/image/20170407/20170407131940_90.jpg";
    private String phone = "0512-69356650";
    private String mpweixin = "苏州天下布医";
    private String website = "http://www.tianxiabuyi.com";
    private String address = "苏州市高新区竹园路209号";
    private String name = "苏州天下布医信息科技有限公司";
    private String introduce = "苏州天下布医，布医天下";
    private String weibo = "布医头条";
    private String post_code = "215000";

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMpweixin() {
        return mpweixin;
    }

    public void setMpweixin(String mpweixin) {
        this.mpweixin = mpweixin;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public void onPhoneClick(View view) {
        IntentUtils.call(view.getContext(), phone);
    }

    public void onWebsiteClick(View view) {
        IntentUtils.startWebsite(view.getContext(), website);
    }

    public void onClipWechat(View view) {
        IntentUtils.clip(view.getContext(), mpweixin);
        ToastUtils.show("已复制微信到剪贴板");
    }
}
