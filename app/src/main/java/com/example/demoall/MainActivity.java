package com.example.demoall;

import androidx.recyclerview.widget.RecyclerView;
import com.example.data.TestInfo;
import com.example.demoall.adapter.TestAdapter;
import com.example.demoall.base.BaseMvpActivity;
import com.example.demoall.model.TestModel;
import com.example.frame.ApiConfig;
import com.example.frame.ICommonModel;
import com.example.frame.LoadTypeConfig;
import com.example.frame.utils.ParamHashMap;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import butterknife.BindView;



public class MainActivity extends BaseMvpActivity   {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int pagdId = 0;
    private List<TestInfo.DataInfo> datas = new ArrayList<>();
    private TestAdapter mTestAdapter;
    private Map<String, Object> params;

    @Override
    public ICommonModel setModel() {
        return new TestModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setUpview() {
        params = new ParamHashMap().add("c", "api").add("a", "getList");
        initRecyclerView(mRecyclerView,mRefreshLayout,mode ->{
            if (mode == LoadTypeConfig.REFRESH){
                pagdId= 0;
                mPresenter.getData(ApiConfig.TEST_GET,LoadTypeConfig.REFRESH,params,pagdId);
            }else{
                pagdId ++;
                mPresenter.getData(ApiConfig.TEST_GET,LoadTypeConfig.MoRE,params,pagdId);
            }
        });
        //适配器
        mTestAdapter = new TestAdapter(MainActivity.this, datas);
        mRecyclerView.setAdapter(mTestAdapter);

    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.TEST_GET,LoadTypeConfig.NoRMAL,params,pagdId);
    }

    @Override
    public void netSuccess(int whichApi, int loadType, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.TEST_GET:
                if (loadType == LoadTypeConfig.MoRE) {
                    mRefreshLayout.finishLoadMore();
                } else if(loadType == LoadTypeConfig.REFRESH){
                    mRefreshLayout.finishRefresh();
                    datas.clear();
                }
                List<TestInfo.DataInfo> datas = ((TestInfo) pD[0]).datas;
                MainActivity.this.datas.addAll(datas);
                mTestAdapter.notifyDataSetChanged();
                break;
        }
    }




}
