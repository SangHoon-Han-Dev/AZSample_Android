package com.homey.cnstsample.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

/**
 * GAID 가져오기
 */
public class GoogleAppIdTask extends AsyncTask<Void, Void, String> {
    private String TAG = this.getClass().getSimpleName();

    protected Context mContext = null;
    protected CallBack mCallBack = null;

    public interface CallBack {
        public void onSuccess(String gaid);    // 성공

        public void onFail(String str);        // 실패
//    public void onError(String error_code);       // Exception
    }

    public GoogleAppIdTask(Context context, CallBack callBack) {
        LogUtil.d(TAG, "[TEST_GAID] GoogleAppIdTask");
        mContext = context;
        mCallBack = callBack;
    }

    protected String doInBackground(final Void... params) {
        LogUtil.d(TAG, "[TEST_GAID] doInBackground");
        String adId = null;

        try {
            adId = AdvertisingIdClient.getAdvertisingIdInfo(mContext).getId();
            LogUtil.d(TAG, "[TEST_GAID] doInBackground adid: " + adId);
        } catch (IllegalStateException ex) {
            LogUtil.d(TAG, "[TEST_GAID] IllegalStateException" + ex);
        } catch (GooglePlayServicesRepairableException ex) {
            LogUtil.d(TAG, "[TEST_GAID] GooglePlayServicesRepairableException" + ex);
        } catch (IOException ex) {
            LogUtil.d(TAG, "[TEST_GAID] IOException" + ex);
        } catch (GooglePlayServicesNotAvailableException ex) {
            LogUtil.d(TAG, "[TEST_GAID] GooglePlayServicesNotAvailableException" + ex);
        } catch (Exception ex) {
            LogUtil.d(TAG, "[TEST_GAID] Exception" + ex);
        }

        return adId;
    }

    protected void onPostExecute(String adId) {
        LogUtil.d(TAG, "[TEST_GAID] onPostExecute adId: " + adId);
        // 수행할 작업
        mCallBack.onSuccess(adId);
    }

    public void executeSync() {
        LogUtil.d(TAG, "[TEST_GAID] executeSync");
        // execute
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            this.execute();
        }
    }
}