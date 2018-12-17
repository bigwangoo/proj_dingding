package com.tianxiabuyi.txutils.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianxiabuyi.txutils.base.impl.IBaseTxFragment;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 若把初始化内容放到initData实现 就是采用Lazy方式加载的Fragment
 * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 * <p>
 * 注1:
 * 如果是与ViewPager一起使用，调用的是 setUserVisibleHint。
 * <p>
 * 注2:
 * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是 onHiddenChanged.
 * 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 * eg: transaction.hide(aFragment); transaction.show(aFragment);
 * <p>
 * Created by Mumu on 2015/11/2.
 */
public abstract class BaseLazyFragment extends Fragment implements IBaseTxFragment {
    /**
     * 是否可见状态
     */
    private boolean isFragmentVisible;
    /**
     * 标志位，View已经初始化完成。
     */
    private boolean isPrepared;
    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;
    /**
     * 忽略isFirstLoad的值，强制刷新重走initData()，但仍要Visible & Prepared
     */
    private boolean forceLoad = false;

    protected View mView;
    private Unbinder unbinder;
    /**
     * EventBus
     */
    private boolean isEventBusEnabled = false;
    /**
     * 网络请求
     */
    private List<TxCall> mCalls = new ArrayList<>();
    /**
     * RxJava
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    protected void onVisible() {
        isFragmentVisible = true;
        lazyLoad();
    }

    protected void onInvisible() {
        isFragmentVisible = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.size() > 0) {
            initArguments(bundle);
        }
    }

    /**
     * 被ViewPager移出的Fragment 下次显示时会从getArguments()中重新获取数据
     * 所以若需要刷新被移除Fragment内的数据需要重新put数据
     * eg:
     * Bundle args = getArguments();
     * if (args != null) { args.putParcelable(KEY, info); }
     */
    @Override
    public void initArguments(Bundle bundle) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutByXml(), container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isFirstLoad = true;
        initArguments(getArguments());
        initView();
        isPrepared = true;
        lazyLoad();

        if (isEventBusEnabled) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView isPrepared = true;
     */
    protected void lazyLoad() {
        if (isPrepared() && isFragmentVisible()) {
            if (forceLoad || isFirstLoad()) {
                forceLoad = false;
                isFirstLoad = false;
                initData();
            }
        }
    }

    public boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public boolean isFirstLoad() {
        return isFirstLoad;
    }

    public void setForceLoad(boolean forceLoad) {
        this.forceLoad = forceLoad;
    }

    /**
     * 开启EventBus
     */
    public void setEventBusEnabled() {
        isEventBusEnabled = true;
    }

    /**
     * 添加网络请求
     */
    public void addCallList(TxCall call) {
        if (mCalls == null) {
            mCalls = new ArrayList<>();
        }
        mCalls.add(call);
    }

    /**
     * 取消网络请求，onDestroy取消
     */
    public void clearCall() {
        if (mCalls != null) {
            for (int i = 0; i < mCalls.size(); i++) {
                TxCall call = mCalls.get(i);
                if (call != null) {
                    call.cancel();
                }
            }
        }
        mCalls = null;
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消订阅, onDestroy取消
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onDestroyView() {
        isPrepared = false;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        clearCall();
        clearDisposable();
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    @NonNull
    public View getView() {
        return mView;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(int resId) {
        return (T) (getView().findViewById(resId));
    }

    @Override
    public void startAnimActivity(Class<?> cla) {
        getActivity().startActivity(new Intent(getActivity(), cla));
    }

    @Override
    public void startAnimActivity(Intent intent) {
        getActivity().startActivity(intent);
    }

    @Override
    public void toast(String msg) {
        ToastUtils.showShort(getActivity(), msg);
    }

    @Override
    public void toast(int msgId) {
        ToastUtils.showShort(getActivity(), msgId);
    }

    @Override
    public void toast(TxException exception) {
        ToastUtils.showShort(getActivity(), exception.getDetailMessage());
    }

    @Override
    public void toastLong(String msg) {
        ToastUtils.showLong(getActivity(), msg);
    }

    @Override
    public void toastLong(int msgId) {
        ToastUtils.showLong(getActivity(), msgId);
    }

}
