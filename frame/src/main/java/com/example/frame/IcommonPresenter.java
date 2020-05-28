package com.example.frame;

public interface IcommonPresenter<p> extends ICommonView {
    //作为中间层发起网络请求   将请求结果回调view
    void getData(int whichApi,p... pPs);
}
