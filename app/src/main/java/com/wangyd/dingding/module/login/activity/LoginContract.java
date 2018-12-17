package com.wangyd.dingding.module.login.activity;

import com.wangyd.dingding.core.mvp.IPresenter;
import com.wangyd.dingding.core.mvp.IView;

/**
 * @author wangyd
 * @date 2018/12/12
 * @description 配置常量
 */
public class LoginContract {

    interface LoginView extends IView {

    }

    interface LoginPresenter extends IPresenter<IView> {

    }
}
