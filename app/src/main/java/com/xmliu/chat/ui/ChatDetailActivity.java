package com.xmliu.chat.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.MessageBody;
import com.easemob.chat.TextMessageBody;
import com.xmliu.chat.BaseActivity;
import com.xmliu.chat.R;
import com.xmliu.chat.adapter.ChatListAdapter;
import com.xmliu.chat.model.ChatListData;
import com.xmliu.chat.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ChatDetailActivity extends BaseActivity {

    private EditText contentET;
    private TextView topNameTV;
    private ImageView backIV;
    private Button sendBtn;
    private NewMessageBroadcastReceiver msgReceiver;

    private ListView mListView;
    private List<ChatListData> mListData = new ArrayList<ChatListData>();
    private ChatListAdapter mAdapter;
    private InputMethodManager imm;

    private String receiveName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x00001:
                        imm.hideSoftInputFromWindow(
                                contentET.getApplicationWindowToken(), 0); // 隐藏键盘
                        mAdapter.notifyDataSetChanged(); // 刷新聊天列表
                        mListView.setSelection(mListData.size()); // 跳转到listview最底部
                        contentET.setText(""); // 清空发送内容
                        break;

                    default:
                        break;
                }
            }

        };

        receiveName = this.getIntent().getStringExtra("userid");

        initView();

        topNameTV.setText(receiveName);

        EMConversation emConversation = EMChatManager.getInstance().getConversation(receiveName);
        int unreadNum = emConversation.getUnreadMsgCount();
        LogUtil.i("TAG", "unreadNum=" + unreadNum);
        if (unreadNum != 0) {
            List<EMMessage> allList = emConversation.loadMoreMsgFromDB(emConversation.getLastMessage().getMsgId(), 30);
            allList.add(emConversation.getLastMessage());
            LogUtil.i("TAG", "allList=" + allList.size());
            List<EMMessage> messageList = new ArrayList<>();
            for (int i = allList.size() - unreadNum; i < allList.size(); i++) {
                messageList.add(allList.get(i));
            }
            for (int i = 0; i < messageList.size(); i++) {
                ChatListData data = new ChatListData();
                MessageBody tmBody = messageList.get(i).getBody();
                if (!messageList.get(i).getFrom().equals(receiveName)) {
                    data.setType(1);
                    data.setSendContent(((TextMessageBody) tmBody).getMessage());
                } else {
                    data.setType(2);
                    data.setReceiveContent(((TextMessageBody) tmBody).getMessage());
                }
                mListData.add(data);
            }
        }
        // 只有注册了广播才能接收到新消息，目前离线消息，在线消息都是走接收消息的广播（离线消息目前无法监听，在登录以后，接收消息广播会执行一次拿到所有的离线消息）
        msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager
                .getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        registerReceiver(msgReceiver, intentFilter);

        imm = (InputMethodManager) contentET.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);

        mAdapter = new ChatListAdapter(ChatDetailActivity.this, mListData);
        mListView.setAdapter(mAdapter);
        mListView.setSelection(mListData.size());
        initEvent();
    }

    private void initView() {
        backIV = (ImageView) findViewById(R.id.common_back_iv);
        contentET = (EditText) findViewById(R.id.chat_content);
        topNameTV = (TextView) findViewById(R.id.chat_list_name);
        sendBtn = (Button) findViewById(R.id.chat_send_btn);
        mListView = (ListView) findViewById(R.id.chat_listview);
    }

    private void initEvent() {
        // TODO Auto-generated method stub
        sendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sendMsg();
            }
        });
        backIV.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        contentET.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View arg0, int keycode, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (keycode == KeyEvent.KEYCODE_ENTER
                        && arg2.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMsg();
                    return true;
                }
                return false;
            }
        });
    }

    void sendMessageHX(String username, final String content) {
        // 获取到与聊天人的会话对象。参数username为聊天人的userid或者groupid，后文中的username皆是如此
        EMConversation conversation = EMChatManager.getInstance()
                .getConversation(username);
        // 创建一条文本消息
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
        // // 如果是群聊，设置chattype,默认是单聊
        // message.setChatType(ChatType.GroupChat);
        // 设置消息body
        TextMessageBody txtBody = new TextMessageBody(content);
        message.addBody(txtBody);
        // 设置接收人
        message.setReceipt(username);
        // 把消息加入到此会话对象中
        conversation.addMessage(message);
        // 发送消息
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.i("TAG", "消息发送失败");
            }

            @Override
            public void onProgress(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.i("TAG", "正在发送消息");
            }

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.i("TAG", "消息发送成功");
                ChatListData data = new ChatListData();
                data.setSendContent(content);
                data.setType(1);
                mListData.add(data);
                mHandler.sendEmptyMessage(0x00001);
            }
        });
    }

    private class NewMessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 注销广播
            abortBroadcast();

            // 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
            String msgId = intent.getStringExtra("msgid");
            // 发送方
            String username = intent.getStringExtra("from");
            // 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
            EMMessage message = EMChatManager.getInstance().getMessage(msgId);
            EMConversation conversation = EMChatManager.getInstance()
                    .getConversation(username);

            MessageBody tmBody = message.getBody();

            ChatListData data = new ChatListData();
            data.setReceiveContent(((TextMessageBody) tmBody).getMessage());
            data.setType(2);
            mListData.add(data);
            mHandler.sendEmptyMessage(0x00001);

            Log.i("TAG", "收到消息：" + ((TextMessageBody) tmBody).getMessage());
            // 如果是群聊消息，获取到group id
            if (message.getChatType() == ChatType.GroupChat) {
                username = message.getTo();
            }

            if (!username.equals(username)) {
                // 消息不是发给当前会话，return
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(msgReceiver);

    }

    private void sendMsg() {
        String content = contentET.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getApplicationContext(), "请输入发送的内容",
                    Toast.LENGTH_SHORT).show();
        } else {
            sendMessageHX(receiveName, content);
        }
    }

}
