/*****************************************************************************
 **                                                                         **
 **               Copyright (C) 2016 XMLIU diyangxia@163.com.               **
 **                                                                         **
 **                          All rights reserved.                           **
 **                                                                         **
 *****************************************************************************/

package com.xmliu.chat.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Date: 2016/1/26 14:17
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO RecyclerView通用ViewHolder
 */
public class RVHolder extends RecyclerView.ViewHolder {

    private RecyclerHolder viewHolder;
    public RVHolder(View itemView) {
        super(itemView);
        viewHolder= RecyclerHolder.getViewHolder(itemView);
    }
    public RecyclerHolder getViewHolder() {
        return viewHolder;
    }
}
