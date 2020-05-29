package com.example.demoall.model;


import com.example.frame.ApiConfig;
import com.example.frame.ICommonModel;
import com.example.frame.IcommonPresenter;
import com.example.frame.NetManger;
import java.util.Map;



//一个独立单元使用一个model
public class TestModel implements ICommonModel {
    NetManger mMager = NetManger.getInstance();

    @Override
    public void getData(final IcommonPresenter pPreseter, final int whichApi, Object[] params) {
       switch (whichApi){
           case ApiConfig.TEST_GET:
               final int loadType=(int) params[0];
               Map param=(Map) params[1];
               int pageId = (int) params[2];
                mMager.netWrok(mMager.getService().getTestData(param,pageId),pPreseter,whichApi,loadType);
               break;
           case ApiConfig.ADVERT:
               break;
       }
    }
}
