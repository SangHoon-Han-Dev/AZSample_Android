package com.homey.cnstsample.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.view.ActionMode;
//import android.support.v7.widget.PopupMenu;
//import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.analytics.HitBuilders;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.homey.az.AZ;
import com.homey.az.common.AZConst;
//import com.homey.az.util.ImageUtil;

// TODO: 2020-09-10 전버전 테스트를 위한 주석처리
import com.homey.az.network.vo.RecentMSGVO;
import com.homey.cnstsample.util.LogUtil;
import com.homey.cnstsample.util.SharedPreferenceManager;
import com.homey.cnstsample.CNSTSApplication;
import com.homey.cnstsample.R;
import com.homey.cnstsample.common.Const;
import com.homey.cnstsample.fcm.PushWakeLock;
import com.homey.cnstsample.util.SystemUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.homey.cnstsample.CNSTSApplication.az;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public String TAG = this.getClass().getSimpleName();

    public Context mContext = null;

    public AZ mAZ;
    CNSTSApplication mCnstsApplication;
    SharedPreferenceManager mSharedPreferenceManager;

    public boolean mPushState = false;
    String mText2 = "";

    //    public Button bt_open_category;
    public Button bt_initAZ;
    public Button bt_startAZ;
    public LinearLayout ll_guide_area;
    public Button bt_guide_open;
    public Button bt_guide_close;
    public TextView tv_unread_count;
    public TextView tv_recentmsg_count;

    Button bt_setMarketingAgreeState;

    /*public Button bt_getNotiCount;
    public Button bt_start_service;
    public Button bt_stop_service;*/

    public ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "[흐름 테스트] onCreate");

        mContext = getApplicationContext();
        mSharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
        mCnstsApplication = (CNSTSApplication) getApplication();

        mAZ = az;

        // TODO: 2020-07-30 알림장에 다운로드된 이미지 가져오기 테스트
        //company_image
        /*try {
            Log.d(TAG, "[흐름 테스트] onCreate 알림장에 다운로드된 이미지 가져오기 테스트 1");
//            String company_image = StringUtil.subStringFileName(clientComVo.getClientComImage());
            String company_image = "homey.png.png";
            com.homey.az.util.LogUtil.d(TAG, "company_image= " + company_image);

//            Bitmap bitmap = ImageUtil.SafeDecodeBitmapFile(getFilesDir() + com.homey.az.common.Const.DOWNLOAD_FOLDER_IMAGE + "/" + company_image, 250);
//            Bitmap bitmap = ImageUtil.SafeDecodeBitmapFile("/data/user/0/com.homey.cnstsample/files/az/image/homey.png.png", 250);
            Bitmap bitmap = ImageUtil.SafeDecodeBitmapFile("/data/user/0/com.homey.cnstsample/files/az/image/ahnlab.png.png", 250);

            ImageView iv_company_image = findViewById(R.id.iv_company_image);
            iv_company_image.setImageBitmap(bitmap);
            bitmap = null;
            Log.d(TAG, "[흐름 테스트] onCreate 알림장에 다운로드된 이미지 가져오기 테스트 2");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // TODO: 2020-07-30 GAID 가져오기 테스트
        /*GoogleAppIdTask.CallBack callBack = new GoogleAppIdTask.CallBack() {
            @Override
            public void onSuccess(String gaid) {
                Log.d(TAG, "[TEST_GAID] onCreate GoogleAppIdTask.CallBack onSuccess gaid: " + gaid);
            }

            @Override
            public void onFail(String str) {
                Log.d(TAG, "[TEST_GAID] onCreate GoogleAppIdTask.CallBack onFail str: " + str);
            }
        };
        GoogleAppIdTask googleAppIdTask = new GoogleAppIdTask(mContext, callBack);
        googleAppIdTask.executeSync();*/

//        sv_searchview.setSubmitButtonEnabled(true);

        final TextView tvLinkify = findViewById(R.id.tvLinkify);
//        registerForContextMenu(tvLinkify);
//        tvLinkify.setLongClickable(false);
        final Context context = this;
        tvLinkify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick tvLinkify");
                PopupMenu popup = new PopupMenu(context, v);//v는 클릭된 뷰를 의미
