package com.xmliu.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.MessageBody;
import com.easemob.chat.TextMessageBody;
import com.xmliu.chat.BaseFragment;
import com.xmliu.chat.R;
import com.xmliu.chat.adapter.ConversationListAdapter;
import com.xmliu.chat.model.ConversationListData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date: 2016/8/31 18:43
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class FragmentChat extends BaseFragment {
    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recyclerView;
    List<String> nameList = new ArrayList<>();
    List<ConversationListData> conversationList = new ArrayList<>();
    ConversationListAdapter adapter;
    private static final DateFormat FORMATTER_ALL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                swipeRefreshLayout.setRefreshing(false);
                adapter = new ConversationListAdapter(mContext, conversationList);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(mContext,
                                ChatDetailActivity.class).putExtra("userid",
                                conversationList.get(position).getFriend()));
                    }
                });
            }
        };

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

        recyclerView = (RecyclerView) view.findViewById(R.id.conversation_recycleView);
        initRecyclerview();

        EMGroupManager.getInstance().loadAllGroups();
        EMChatManager.getInstance().loadAllConversations();

        loadData();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        return view;
    }

    private void loadData() {
        nameList.clear();
        conversationList.clear();
        nameList = EMChatManager.getInstance().getConversationsUnread();
        for (int i = 0; i < nameList.size(); i++) {
            ConversationListData data = new ConversationListData();
            data.setUsername(nameList.get(i));
            data.setFriend(nameList.get(i));
            EMConversation emConversation = EMChatManager.getInstance().getConversation(nameList.get(i));
            data.setTime(FORMATTER_ALL.format(new Date(emConversation.getLastMessage().getMsgTime())));

            if (emConversation.getLastMessage().getType() == EMMessage.Type.TXT) {
                MessageBody tmBody = emConversation.getLastMessage().getBody();
                data.setMessage(((TextMessageBody) tmBody).getMessage());

            }

            conversationList.add(data);
        }
        mHandler.sendEmptyMessage(0);
    }


    /**
     * 初始化主列表
     */
    private void initRecyclerview() {
        swipeRefreshLayout.setColorSchemeResources(R.color.blue,
                R.color.orange,
                R.color.red);

        // RecycleView初始化配置
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        recyclerView.addItemDecoration(new MainDividerItemDecoration(
//                TravelCalendarActivity.this, MainDividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
    }

}
