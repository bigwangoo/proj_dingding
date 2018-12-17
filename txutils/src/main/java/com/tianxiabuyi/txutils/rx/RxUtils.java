package com.tianxiabuyi.txutils.rx;

/**
 * Created by xjh1994 on 2016/9/1.
 */

public class RxUtils {

//    public static  <T> Observable.Transformer<T, T> applySchedulers() {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
//
//    public static <T extends HttpResult> Observable.Transformer<T, T> apply() {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .lift(ApiClient.<T>apiErrorOperator());
//            }
//        };
//    }
}
