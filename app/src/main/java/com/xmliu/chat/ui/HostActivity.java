package com.xmliu.chat.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TabHost;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
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

    private BottomBar bottomBar;

    private FrameLayout contentTV;
    private FragmentTabHost mFragmentTabhost;

    public static final String SHOW_OF_CHAT_TAG = "chat";
    public static final String SHOW_OF_FRIENDS_TAG = "friends";
    public static final String SHOW_OF_MINE_TAG = "mine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_tab);
        AppManager.getInstance().addActivity(this);
//        contentTV = (FrameLayout) findViewById(R.id.contentContainer);
        mFragmentTabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

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

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                // The tab with id R.id.tab_favorites was selected,
                // change your content accordingly.
                if (tabId == R.id.tab_chat) {
                    Log.i("TAG", "tab_discover");
                    mFragmentTabhost.setCurrentTabByTag(SHOW_OF_CHAT_TAG);
//                    contentTV.setText("发现");
                } else if (tabId == R.id.tab_friends) {
                    Log.i("TAG", "tab_friends");

                    mFragmentTabhost.setCurrentTabByTag(SHOW_OF_FRIENDS_TAG);
//                    contentTV.setText("朋友");
                } else if (tabId == R.id.tab_mine) {
                    Log.i("TAG", "tab_mine");
                    mFragmentTabhost.setCurrentTabByTag(SHOW_OF_MINE_TAG);
//                    contentTV.setText("我的");
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_friends) {
                    // The tab with id R.id.tab_favorites was reselected,
                    // change your content accordingly.
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
