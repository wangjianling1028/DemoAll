package com.example.frame;

import com.example.data.TestInfo;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestModel implements ICommonModel {
    @Override
    public void getData(final IcommonPresenter pPreseter, final int whichApi, Object[] params) {
        final int loadType=(int) params[0];
        Map param=(Map) params[1];
        int pageId = (int) params[2];
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://static.owspace.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        Observable testData = retrofit.create(IService.class).getTestData(param, pageId);
        testData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                    pPreseter.onSuccess(whichApi,loadType,o);
                    }

                    @Override
                    public void onError(Throwable e) {
                    pPreseter.onFailed(whichApi,e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
