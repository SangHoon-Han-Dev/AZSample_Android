package com.homey.cnstsample.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by rocomo12 on 2017-11-27.
 */

public class SystemUtil {

    public static String getMyNumber(Context context) {
        String myNumber = null;
        TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            myNumber = mgr.getLine1Number();
            myNumber = myNumber.replace("+82", "0");
            Log.d("SystemUtil", "getMyNumber myNumber= " + myNumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return myNumber;
    }

    public static String getTelecomCode(Context context) {
        Log.d("SystemUtil", "getTelecomCode");
        String telecom_code = "";

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkoper = telephonyManager.getNetworkOperatorName();
        Log.d("SystemUtil", "getTelecomCode networkoper= " + networkoper);

        return networkoper;
    }

    /**
     * 디바이스 OS 버전
     */
    public static String getOSVersion() {
        Log.d("SystemUtil", "getOSVersion");
        return Build.VERSION.RELEASE;
    }

    /**
     * 앱 버전 name
     */
    public static String getPackageVersionName(Context context) {
        Log.d("SystemUtil", "getPackageVersionName");
        String app_version_name = "";
        try {
            app_version_name = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("SystemUtil", "getPackageVersionName app_version_name= " + app_version_name);

        return app_version_name;
    }

    public static String getDeviceModelName() {
        Log.d("SystemUtil", "getDeviceModelName");
        return Build.MODEL;
    }
}
