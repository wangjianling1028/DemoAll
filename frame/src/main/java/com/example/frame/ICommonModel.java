package com.example.frame;

public interface ICommonModel<T> {
    //model层的耗时任务 不处理刷新和加载
    void getData(IcommonPresenter pPreseter,int whichApi,T... params);
}
