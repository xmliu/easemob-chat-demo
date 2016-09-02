package com.xmliu.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMNotifier;
import com.easemob.exceptions.EaseMobException;
import com.xmliu.chat.BaseFragment;
import com.xmliu.chat.R;
import com.xmliu.chat.adapter.FriendListAdapter;
import com.xmliu.chat.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2016/8/31 18:43
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class FragmentFriends extends BaseFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mListView;
    private ImageView mAddBtn;
    private FriendListAdapter mAdapter;
    private List<String> userList = new ArrayList<String>();
    /* 常量 */
    private final int CODE_ADD_FRIEND = 0x00001;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case CODE_ADD_FRIEND:
                        Toast.makeText(mContext, "请求发送成功，等待对方验证",
                                Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }

        };
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

        mListView = (ListView) view.findViewById(R.id.chat_listview);
        mAddBtn = (ImageView) view.findViewById(R.id.chat_add_btn);

        swipeRefreshLayout.setColorSchemeResources(R.color.blue,
                R.color.orange,
                R.color.red);


        EMContactManager.getInstance().setContactListener(
                new MyContactListener());
        EMChat.getInstance().setAppInited(); // 这句代码不能少

        initList();

        mAddBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showAddDialog();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                startActivity(new Intent(mContext,
                        ChatDetailActivity.class).putExtra("userid",
                        userList.get(arg2)));
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                // TODO Auto-generated method stub
                showDeleteDialog(userList.get(arg2));
                return true;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initList();
            }
        });
        return view;
    }

    private void initList() {
        try {
            userList.clear();
            userList = EMContactManager.getInstance().getContactUserNames();
            swipeRefreshLayout.setRefreshing(false);
            mAdapter = new FriendListAdapter(mContext, userList);
            mListView.setAdapter(mAdapter);
        } catch (EaseMobException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            Log.i("TAG", "usernames errcode==>" + e1.getErrorCode());
            Log.i("TAG", "usernames errcode==>" + e1.getMessage());
        }// 需异步执行
    }

    private class MyContactListener implements EMContactListener {

        @Override
        public void onContactAgreed(String username) {
            // 好友请求被同意
            Log.i("TAG", "onContactAgreed==>" + username);
            // 提示有新消息
            EMNotifier.getInstance(mContext).notifyOnNewMsg();
            Toast.makeText(mContext, username + "同意了你的好友请求",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onContactRefused(String username) {
            // 好友请求被拒绝
            Log.i("TAG", "onContactRefused==>" + username);
        }

        @Override
        public void onContactInvited(String username, String reason) {
            // 收到好友添加请求
            Log.i("TAG", username + "onContactInvited==>" + reason);
            showAgreedDialog(username, reason);
            EMNotifier.getInstance(mContext).notifyOnNewMsg();
        }

        @Override
        public void onContactDeleted(List<String> usernameList) {
            // 好友被删除时回调此方法
            Log.i("TAG", "usernameListDeleted==>" + usernameList.size());
        }

        @Override
        public void onContactAdded(List<String> usernameList) {
            // 添加了新的好友时回调此方法
            for (String str : usernameList) {
                Log.i("TAG", "usernameListAdded==>" + str);
            }
        }
    }

    private void showAgreedDialog(final String user, String reason) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .title("应用提示")
                .theme(Theme.LIGHT)
                .content(
                        "用户 " + user + " 想要添加您为好友，是否同意？\n" + "验证信息：" + reason)
                .positiveText("同意").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        try {
                            EMChatManager.getInstance().acceptInvitation(user);
                            initList();
                        } catch (EaseMobException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.i("TAG",
                                    "showAgreedDialog1==>" + e.getErrorCode());
                        }

                    }
                })
                .negativeText("拒绝")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        try {
                            EMChatManager.getInstance().refuseInvitation(user);
                        } catch (EaseMobException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.i("TAG",
                                    "showAgreedDialog2==>" + e.getErrorCode());
                        }
                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();


    }

    private void showAddDialog() {

        new MaterialDialog.Builder(mContext)
                .title("添加好友")
                .customView(R.layout.chat_add_friends, true)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                        EditText mIdET = (EditText) dialog.getCustomView()
                                .findViewById(R.id.chat_add_friend_id);
                        EditText mReasonET = (EditText) dialog.getCustomView()
                                .findViewById(R.id.chat_add_friend_reason);

                        String idStr = mIdET.getText()
                                .toString().trim();
                        String reasonStr = mReasonET.getText()
                                .toString().trim();
                        try {
                            EMContactManager.getInstance()
                                    .addContact(idStr,
                                            reasonStr);
                            mHandler.sendEmptyMessage(CODE_ADD_FRIEND);
                        } catch (EaseMobException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            LogUtil.i("TAG", "addContacterrcode==>"
                                    + e.getErrorCode());
                        }

                    }
                })
                .negativeText("取消")
                .show();

    }
    private void showDeleteDialog(final String user) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .title("应用提示")
                .theme(Theme.LIGHT)
                .content("确定删除好友  " + user + " 吗？\n")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        // EMChatManager.getInstance().logout();
                        try {
                            EMContactManager.getInstance().deleteContact(user);
                            initList();
                        } catch (EaseMobException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.i("TAG",
                                    "showAgreedDialog1==>" + e.getErrorCode());
                        }

                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();

    }

}
