package com.tianxiabuyi.txutils.network.model;

/**
 * @author xjh1994
 * @date 2016/8/19
 * @description 上传文件返回
 */
public class TxFileResult extends HttpResult {

    /**
     * img : "http://www.tianxiabuyi.com/pic/logo-web.jpg"
     */

    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
