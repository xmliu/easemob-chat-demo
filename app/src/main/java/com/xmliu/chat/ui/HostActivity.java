package com.xmliu.chat.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.xmliu.chat.AppManager;
import com.xmliu.chat.R;
import com.xmliu.chat.util.XmTools;

/**
 * Date: 2016/8/31 16:38
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class HostActivity extends FragmentActivity {

    private FragmentTabHost mFragmentTabhost;

    public static final String SHOW_OF_CHAT_TAG = "chat";
    public static final String SHOW_OF_FRIENDS_TAG = "friends";
    public static final String SHOW_OF_MINE_TAG = "mine";

    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_tab);
        AppManager.getInstance().addActivity(this);
        mFragmentTabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        radioGroup = (RadioGroup) findViewById(R.id.main_radio);

        mFragmentTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        TabHost.TabSpec tabSpec0 = mFragmentTabhost.newTabSpec(SHOW_OF_CHAT_TAG)
                .setIndicator("0");
        TabHost.TabSpec tabSpec1 = mFragmentTabhost.newTabSpec(SHOW_OF_FRIENDS_TAG)
                .setIndicator("1");
        TabHost.TabSpec tabSpec2 = mFragmentTabhost.newTabSpec(SHOW_OF_MINE_TAG)
                .setIndicator("2");

        mFragmentTabhost.addTab(tabSpec0, FragmentChat.class, null);
        mFragmentTabhost.addTab(tabSpec1, FragmentFriends.class, null);
        mFragmentTabhost.addTab(tabSpec2, FragmentMine.class, null);

        mFragmentTabhost.setCurrentTabByTag(SHOW_OF_CHAT_TAG);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_chat) {
                    Log.i("TAG", "tab_discover");
                    mFragmentTabhost.setCurrentTabByTag(SHOW_OF_CHAT_TAG);
                } else if (checkedId == R.id.radio_friends) {
                    Log.i("TAG", "tab_friends");

                    mFragmentTabhost.setCurrentTabByTag(SHOW_OF_FRIENDS_TAG);
                } else if (checkedId == R.id.radio_mine) {
                    Log.i("TAG", "tab_mine");
                    mFragmentTabhost.setCurrentTabByTag(SHOW_OF_MINE_TAG);
                }
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            XmTools.showExitDialog(HostActivity.this);
        }

        return super.onKeyDown(keyCode, event);
    }


}
