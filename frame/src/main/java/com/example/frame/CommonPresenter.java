package com.example.frame;

public class CommonPresenter implements IcommonPresenter {
    private ICommonView mView;
    private ICommonModel mModel;

    public CommonPresenter(ICommonView mView, ICommonModel mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object[] pD) {
        mView.onSuccess(whichApi,loadType,pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        mView.onFailed(whichApi,pThrowable);
    }

    @Override
    public void getData(int whichApi, Object... pObject) {
            mModel.getData(this,whichApi,pObject);
    }
}
