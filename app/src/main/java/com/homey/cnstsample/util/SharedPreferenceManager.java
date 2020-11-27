package com.homey.cnstsample.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.homey.cnstsample.util.NumberUtil;

/**
 * Created by 상훈 on 2016-04-04.
 */
public class SharedPreferenceManager {
    private static Context mContext = null;

    public SharedPreferenceManager(Context context) {
        mContext = context;
    }

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
//        return mContext.getSharedPreferences("",Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        boolean value = defValue;

        try {
            value = getSharedPreferences().getBoolean(key, defValue);
        } catch (ClassCastException e) {
        }

        return value;
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defValue) {
        String value = defValue;

        try {
            value = getSharedPreferences().getString(key, defValue);
        } catch (ClassCastException e) {
        }

        return value;
    }

    public static int getInt(String key, int defValue) {
        int value = defValue;

        try {
            value = getSharedPreferences().getInt(key, defValue);
        } catch (ClassCastException e) {
        }

        return value;
    }

    public static void putString(String key, String value) {
//        new PrefStringWriteTask().execute(key, value);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putInt(String key, int value) {
//        Log.d("SharedPre", "key=" + key + ", value=" + value);
//        new PrefIntegerWriteTask().execute(key, String.valueOf(value));
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putBoolean(String key, boolean value) {
//        new PrefBooleanWriteTask().execute(key, String.valueOf(value));
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putStringWithoutThread(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    // --------------------------------------------------------------
    //
    // AsyncTask
    //
    // --------------------------------------------------------------
    private static class PrefStringWriteTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putString(params[0], params[1]);
            editor.commit();

            return null;
        }
    }

    private static class PrefIntegerWriteTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putInt(params[0], NumberUtil.toInt(params[1]));
            editor.commit();

            return null;
        }
    }

    private static class PrefBooleanWriteTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();

            boolean value = Boolean.valueOf(params[1]);
            editor.putBoolean(params[0], value);
            editor.commit();

            return null;
        }
    }
}
