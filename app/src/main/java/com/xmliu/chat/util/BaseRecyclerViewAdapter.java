/*****************************************************************************
 **                                                                         **
 **               Copyright (C) 2016 XMLIU diyangxia@163.com.               **
 **                                                                         **
 **                          All rights reserved.                           **
 **                                                                         **
 *****************************************************************************/

package com.xmliu.chat.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

/**
 * Date: 2016/1/26 14:08
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: RecyclerView通用适配器
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RVHolder> {
    public List<?> list;
    private Context context;

    public BaseRecyclerViewAdapter(Context context, List<?> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(onCreateViewLayoutID(viewType), null);
        return new RVHolder(view);
    }

    public abstract int onCreateViewLayoutID(int viewType);

    @Override
    public void onViewRecycled(final RVHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final RVHolder holder, final int position) {
        onBindViewHolder(holder.getViewHolder(), position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(null, v, holder.getPosition(), holder.getItemId());
                }
            });
        }
        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(null, v, holder.getPosition(), holder.getItemId());
                    return true;
                }
            });
        }
    }

    public abstract void onBindViewHolder(RecyclerHolder holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }
    public AdapterView.OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    /**
     * 添加Item点击事件
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    /**
     * 添加Item长按事件
     */
    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
