package com.bilibili.news.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bilibili.news.R;
import com.bilibili.news.activity.WebViewActivity;
import com.bilibili.news.adapter.NewsListAdapter;
import com.bilibili.news.engin.JNI;
import com.bilibili.news.ui.widget.RefreshLayout;
import com.bilibili.news.utils.NewsBean;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public abstract class BaseFragment extends Fragment {
    RefreshLayout refreshLayout;
    ListView listView;
    ImageView iv;
    ProgressBar progressBar;
    NewsListAdapter adapter;
    List<NewsBean.ResultBean.DataBean> datas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_toutiao, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        listView = view.findViewById(R.id.list_view);
        iv = view.findViewById(R.id.iv_error);
        progressBar = view.findViewById(R.id.progress_bar);
        initView();
        initData();
        return view;
    }

    private void initData() {
        progressBar.setVisibility(View.VISIBLE);
        iv.setVisibility(View.GONE);
        refresh();
    }


    @SuppressLint("ResourceAsColor")
    private void initView() {
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        refreshLayout.setHasMore(false);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean.ResultBean.DataBean dataBean = datas.get(position);
                Intent intent = WebViewActivity.newIntent(getContext(),dataBean.getUrl());
                getActivity().startActivity(intent);
            }
        });

    }
    private void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        refreshLayout.setLoading(false);
                        refreshLayout.setHasMore(false);
                    }
                });
            }

        }).start();
    }
    private void refresh() {
        com.bilibili.news.engin.Network.createApi().getNews(JNI.getAppKey(),getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsBean>() {
                    @Override
                    public void call(NewsBean newsBean) {
                        Log.d("ToutiaoFragment", "success");
                        refreshLayout.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                        datas = newsBean.getResult().getData();
                        adapter = new NewsListAdapter(datas,getContext());
                        listView.setAdapter(adapter);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iv.setVisibility(View.VISIBLE);
                        refreshLayout.setEnabled(false);
                        progressBar.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(),"fail",Toast.LENGTH_SHORT).show();
                        Log.d("ToutiaoFragment","fail");
                        throwable.printStackTrace();
                    }
                });
    }
    public abstract String getType();


}
