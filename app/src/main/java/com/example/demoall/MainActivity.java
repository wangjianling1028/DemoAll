package com.example.demoall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.textservice.TextInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.data.TestInfo;
import com.example.demoall.adapter.TestAdapter;
import com.example.frame.ApiConfig;
import com.example.frame.CommonPresenter;
import com.example.frame.ICommonView;
import com.example.frame.LoadTypeConfig;
import com.example.frame.TestModel;
import com.example.frame.utils.ParamHashMap;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements ICommonView {
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private int pagdId = 0;
    private List<TestInfo.DataInfo> datas = new ArrayList<>();
    private TestModel mModel;
    private CommonPresenter mPresenter;
    private TestAdapter mTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel = new TestModel();
        mPresenter = new CommonPresenter(this, mModel);
        //找控件
        mRecyclerView = findViewById(R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.refreshLayout);

        final Map<String,Object> params = new ParamHashMap().add("c", "api").add("a", "getList");
        //上拉加载，下拉刷新
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pagdId =0;
                mPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH,params,pagdId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pagdId++;
                mPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH,params,pagdId);
            }
        });

         mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
         mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        //适配器
        mTestAdapter = new TestAdapter(MainActivity.this, datas);
        mRecyclerView.setAdapter(mTestAdapter);
    }
    //成功方法
    @Override
    public void onSuccess(int whichApi, int loadType, Object[] pD) {
      switch (whichApi){
          case ApiConfig.TEST_GET:
              if (loadType == LoadTypeConfig.MoRE){
                  mRefreshLayout.finishLoadMore();
              }else{
                  mRefreshLayout.finishRefresh();
                  datas.clear();
              }

              List<TestInfo.DataInfo> datas=((TestInfo)pD[0]).datas;
                MainActivity.this.datas.addAll(datas);
                mTestAdapter.notifyDataSetChanged();
              break;
      }
    }
    //失败方法 三目运算
    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        Toast.makeText(this, pThrowable.getMessage()!=null ? pThrowable.getMessage() : "网络请求发生错误", Toast.LENGTH_SHORT).show();
    }
}
