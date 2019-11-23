package com.bilibili.news.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.news.R;
import com.bilibili.news.utils.NewsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsListAdapter extends BaseAdapter {
    private List<NewsBean.ResultBean.DataBean> data;
    private Context context;
    public NewsListAdapter(List<NewsBean.ResultBean.DataBean>data,Context context){
        this.data=data;
        this.context = context;
    }
    public void setData(List<NewsBean.ResultBean.DataBean>data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = View.inflate(context, R.layout.item_news,null);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = view.findViewById(R.id.iv_icon);
            viewHolder.tvDate = view.findViewById(R.id.tv_date);
            viewHolder.tvFrom = view.findViewById(R.id.tv_from);
            viewHolder.tvTitle = view.findViewById(R.id.tv_title);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        NewsBean.ResultBean.DataBean dataBean = data.get(position);
        viewHolder.tvTitle.setText(dataBean.getTitle());
        viewHolder.tvFrom.setText(dataBean.getAuthor_name());
        viewHolder.tvDate.setText(dataBean.getDate());
        if(!TextUtils.isEmpty(dataBean.getThumbnail_pic_s())){
            Picasso.with(context)
                    .load(dataBean.getThumbnail_pic_s())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(viewHolder.ivIcon);
        }
        return view;
    }

    public static class ViewHolder{
        ImageView ivIcon;
        TextView tvTitle,tvFrom,tvDate;
    }
}
