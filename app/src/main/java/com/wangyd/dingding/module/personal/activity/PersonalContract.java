package com.wangyd.dingding.module.personal.activity;

import com.tianxiabuyi.villagedoctor.common.mvp.IPresenter;
import com.tianxiabuyi.villagedoctor.common.mvp.IView;

/**
 * @author wangyd
 * @date 2018/11/9
 * @description description
 */
public class PersonalContract {

    public interface View extends IView {
        /**
         * 设置离线状态
         */
        void setOffLineModeState();

        /**
         * 设置离线数量
         */
        void setOffLineDataSize(String size);
    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 获取离线数据
         */
        void getOfflineData();
    }
}