/*****************************************************************************
 **                                                                         **
 **               Copyright (C) 2016 XMLIU diyangxia@163.com.               **
 **                                                                         **
 **                          All rights reserved.                           **
 **                                                                         **
 *****************************************************************************/
package com.xmliu.chat.util;

import android.util.Log;

/**
 * Date: 2015/11/25 16:30
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: 日志工具类，可设置上线和调试模式下是否显示日志。来源于guolin的书
 */
public class LogUtil {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static int LEVEL = VERBOSE;

    public static void setDebug(boolean isDebug){
        if(!isDebug){
            LEVEL = NOTHING;
        }else{
            LEVEL = VERBOSE;
        }
    }

    public static void v(String tag,String msg){
        if(LEVEL <= VERBOSE){
            Log.v("TAG", tag + " >>>>>>>> " + msg);
        }
    }

    public static void d(String tag,String msg){
        if(LEVEL <= DEBUG){
            Log.d("TAG", tag + " >>>>>>>> " + msg);
        }
    }

    public static void i(String tag,String msg){
        if(LEVEL <= INFO){
            Log.i("TAG", tag + " >>>>>>>> " + msg);
        }
    }

    public static void w(String tag,String msg){
        if(LEVEL <= WARN){
            Log.w("TAG", tag + " >>>>>>>> " + msg);
        }
    }

    public static void e(String tag,String msg){
        if(LEVEL <= ERROR){
            Log.e("TAG", tag + " >>>>>>>> " + msg);
        }
    }

}
