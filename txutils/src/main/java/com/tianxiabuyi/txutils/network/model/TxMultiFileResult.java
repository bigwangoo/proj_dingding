package com.tianxiabuyi.txutils.network.model;

import java.util.List;

/**
 * @author xjh1994
 * @date 2016/9/12
 * @description 上传多文件返回
 */
public class TxMultiFileResult extends HttpResult {

    /**
     * result : ["http://file.eeesys.com/attach/1473679343t"]
     */

    private List<String> result;

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
