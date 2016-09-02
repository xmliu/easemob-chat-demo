package com.xmliu.chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;


/**
 * Date: 2016/3/9 14:42
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class BaseFragment extends Fragment {

    protected BaseApplication mApplication;
    protected Context mContext;
    protected Handler mHandler;
    protected String TAG;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mApplication = (BaseApplication) ((Activity)context).getApplication();
//        NetWorkUtils.isNetworkAvailable(context);
        TAG=this.getClass().getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
