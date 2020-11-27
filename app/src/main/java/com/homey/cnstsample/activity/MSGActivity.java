package com.homey.cnstsample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.homey.az.AZ;
import com.homey.az.activity.AZMSGActivity;
import com.homey.cnstsample.CNSTSApplication;
import com.homey.cnstsample.common.Const;
import com.homey.cnstsample.util.SharedPreferenceManager;

/**
 * Created by rocomo12 on 2017-11-20.
 */

public class MSGActivity extends AZMSGActivity {
    //public class MSGActivity extends AZMSGActivity2 {
    public String TAG = this.getClass().getSimpleName();

    Context mContext;
    AZ mAZ;
    CNSTSApplication mCnstsApplication;

    public MSGActivity() {
        super();
        //V3MP 추가작업
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "cnstsample onCreate");

        mContext = this;
        mAZ = mCnstsApplication.az;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "cnstsample onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    // TODO: 2020-09-10 전버전 테스트를 위한 주석처리
/*
    @Override
    public void showSettingsScreen() {
        super.showSettingsScreen();
    }*/

    @Override
    public void showSettingsScreen(int menuNum) {
        super.showSettingsScreen(menuNum);
        Log.d(TAG, "showSettingsScreen menuNum: " + menuNum);

        switch (menuNum) {
            case 1:
                // 개인정보 수집 및 이용 약관 작업
                Log.d(TAG, "showSettingsScreen 개인정보 수집 및 이용 약관 작업");
                //V3MP 세팅화면 실행
//                Intent intent = new Intent(getApplicationContext(), SettingsScreenActivity.class);
//                startActivity(intent);

                Toast.makeText(mContext, "개인정보 수집 및 이용 약관 작업", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                // 개인정보 제3자 제공 약관 작업
                Log.d(TAG, "showSettingsScreen 개인정보 제3자 제공 약관 작업");
                Toast.makeText(mContext, "개인정보 제3자 제공 약관 작업", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                // 개인정보 처리 방침 작업
                Log.d(TAG, "showSettingsScreen 개인정보 처리 방침 작업");
                Toast.makeText(mContext, "개인정보 처리 방침 작업", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                // 알림장 사용 동의 철회 작업
                Log.d(TAG, "showSettingsScreen 알림장 사용 동의 철회 작업");

                boolean result = mAZ.setPushState(false);//PUSH 동의/거절 전달

                Log.d(TAG, "showSettingsScreen 알림장 사용 동의 철회 작업완료");

                if (result) {//처리 완료시
                    Log.d(TAG, "showSettingsScreen 알림장 사용 동의 철회 작업 성공");
                    SharedPreferenceManager mSharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
                    mSharedPreferenceManager.putBoolean(Const.TAG_PUSH_AGREE_STATE, false);
                    Toast.makeText(mContext, "알림장 사용 동의 철회 성공", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Log.d(TAG, "showSettingsScreen 알림장 사용 동의 철회 작업 실패");
                    Toast.makeText(mContext, "알림장 사용 동의 철회 실패", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
