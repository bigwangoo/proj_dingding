//package com.tianxiabuyi.txutils.util.analysis;
//
//import android.content.Context;
//
//
///**
// * Created by xjh1994 on 2016/9/21.
// * 应用统计工具类
// */
//
//public class AnalysisUtils implements IAnalysisProvider {
//
//    private volatile static AnalysisUtils mInstance;
//    private IAnalysisProvider mAnalysisProvider;
//
//    public AnalysisUtils() {
//        mAnalysisProvider = new RazorAnalysisProvider();
//    }
//
//    public static AnalysisUtils getInstance() {
//        if (mInstance == null) {
//            synchronized (AnalysisUtils.class) {
//                if (mInstance == null) {
//                    mInstance = new AnalysisUtils();
//                    return mInstance;
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    public synchronized void init(IAnalysisProvider analysisProvider) {
//        this.mAnalysisProvider = analysisProvider;
//    }
//
//    @Override
//    public void init(Context context, Object... params) {
//        mAnalysisProvider.init(context, params);
//    }
//
//    @Override
//    public void setDefaultReportPolicy(Context context, Object policy) {
//        mAnalysisProvider.setDefaultReportPolicy(context, policy);
//    }
//
//    @Override
//    public void onResume(Context context) {
//        mAnalysisProvider.onResume(context);
//    }
//
//    @Override
//    public void onPause(Context context) {
//        mAnalysisProvider.onPause(context);
//    }
//
//    @Override
//    public void onEvent(Context context, String eventId) {
//        mAnalysisProvider.onEvent(context, eventId);
//    }
//
//    @Override
//    public void onEvent(Context context, String eventId, String label) {
//        mAnalysisProvider.onEvent(context, eventId, label);
//    }
//
//    @Override
//    public void onError(Context context, String eventId) {
//        mAnalysisProvider.onError(context, eventId);
//    }
//
//    @Override
//    public void onFragmentResume(Context context, String pageName) {
//        mAnalysisProvider.onFragmentResume(context, pageName);
//    }
//
//    @Override
//    public void bindUserId(Context context, String userId) {
//        mAnalysisProvider.bindUserId(context, userId);
//    }
//
//    @Override
//    public void setDebugMode(boolean isDebug) {
//        mAnalysisProvider.setDebugMode(isDebug);
//    }
//
//    @Override
//    public void update(Context context) {
//        mAnalysisProvider.update(context);
//    }
//}
