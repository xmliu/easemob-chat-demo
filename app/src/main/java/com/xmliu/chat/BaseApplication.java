package com.xmliu.chat;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.Log;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.easemob.chat.TextMessageBody;
import com.xmliu.chat.ui.ChatDetailActivity;

import java.util.Iterator;
import java.util.List;

public class BaseApplication extends Application {

	private static final String TAG = BaseApplication.class.getSimpleName();

	private static BaseApplication mInstance = null;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		// 如果app启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process
		// name就立即返回

		if (processAppName == null
				|| !processAppName.equalsIgnoreCase("com.xmliu.chat")) {
			Log.e(TAG, "enter the service process!");
			// "com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名

			// 则此application::onCreate 是被service 调用的，直接返回
			return;
		}
		
//		EMChat.getInstance().setAutoLogin(false);
		EMChat.getInstance().init(getApplicationContext());
		// 在做代码混淆的时候需要设置成false
		EMChat.getInstance().setDebugMode(true);
		initHXOptions();
		mInstance = this;

	}

	protected void initHXOptions() {
		Log.d(TAG, "init HuanXin Options");

		// 获取到EMChatOptions对象
		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		// 默认添加好友时，是不需要验证的true，改成需要验证false
		options.setAcceptInvitationAlways(false);
		// 默认环信是不维护好友关系列表的，如果app依赖环信的好友关系，把这个属性设置为true
		options.setUseRoster(true);
		options.setNumberOfMessagesLoaded(1);
		//设置自定义的文字提示
		options.setNotifyText(new OnMessageNotifyListener() {

			@Override
			public String onNewMessageNotify(EMMessage message) {
				//可以根据message的类型提示不同文字，这里为一个简单的示例
				return message.getFrom() + ":"+((TextMessageBody)message.getBody()).getMessage();
			}

			@Override
			public String onLatestMessageNotify(EMMessage message, int fromUsersNum, int messageNum) {
				return fromUsersNum + "个基友，发来了" + messageNum + "条新消息";
			}

			@Override
			public String onSetNotificationTitle(EMMessage emMessage) {
				return null;
			}

			@Override
			public int onSetSmallIcon(EMMessage emMessage) {
				return 0;
			}
		});
		//设置notification点击listener
		options.setOnNotificationClickListener(new OnNotificationClickListener() {

			@Override
			public Intent onNotificationClick(EMMessage message) {
				Intent intent = new Intent(getApplicationContext(), ChatDetailActivity.class);
				EMMessage.ChatType chatType = message.getChatType();
				if(chatType == EMMessage.ChatType.Chat){ //单聊信息
					intent.putExtra("userid", message.getFrom());
				}
				return intent;
			}
		});
	}

	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = this.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
					.next());
			try {
				if (info.pid == pID) {
					CharSequence c = pm.getApplicationLabel(pm
							.getApplicationInfo(info.processName,
									PackageManager.GET_META_DATA));
					// Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
					// info.processName +"  Label: "+c.toString());
					// processName = c.toString();
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				// Log.d("Process", "Error>> :"+ e.toString());
			}
		}
		return processName;
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		Log.i(TAG, "onLowMemory");
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onTerminate");
		super.onTerminate();
	}

	public static BaseApplication getInstance() {
		return mInstance;
	}

}
