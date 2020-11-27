package com.homey.cnstsample.util;

import android.util.Log;


/**
 * Created by 상훈 on 2016-04-04.
 * 로그 처리 관련 유틸
 */
public class LogUtil {
        public static boolean debugFlag= true;
//    public static boolean debugFlag = true;
//    public static boolean debugFlag = AZConst.MODE_TEST;

    public static void v(String tag, String message) {
        if (debugFlag)
            Log.v(tag, message);
    }

    public static void d(String tag, String message) {
        if (debugFlag)
            Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        if (debugFlag)
            Log.i(tag, message);
    }

    public static void w(String tag, String message) {
        if (debugFlag)
            Log.w(tag, message);
    }

    public static void e(String tag, String message) {
        if (debugFlag)
            Log.e(tag, message);
    }

    public static void wtf(String tag, String message) {
        if (debugFlag)
            Log.wtf(tag, message);
    }
}