//                PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미
                Log.d(TAG, "onClick tvLinkify1");
                getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());
                Log.d(TAG, "onClick tvLinkify2");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.d(TAG, "onMenuItemClick tvLinkify");
                        switch (item.getItemId()) {
                            case R.id.action_search:
                                Toast.makeText(getApplication(), "메뉴1", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                Log.d(TAG, "onClick tvLinkify3");
                popup.show();//Popup Menu 보이기
                Log.d(TAG, "onClick tvLinkify4");
            }
        });

       /* tvLinkify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionMode != null) {
                    return;
                }

                // Start the CAB using the ActionMode.Callback defined above
//                actionMode = startActionMode(actionModeCallback);
                actionMode = startSupportActionMode(actionModeCallback);
//                tvLinkify.performLongClick();
//                openContextMenu(tvLinkify);
            }
        });*/


        PushWakeLock.releaseCpuLock();//WakeLock 해제

//        // GCM 수신 후 Notification 선택 시 Timeline으로 이동
//        // 2019-10-01 바로가기, 고객사로 이동
//        Intent get_intent = getIntent();
//        String intent_extra_company_id = get_intent.getStringExtra(AZConst.TAG_COMPANY_ID);//COMPANY_ID값이 있으면 알림장 실행
//        Log.d(TAG, "onCreate intent_extra_company_id= " + intent_extra_company_id);
//        if (intent_extra_company_id != null && !TextUtils.isEmpty(intent_extra_company_id)) {
//            Log.d(TAG, "onCreate TAG_COMPANY_ID");
//            initAZ(intent_extra_company_id);
//        }

        intentState(getIntent());

