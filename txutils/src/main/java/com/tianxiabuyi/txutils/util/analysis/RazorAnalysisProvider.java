//package com.tianxiabuyi.txutils.util.analysis;
//
//import android.content.Context;
//
//
///**
// * Created by xjh1994 on 2016/9/21.
// * Cobub Razor统计
// * @url http://www.cobub.com/docs/razor:android-developer-guide
// */
//
//public class RazorAnalysisProvider implements IAnalysisProvider {
//
//    @Override
//    public void init(Context context, Object... params) {
//        UmsAgent.init(context, (String) params[0], (String) params[1]);
//    }
//
//    @Override
//    public void setDefaultReportPolicy(Context context, Object policy) {
//        UmsAgent.setDefaultReportPolicy(context, (UmsAgent.SendPolicy) policy);
//    }
//
//    @Override
//    public void onResume(Context context) {
//        UmsAgent.onResume(context);
//    }
//
//    @Override
//    public void onPause(Context context) {
//        UmsAgent.onPause(context);
//    }
//
//    @Override
//    public void onEvent(Context context, String eventId) {
//        UmsAgent.onEvent(context, eventId);
//    }
//
//    @Override
//    public void onEvent(Context context, String eventId, String label) {
//        UmsAgent.onEvent(context, eventId, label, 1);
//    }
//
//    @Override
//    public void onError(Context context, String eventId) {
//        UmsAgent.onError(context, "Exception", eventId);
//    }
//
//    @Override
//    public void onFragmentResume(Context context, String pageName) {
//        //TODO NullPointerException
////        UmsAgent.onFragmentResume(context, pageName);
//    }
//
//    @Override
//    public void bindUserId(Context context, String userId) {
//        UmsAgent.bindUserIdentifier(context, userId);
//    }
//
//    @Override
//    public void setDebugMode(boolean isDebug) {
//        UmsAgent.setDebugEnabled(isDebug);
//    }
//
//    @Override
//    public void update(Context context) {
//        UmsAgent.update(context);
//    }
//}
