package com.xmliu.chat.adapter;

import android.content.Context;
import android.widget.TextView;

import com.xmliu.chat.R;
import com.xmliu.chat.model.ConversationListData;
import com.xmliu.chat.util.BaseRecyclerViewAdapter;
import com.xmliu.chat.util.RecyclerHolder;

import java.util.List;

/**
 * Date: 2016/9/1 11:34
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class ConversationListAdapter extends BaseRecyclerViewAdapter {


    public ConversationListAdapter(Context context, List<?> list) {
        super(context, list);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_conversation_list;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        final ConversationListData item = (ConversationListData) list.get(position);
        TextView dateTV = holder.getTextView(R.id.conversation_time_iv);
        TextView contentTV = holder.getTextView(R.id.conversation_message_iv);
        TextView nameTV = holder.getTextView(R.id.conversation_name_iv);

        dateTV.setText(item.getTime());
        contentTV.setText(item.getMessage());
        nameTV.setText(item.getUsername());

    }
}