//        checkPermission();
        checkPermission2();

        //fcm token 확인
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "token_test getInstanceId failed", task.getException());
                            return;
                        }

                        String token = task.getResult().getToken();
                        Log.d(TAG, "token_test= " + token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();

                        com.homey.cnstsample.util.SharedPreferenceManager sharedPreferenceManager = new com.homey.cnstsample.util.SharedPreferenceManager(mContext);
                        sharedPreferenceManager.putString("fcmtoken", token);
                    }
                });

        bt_initAZ = findViewById(R.id.bt_initAZ);
        bt_initAZ.setOnClickListener(this);
        bt_startAZ = findViewById(R.id.bt_startAZ);
        bt_startAZ.setOnClickListener(this);
        ll_guide_area = findViewById(R.id.ll_guide_area);
        bt_guide_open = findViewById(R.id.bt_guide_open);
        bt_guide_open.setOnClickListener(this);
        bt_guide_close = findViewById(R.id.bt_guide_close);
        bt_guide_close.setOnClickListener(this);

        bt_setMarketingAgreeState = (Button) findViewById(R.id.bt_setMarketingAgreeState);
        bt_setMarketingAgreeState.setOnClickListener(this);

        // 2018/01/03 광고성 메시지 수신 동의/거절 값 추가
        boolean marketingAgreeState = mSharedPreferenceManager.getBoolean(Const.TAG_MARKETING_AGREE_STATE, true);
        Log.d(TAG, "onCreate marketing_agree_state : " + marketingAgreeState);
        if (marketingAgreeState) {
            Log.d(TAG, "onCreate getMarketingAgreeState: true");
            bt_setMarketingAgreeState.setText("광고수신 거절하기");
        } else {
            Log.d(TAG, "onCreate getMarketingAgreeState: false");
            bt_setMarketingAgreeState.setText("광고수신 동의하기");
        }

        Log.d(TAG, "[흐름 테스트] onCreate 1");
        tv_unread_count = findViewById(R.id.tv_unread_count);
        tv_recentmsg_count = findViewById(R.id.tv_recentmsg_count);
        Log.d(TAG, "[흐름 테스트] onCreate 2");
        /*String text = "010988728829 https://www.naver.com/ [[azsample1234://az123?type=1&seq=1]]";
        TextView tvLinkify = findViewById(R.id.tvLinkify);
        tvLinkify.setText(text);

        deeplinkCheck(tvLinkify);

        String text2 = "01087728829 https://www.naver.com/";
        TextView tvLinkify2 = findViewById(R.id.tvLinkify2);
        tvLinkify2.setText(text2);

        deeplinkCheck(tvLinkify2);

        TextView tvSpan = (TextView) findViewById(R.id.tvSpan);

        Spannable span = (Spannable) tvSpan.getText();
        tvSpan.setMovementMethod(LinkMovementMethod.getInstance());
        span.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                Toast.makeText(mContext, "라크라꾸~~", Toast.LENGTH_SHORT).show();
            }
        }, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/


        String text = "1 알림장 서비스는 정보통신서비스제공자가 준수하여야 할 관련 법령상(정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법, 통신비밀보호법, 전기통신사업법 등)의 개인정보보호 규정을 준수하며, 관련 법령에 의거한 개인정보취급방침을 정하여 이용자 권익 보호를 위해 사전 동의를 얻어 서비스를 제공하고 있습니다." +
                "2 알림장 서비스는 정보통신서비스제공자가 준수하여야 할 관련 법령상(정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법, 통신비밀보호법, 전기통신사업법 등)의 개인정보보호 규정을 준수하며, 관련 법령에 의거한 개인정보취급방침을 정하여 이용자 권익 보호를 위해 사전 동의를 얻어 서비스를 제공하고 있습니다." +
                "3 알림장 서비스는 정보통신서비스제공자가 준수하여야 할 관련 법령상(정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법, 통신비밀보호법, 전기통신사업법 등)의 개인정보보호 규정을 준수하며, 관련 법령에 의거한 개인정보취급방침을 정하여 이용자 권익 보호를 위해 사전 동의를 얻어 서비스를 제공하고 있습니다." +
                "4 알림장 서비스는 정보통신서비스제공자가 준수하여야 할 관련 법령상(정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법, 통신비밀보호법, 전기통신사업법 등)의 개인정보보호 규정을 준수하며, 관련 법령에 의거한 개인정보취급방침을 정하여 이용자 권익 보호를 위해 사전 동의를 얻어 서비스를 제공하고 있습니다." +
                "5  알림장 서비스는 정보통신서비스제공자가 준수하여야 할 관련 법령상(정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법, 통신비밀보호법, 전기통신사업법 등)의 개인정보보호 규정을 준수하며, 관련 법령에 의거한 개인정보취급방침을 정하여 이용자 권익 보호를 위해 사전 동의를 얻어 서비스를 제공하고 있습니다." +
                "6  알림장 서비스는 정보통신서비스제공자가 준수하여야 할 관련 법령상(정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법, 통신비밀보호법, 전기통신사업법 등)의 개인정보보호 규정을 준수하며, 관련 법령에 의거한 개인정보취급방침을 정하여 이용자 권익 보호를 위해 사전 동의를 얻어 서비스를 제공하고 있습니다." +
                "7  알림장 서비스는 정보통신서비스제공자가 준수하여야 할 관련 법령상(정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법, 통신비밀보호법, 전기통신사업법 등)의 개인정보보호 규정을 준수하며, 관련 법령에 의거한 개인정보취급방침을 정하여 이용자 권익 보호를 위해 사전 동의를 얻어 서비스를 제공하고 있습니다." +
                "8  알림장 서비스는 정보통신서비스제공자가 준수하여야 할 관련 법령상(정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법, 통신비밀보호법, 전기통신사업법 등)의 개인정보보호 규정을 준수하며, 관련 법령에 의거한 개인정보취급방침을 정하여 이용자 권익 보호를 위해 사전 동의를 얻어 서비스를 제공하고 있습니다." +
                "9 알림장 서비스는 정보통신서비스제공자가 준수하여야 할 관련 법령상(정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법, 통신비밀보호법, 전기통신사업법 등)의 개인정보보호 규정을 준수하며, 관련 법령에 의거한 개인정보취급방침을 정하여 이용자 권익 보호를 위해 사전 동의를 얻어 서비스를 제공하고 있습니다.";
        setText(text);
    }

    /*private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_faq:
//                    shareCurrentItem();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtil.d(TAG, "onCreateOptionsMenu main_menu");
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.menu_faq: {
                Toast.makeText(mContext, "menu_faq", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.menu_1: {
                Toast.makeText(mContext, "menu_1", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.menu_faq: {
                Toast.makeText(mContext, "menu_faq", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.menu_1: {
                Toast.makeText(mContext, "menu_1", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }*/

    // TODO: 2020-06-25 FAQ, 약관 내용 받기
    public void setText(String text) {
        LogUtil.d(TAG, "setText text: " + text);

        mSharedPreferenceManager.putString("faq_text", text);

        String faq_text = mSharedPreferenceManager.getString("faq_text");
        LogUtil.d(TAG, "setText faq_text: " + faq_text);
    }

    /**
     * TextView의 Text가 딥링크를 포함하는지 확인하여 딥링크 문자열에 링크를 건다.
     *
     * @param textView
     */
    private void deeplinkCheck(TextView textView) {

        String text = textView.getText().toString();
        Log.d(TAG, "deeplinkCheck text: " + text);

        String resultText = text;

        Linkify.addLinks(textView, Linkify.ALL);

        if (text.contains("azsample1234://")) {
//            System.out.println( indexOfTestOne.lastIndexOf("x") );  // -1
//
//            System.out.println( indexOfTestOne.lastIndexOf("o",5) );  // 4
            int startIndex = text.indexOf("azsample1234://");
            int endIndex = startIndex + 33;
            resultText = text.substring(startIndex, endIndex);

            Log.d(TAG, "startIndex: " + startIndex);
            Log.d(TAG, "endIndex: " + endIndex);
            Log.d(TAG, "resultText: " + resultText);

            resultText = resultText.replace("?", "\\?");
            Log.d(TAG, "resultText: " + resultText);

            Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return "";
                }
            };
            Pattern pattern = Pattern.compile(resultText);
            Linkify.addLinks(textView, pattern, resultText, null, mTransform);
        } else {
            Linkify.addLinks(textView, Linkify.ALL);
        }
    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        001);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 001: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", "onRequestPermissionsResult 001 동의");
                } else {
                    Log.d("MainActivity", "onRequestPermissionsResult 001 거절");
                }
                return;
            }
        }
    }


    /**
     * 권한 체크
     */

    private void checkPermission2() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                // Toast.makeText(getApplicationContext(), "동의되있음", Toast.LENGTH_SHORT).show();
                LogUtil.d(TAG, "onRequestP/ermissionsResult 001 재요청");
            } else {
                // No explanation needed, we can request the permission.
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an

                // app-defined int constant. The callback method gets the

                // result of the request.
            }
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_NUMBERS},
                    001);
        } else {
            //모두 동의되어있을때
//            startMain();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "[흐름 테스트] onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "[흐름 테스트] onDestroy");

        if (mAZ != null && mAZ.onInit()) {
            mAZ.release();
            mAZ = null;
        }
    }

    /**
     * AZ 라이브러리 init
     */
    public void initAZ(String company_id, String deeplink_seq) {
        Log.d(TAG, "[흐름 테스트] initAZ company_id: " + company_id + ", deeplink_seq:" + deeplink_seq);
//        mCnstsApplication = (CNSTSApplication) mContext;

        String gcm_token = mSharedPreferenceManager.getString("fcmtoken", "");
        boolean marketingAgreeState = mSharedPreferenceManager.getBoolean(Const.TAG_MARKETING_AGREE_STATE, true);
        Log.d(TAG, "initAZ marketing_agree_state : " + marketingAgreeState);
        Log.d(TAG, "initAZ company_id : " + company_id);
        Log.d(TAG, "initAZ gcm_token : " + gcm_token);

        // 2018/01/03 광고성 메시지 수신 동의/거절 값 추가
//        boolean marketing_agree_state = true;
//        marketing_agree_state = mSharedPreferenceManager.getBoolean(Const.TAG_MARKETING_AGREE_STATE, true);
//        Log.d(TAG, "initAZ marketing_agree_state : " + marketing_agree_state);

        // 2018/01/16 추가
        // 디바이스 모델명 가져오기
        String device_model_name = SystemUtil.getDeviceModelName();
        // 통신사명 가져오기
        String telecom_code = SystemUtil.getTelecomCode(mContext);
        // os버전 가져오기
        String os_version = SystemUtil.getOSVersion();
        // 앱 버전 가져오기
        String app_version_name = SystemUtil.getPackageVersionName(mContext);

        String number = SystemUtil.getMyNumber(getApplicationContext());
        if (number == null || TextUtils.isEmpty(number)) {

            // TODO: 2020-09-25 전화번로를 가져올 수 없을때 return 처리
//            number = Const.TEST_NUMBER_;
            startToast("전화번호를 가져올 수 없습니다.");
            Log.d(TAG, "initAZ 전화번호를 가져올 수 없습니다.");
            return;
        }

        if (Const.TEST_PN_ON) {
            mAZ = new AZ(getApplicationContext(), number, gcm_token, Const.TEST_PN, marketingAgreeState, device_model_name, telecom_code, os_version, app_version_name);
        } else {
            mAZ = new AZ(getApplicationContext(), number, gcm_token, getApplicationContext().getPackageName(), marketingAgreeState, device_model_name, telecom_code, os_version, app_version_name);
        }

        boolean result_init = mAZ.init();//라이브러리 초기화

        if (result_init) {
            Log.d(TAG, "Init 성공");
            startToast("알림장 Init 성공");

            boolean pushAgreeState = mSharedPreferenceManager.getBoolean(Const.TAG_PUSH_AGREE_STATE, false);

            Log.d(TAG, "initAZ 기존 약관동의 상태 pushAgreeState: " + pushAgreeState);

            if (!pushAgreeState) {
                pushAgreeState = mAZ.setPushState(true); // 약관동의
                Log.d(TAG, "initAZ 약관동의 시도 후 약관동의 상태 pushAgreeState: " + pushAgreeState);
                mSharedPreferenceManager.putBoolean(Const.TAG_PUSH_AGREE_STATE, true);
            } else {
                mSharedPreferenceManager.putBoolean(Const.TAG_PUSH_AGREE_STATE, false);
            }

            // 약관동의 성공 후
            if (pushAgreeState) {
                Log.d(TAG, "initAZ 약관동의됨");

                // 읽지않은 메시지 가져와 설정
                String unReadMSGCount = String.valueOf(mAZ.getNotiCount());
                Log.d(TAG, "initAZ unReadMSGCount: " + unReadMSGCount);
                Log.d(TAG, "[흐름 테스트] onCreate 3");
                tv_unread_count.setText(unReadMSGCount);
                Log.d(TAG, "[흐름 테스트] onCreate 4");
                // 최근 수신 메시지 가져와 설정
                setRecentMSG();
                Log.d(TAG, "[흐름 테스트] onCreate 5");
                // TODO: 2020-07-07 init 되어있을때 처리
                if (company_id != null && !company_id.isEmpty() || deeplink_seq != null && !deeplink_seq.isEmpty()) {
                    Log.d(TAG, "[흐름 테스트] initAZ init 완료 후 알림장 바로 실행");
                    startMSGActivity(company_id, deeplink_seq);//알림장 화면으로 이동
                }
            }
        } else {
            Log.d(TAG, "initAZ 알림장 Init 실패");
            startToast("알림장 Init 실패");
        }

        az = mAZ;
    }


    // TODO: 2020-09-10 전버전 테스트를 위한 주석처리

    /**
     * 최근 수신한 메시지 설정
     */
    public void setRecentMSG() {
        Log.d(TAG, "setRecentMSG");
        LinearLayout ll_recentmsg_area = findViewById(R.id.ll_recentmsg_area); // 메시지 row 추가할 View
        ll_recentmsg_area.removeAllViews();

        final ArrayList<RecentMSGVO> recentMSGVOList = mAZ.getRecentMSG(2);
        int recentMSGCount = recentMSGVOList.size();

        Log.d(TAG, "setRecentMSG recentMSGCount: " + recentMSGCount);

        tv_recentmsg_count.setText(String.valueOf(recentMSGCount));

        // 최근 수신한 메시지가 있을때
        if (recentMSGCount > 0) {

            // 메시지 갯수만큼 row 추가
            for (int i = 0; i < recentMSGCount; i++) {

                final String msgID = recentMSGVOList.get(i).getMsgID(); // 메시지 ID
                final String companyID = recentMSGVOList.get(i).getCompanyID(); // 메시지 고객사 ID
                String msgText = recentMSGVOList.get(i).getMsgText(); // 메시지 내용
                String msgTime = recentMSGVOList.get(i).getMsgTime(); // 메시지 수신 시간
                String contentsType = recentMSGVOList.get(i).getContentsType(); // 메시지 광고성 구분
                String companyName = recentMSGVOList.get(i).getCompanyName(); // 고객사명
                Bitmap companyLogo = recentMSGVOList.get(i).getCompanyLogo(); // 고객사 로고 이미지

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View convertView = layoutInflater.inflate(R.layout.row_companylist, ll_recentmsg_area, false);

                RelativeLayout rl_companylist_area = convertView.findViewById(R.id.rl_companylist_area); // row 영역 클릭 시 해당 메시지 화면으로 이동
                TextView tv_companylist_company_name = convertView.findViewById(R.id.tv_companylist_company_name); // 고객사명
                ImageView iv_company_image = convertView.findViewById(R.id.iv_company_image); // 고객사 로고
                TextView tv_companylist_content = convertView.findViewById(R.id.tv_companylist_content); // 메시지 내용
                TextView tv_companylist_time = convertView.findViewById(R.id.tv_companylist_time); // 메시지 수신 시간

                tv_companylist_company_name.setText(companyName);
                iv_company_image.setImageBitmap(companyLogo);
                tv_companylist_content.setText(msgText);
                tv_companylist_time.setText(msgTime);

                // 메시지 row 클릭 시 해당 메시지 화면으로 이동
                rl_companylist_area.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.d(TAG, "onClick rl_companylist_area");
                        Intent intent = new Intent(mContext, MSGActivity.class);
                        intent.putExtra(AZConst.TAG_COMPANY_ID, companyID); // 이동할 메시지 화면의 고객사ID
                        intent.putExtra(AZConst.TAG_DEEPLINK_SEQ, "");
                        startActivity(intent);
                    }
                });

                ll_recentmsg_area.addView(convertView);
            }
        }
    }

    /**
     * 알림장 Timeline 화면으로 이동
     */
    public void startMSGActivity(String company_id, String deeplink_seq) {
        Log.d(TAG, "[흐름 테스트] startMSGActivity company_id: " + company_id + ", deeplink_seq: " + deeplink_seq);

        // TODO: 2019-10-01 바로가기, 고객사로 이동
        Intent intent = new Intent(mContext, MSGActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(AZConst.TAG_COMPANY_ID, company_id);
        intent.putExtra(AZConst.TAG_DEEPLINK_SEQ, deeplink_seq);
        startActivity(intent);
    }

    /**
     * 알림장 Timeline 화면으로 이동
     */
    public void startMSGActivity(String company_id) {
        Log.d(TAG, "startMSGActivity company_id= " + company_id);

        // TODO: 2019-10-01 바로가기, 고객사로 이동
        Intent intent = new Intent(mContext, MSGActivity.class);
        intent.putExtra(AZConst.TAG_COMPANY_ID, company_id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "[흐름 테스트] onNewIntent");
        intentState(intent);
    }

    /**
     * Intent를 확인하여 처리한다.
     *
     * @param intent
     */
    public void intentState(Intent intent) {
        Log.d(TAG, "[흐름 테스트] intentState intent.getAction()= " + intent.getAction());

        String intent_extra_deeplink_seq = "";
        String intent_extra_company_id = "";
        // TODO: 2020-02-12  딥링크 테스트
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            intent_extra_deeplink_seq = uri.getQueryParameter("seq");
            Log.d(TAG, "[흐름 테스트] intentState intent_extra_deeplink_seq:" + intent_extra_deeplink_seq);
//            initAZ(intent_extra_company_id, intent_extra_deeplink_seq);

            // TODO: 2020-07-08 init 되어있을때 처리
            if (mAZ != null && mAZ.onInit()) {
                Log.d(TAG, "[흐름 테스트] intentState init 되어있어 알림장 바로 실행");
                startMSGActivity(intent_extra_company_id, intent_extra_deeplink_seq);//알림장 화면으로 이동
            } else {
                initAZ(intent_extra_company_id, intent_extra_deeplink_seq);
            }
        } else {
            //GCM 수신 후 Notification 선택 시 Timeline으로 이동
            // TODO: 2019-10-01 바로가기, 고객사로 이동
            intent_extra_company_id = intent.getStringExtra(AZConst.TAG_COMPANY_ID);//COMPANY_ID값이 있으면 알림장 실행
            Log.d(TAG, "[흐름 테스트] intentState intent_extra_company_id= " + intent_extra_company_id);
            if (intent_extra_company_id != null && !TextUtils.isEmpty(intent_extra_company_id)) {
                Log.d(TAG, "[흐름 테스트] intentState TAG_COMPANY_ID");
//                initAZ(intent_extra_company_id, intent_extra_deeplink_seq);

                // TODO: 2020-07-08 init 되어있을때 처리
                if (mAZ != null && mAZ.onInit()) {
                    Log.d(TAG, "[흐름 테스트] intentState init 되어있어 알림장 바로 실행");
                    startMSGActivity(intent_extra_company_id, intent_extra_deeplink_seq);//알림장 화면으로 이동
                } else {
                    initAZ(intent_extra_company_id, intent_extra_deeplink_seq);
                }
            }
        }
    }

    void updateMarketingString(boolean pushState) {
        Log.d(TAG, "TEST_MARKETING_001 updateMarketingString pushState: " + pushState);
        mSharedPreferenceManager.putBoolean(Const.TAG_MARKETING_AGREE_STATE, pushState);

        if (pushState) {
            Log.d(TAG, "TEST_MARKETING_001 bt_setMarketingAgreeState: true");
            mText2 = "광고수신 거절하기";
        } else {
            Log.d(TAG, "TEST_MARKETING_001 bt_setMarketingAgreeState: false");
            mText2 = "광고수신 동의하기";
        }

        bt_setMarketingAgreeState.setText(mText2);

        startToast("광고수신 상태 변경 완료");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch (v.getId()) {
            case R.id.bt_initAZ: {
                Log.d(TAG, "onClick bt_initAZ");
                //AZ 라이브러리 init
//                initAZ("");
                initAZ("", "");
                break;
            }
            case R.id.bt_startAZ: {
                Log.d(TAG, "onClick bt_startAZ");
                //AZ 라이브러리 start
                if (mAZ != null && mAZ.onInit()) {
                    startMSGActivity("", "");//알림장 화면으로 이동
                } else {
                    startToast("알림장 init을 해주세요.");
                }

                break;
            }
            case R.id.bt_guide_open: {
                Log.d(TAG, "onClick bt_guide_open");

                ll_guide_area.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.bt_guide_close: {
                Log.d(TAG, "onClick bt_guide_close");

                ll_guide_area.setVisibility(View.GONE);
                break;
            }
            case R.id.bt_setMarketingAgreeState: {
                Log.d(TAG, "onClick bt_setMarketingAgreeState");
                Log.d(TAG, "TEST_MARKETING_001 onClick bt_setMarketingAgreeState");

                if (mAZ == null || !mAZ.onInit()) {
                    startToast("알림장 init을 해주세요.");
                    return;
                }

                // 결과에 상관없이 성공한것으로 처리하기로 했다. 실패하더라도 알림장을 다시 init 할때 동기화 시도하기 때문
                boolean marketingAgreeState = mSharedPreferenceManager.getBoolean(Const.TAG_MARKETING_AGREE_STATE, true);
                Log.d(TAG, "TEST_MARKETING_001 onClick bt_setMarketingAgreeState marketing_agree_state : " + marketingAgreeState);
                boolean result = mCnstsApplication.az.setMarketingAgreeState(!marketingAgreeState);//광고 수신 동의/거절 전달
                updateMarketingString(!marketingAgreeState);

                /*if (result) {//처리 완료시

                    marketingAgreeState = mCnstsApplication.az.getMarketingAgreeState();
                    if (marketingAgreeState) {
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
            default: {

            }
        }
    }

    /**
     * Toast 띄우기
     *
     * @param msg
     */
    private void startToast(final String msg) {
        Log.d(TAG, "startToast msg= " + msg);

        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
