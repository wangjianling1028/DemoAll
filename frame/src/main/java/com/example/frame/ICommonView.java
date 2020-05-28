package com.example.frame;

public interface ICommonView<D> {
    //成功回调    whichApi 成功接口标识  loadType 类型的回调   pD  一般是实体类的回调 长度未写死
    void onSuccess(int whichApi,int loadType,D... pD);

    //失败回调 whichApi 那个接口失败了  pThrowable 失败的具体描述
    void onFailed(int whichApi,Throwable pThrowable);

}
