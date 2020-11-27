package com.homey.cnstsample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.homey.az.AZ;
import com.homey.cnstsample.common.Const;
import com.homey.cnstsample.util.SharedPreferenceManager;
import com.homey.cnstsample.CNSTSApplication;
import com.homey.cnstsample.R;
import com.homey.cnstsample.util.SystemUtil;

/**
 * Created by rocomo12 on 2017-11-27.
 */

public class SettingsScreenActivity extends Activity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();

    public AZ mAZ;
    CNSTSApplication mCnstsApplication;
    SharedPreferenceManager mSharedPreferenceManager;

    boolean mPushState = true;
    boolean mMarketingAgreeState = true;
    String mText1 = "";
    String mText2 = "";

    Button bt_setPushState;
    Button bt_setMarketingAgreeState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsscreen);

        mCnstsApplication = (CNSTSApplication) getApplicationContext();
        mSharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
        if (mCnstsApplication.az == null) {
//            SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
            String gcm_token = SharedPreferenceManager.getString("fcmtoken", "");
            Log.d(TAG, "gcm_token : " + gcm_token);

            // 2018/01/03 광고성 메시지 수신 동의/거절 값 추가
            boolean marketing_agree_state = true;
            marketing_agree_state = mSharedPreferenceManager.getBoolean(Const.TAG_MARKETING_AGREE_STATE, true);
            Log.d(TAG, "hsh_test4 marketing_agree_state : " + marketing_agree_state);

            if (Const.TEST_PN_ON) {
                mAZ = new AZ(getApplicationContext(), SystemUtil.getMyNumber(getApplicationContext()), gcm_token, Const.TEST_PN, marketing_agree_state);
            } else {
                mAZ = new AZ(getApplicationContext(), SystemUtil.getMyNumber(getApplicationContext()), gcm_token, getApplicationContext().getPackageName(), marketing_agree_state);
            }
            mAZ.init();//라이브러리 초기화
            mCnstsApplication.az = mAZ;
        }

        try {
            mPushState = mCnstsApplication.az.getPushState();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mMarketingAgreeState = mCnstsApplication.az.getMarketingAgreeState();
            Log.d(TAG, "hsh_test4 mMarketingAgreeState : " + mMarketingAgreeState);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bt_setPushState = (Button) findViewById(R.id.bt_setPushState);
        bt_setPushState.setOnClickListener(this);
        if (mPushState) {
            Log.d(TAG, "getPushState= true");
            bt_setPushState.setText("약관철회");
        } else {
            Log.d(TAG, "getPushState= false");
            bt_setPushState.setText("PUSH 동의");
        }

        bt_setMarketingAgreeState = (Button) findViewById(R.id.bt_setMarketingAgreeState);
        bt_setMarketingAgreeState.setOnClickListener(this);
        if (mMarketingAgreeState) {
            Log.d(TAG, "getMarketingAgreeState= true");
            bt_setMarketingAgreeState.setText("광고 수신 거절");
        } else {
            Log.d(TAG, "getMarketingAgreeState= false");
            bt_setMarketingAgreeState.setText("광고 수신 동의");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_setPushState: {
                Log.d(TAG, "onClick bt_setPushState");
                boolean result = mCnstsApplication.az.setPushState(!mPushState);//PUSH 동의/거절 전달
                if (result) {//처리 완료시

                    mPushState = mCnstsApplication.az.getPushState();
                    if (mPushState) {
                        Log.d(TAG, "getPushState= true");
                        mText1 = "약관철회";
                    } else {
                        Log.d(TAG, "getPushState= false");
                        mText1 = "PUSH 동의";
                    }
                    updatePushString();
                }
                break;
            }

            case R.id.bt_setMarketingAgreeState: {
                Log.d(TAG, "TEST_MARKETING_001 onClick bt_setMarketingAgreeState");
                boolean result = mCnstsApplication.az.setMarketingAgreeState(!mMarketingAgreeState);//광고 수신 동의/거절 전달

                updateMarketingString(!mMarketingAgreeState);

                /*if (result) {//처리 완료시

                    mMarketingAgreeState = mCnstsApplication.az.getMarketingAgreeState();
                    if (mMarketingAgreeState) {
                        Log.d(TAG, "bt_setMarketingAgreeState= true");
                        mText2 = "광고 수신 거절";
                    } else {
                        Log.d(TAG, "bt_setMarketingAgreeState= false");
                        mText2 = "광고 수신 동의";
                    }
                    updateMarketingString();
                }*/
                break;
            }
        }
    }

    void updatePushString() {
        Log.d(TAG, "updatePushString");
        bt_setPushState.setText(mText1);
    }

    void updateMarketingString(boolean pushState) {
        Log.d(TAG, "TEST_MARKETING_001 updateMarketingString pushState: " + pushState);
        mSharedPreferenceManager.putBoolean(Const.TAG_MARKETING_AGREE_STATE, pushState);

        if (pushState) {
            Log.d(TAG, "TEST_MARKETING_001 bt_setMarketingAgreeState= true");
            mText2 = "광고 수신 거절";
        } else {
            Log.d(TAG, "TEST_MARKETING_001 bt_setMarketingAgreeState= false");
            mText2 = "광고 수신 동의";
        }

        bt_setMarketingAgreeState.setText(mText2);
    }
}
