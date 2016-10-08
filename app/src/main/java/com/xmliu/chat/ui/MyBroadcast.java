package com.xmliu.chat.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

/**
 * Date: 2016/10/8 13:13
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class MyBroadcast extends BroadcastReceiver {

    private static MyBroadcast registerReceiver = new MyBroadcast();
    private static IntentFilter intentFilter;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String packageName = intent.getData().getSchemeSpecificPart();
        if(Intent.ACTION_PACKAGE_ADDED.equals(action)){
            PackageManager pm = context.getPackageManager();
            Intent myIntent = new Intent();
            try {
                myIntent = pm.getLaunchIntentForPackage(packageName);
            }catch (Exception e){
                e.printStackTrace();
            }
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
        }

    }

    private static void registerReceiver(Context context) {
        intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        context.registerReceiver(registerReceiver, intentFilter);

    }

    private static void unRegisterReceiver(Context context) {
        context.unregisterReceiver(registerReceiver);


    }
}
