package com.wangyd.dingding.core.mvp;

import android.os.Bundle;

/**
 * @author wangyd
 * @date 2018/10/30
 * @description 定义 P 层，生命周期与 V 层同步
 */
public interface IPresenter<V extends IView> {

    void onAttachView(V view, Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle savedInstanceState);

    void onDetachView(boolean retainInstance);

    void onDestroy();
}
