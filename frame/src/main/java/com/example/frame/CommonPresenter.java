package com.example.frame;



import java.lang.ref.SoftReference;

public class CommonPresenter<V extends ICommonView,M extends ICommonModel> implements IcommonPresenter {
    private SoftReference<V> mView;
    private SoftReference<M> mModel;

    public CommonPresenter(V pView, M pModel) {
     mView =  new SoftReference<>(pView);
     mModel =  new SoftReference<>(pModel);

    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object[] pD) {
      if (mView !=null && mView.get() !=null) mView.get().onSuccess(whichApi,loadType,pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        if (mView !=null && mView.get() !=null) mView.get().onFailed(whichApi,pThrowable);
    }

    @Override
    public void getData(int whichApi, Object... pObjects) {
        if (mModel !=null && mModel.get() !=null) mModel.get().getData(this,whichApi,pObjects);
    }

    public void clear(){
        if (mView !=null){
            mView.clear();
            mView = null;
        }
        if (mModel !=null){
            mModel.clear();
            mModel = null;
        }
    }
}
