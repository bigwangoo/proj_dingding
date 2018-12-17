package com.wangyd.dingding.module.personal.activity;

import android.app.Activity;

import com.tianxiabuyi.villagedoctor.common.db.OfflineDB;
import com.tianxiabuyi.villagedoctor.common.db.model.OfflineContract;
import com.tianxiabuyi.villagedoctor.common.db.model.OfflineFollowup;
import com.tianxiabuyi.villagedoctor.common.db.model.OfflineResident;
import com.tianxiabuyi.villagedoctor.common.db.model.OfflineService;
import com.tianxiabuyi.villagedoctor.common.db.model.OfflineSignIn;
import com.tianxiabuyi.villagedoctor.common.mvp.BasePresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyd
 * @date 2018/11/9
 * @description 个人中心
 */
public class PersonalPresenter extends BasePresenter<PersonalContract.View>
        implements PersonalContract.Presenter {

    public PersonalPresenter(Activity activity) {
        super(activity);
    }

    @Override
    protected void onAttached() {

    }

    /**
     * 异步加载离线数据
     */
    @Override
    public void getOfflineData() {
        Observable<List<OfflineSignIn>> sign = Observable.create(new ObservableOnSubscribe<List<OfflineSignIn>>() {
            @Override
            public void subscribe(ObservableEmitter<List<OfflineSignIn>> e) throws Exception {
                e.onNext(OfflineDB.getSignInData());
            }
        });
        Observable<List<OfflineService>> service = Observable.create(new ObservableOnSubscribe<List<OfflineService>>() {
            @Override
            public void subscribe(ObservableEmitter<List<OfflineService>> e) throws Exception {
                e.onNext(OfflineDB.getFollowUpData());
            }
        });
        Observable<List<OfflineFollowup>> followup = Observable.create(new ObservableOnSubscribe<List<OfflineFollowup>>() {
            @Override
            public void subscribe(ObservableEmitter<List<OfflineFollowup>> e) throws Exception {
                e.onNext(OfflineDB.getFollowupData());
            }
        });
        Observable<List<OfflineContract>> contract = Observable.create(new ObservableOnSubscribe<List<OfflineContract>>() {
            @Override
            public void subscribe(ObservableEmitter<List<OfflineContract>> e) throws Exception {
                e.onNext(OfflineDB.getContractData());
            }
        });
        Observable<List<OfflineResident>> resident = Observable.create(new ObservableOnSubscribe<List<OfflineResident>>() {
            @Override
            public void subscribe(ObservableEmitter<List<OfflineResident>> e) throws Exception {
                e.onNext(OfflineDB.getResidentData());
            }
        });

        Observable
                .zip(sign, service, followup, contract, resident, new Function5<List<OfflineSignIn>, List<OfflineService>, List<OfflineFollowup>, List<OfflineContract>, List<OfflineResident>, String>() {
                    @Override
                    public String apply(List<OfflineSignIn> offlineSignIns,
                                        List<OfflineService> offlineServices,
                                        List<OfflineFollowup> offlineFollowups,
                                        List<OfflineContract> offlineContracts,
                                        List<OfflineResident> offlineResidents) throws Exception {
                        int size1 = 0;
                        int size2 = 0;
                        int size3 = 0;
                        int size4 = 0;
                        int size5 = 0;
                        if (offlineSignIns != null) {
                            size1 = offlineSignIns.size();
                        }
                        if (offlineServices != null) {
                            size2 = offlineServices.size();
                        }
                        if (offlineFollowups != null) {
                            size3 = offlineFollowups.size();
                        }
                        if (offlineContracts != null) {
                            size4 = offlineContracts.size();
                        }
                        if (offlineResidents != null) {
                            size5 = offlineResidents.size();
                        }
                        return String.valueOf(size1 + size2 + size3 + size4 + size5);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        addDisposable(disposable);
                    }

                    @Override
                    public void onNext(String s) {
                        mView.setOffLineDataSize(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
