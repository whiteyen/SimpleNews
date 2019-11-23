package com.bilibili.news.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bilibili.news.R;

public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    private ListView mListView;
    private OnloadListener mOnloadListener;
    private View mListViewFooter;
    private boolean isLoading = false;
    private boolean hasMore = true;
    public RefreshLayout(Context context){
        this(context,null);
    }
    public RefreshLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer,null,false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(mListView==null){
            getListView();
        }
    }

    private void getListView() {
        int childs = getChildCount();
        if(childs>0){
            View childView = getChildAt(0);
            if(childView instanceof ListView){
                mListView = (ListView)childView;
                mListView.setOnScrollListener(this);
            }
        }
    }
    public void setLoading(boolean loading){
        isLoading = loading;
        if(isLoading){
            mListView.addFooterView(mListViewFooter);
        }else{
            mListView.removeFooterView(mListViewFooter);
        }
    }

    public void setmOnloadListener(OnloadListener mOnloadListener) {
        this.mOnloadListener = mOnloadListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(hasMore&&mListView.getLastVisiblePosition()==mListView.getAdapter().getCount()-1&&isLoading==false){
            setLoading(true);
            mOnloadListener.onLoad();
        }
    }

    public interface OnloadListener {

        public void onLoad() ;
    }

    public void setHasMore(boolean hasMore){
        this.hasMore = hasMore;
    }
}
