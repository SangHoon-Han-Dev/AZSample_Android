package com.homey.cnstsample.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.homey.az.AZ;
import com.homey.az.AZNotiInfoVO;
import com.homey.az.common.AZConst;
import com.homey.cnstsample.R;
import com.homey.cnstsample.activity.MainActivity;
import com.homey.cnstsample.common.Const;
import com.homey.cnstsample.util.SharedPreferenceManager;
import com.homey.cnstsample.util.SystemUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by rocomo12 on 2018-03-27.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    // --------------------------------------------------------------
    //
    // Define
    //
    // --------------------------------------------------------------
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    SharedPreferenceManager mSharedPreferenceManager;
    public AZ mAZ;
    private NotificationManager mNotificationManager;
    private NotificationHelper noti;
    private static final int NOTI_SECONDARY1 = 1200;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        mContext = getApplicationContext();
        mSharedPreferenceManager = new SharedPreferenceManager(mContext);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
//        releaseCpuWakeLock();
    }

    /**
     * 새로운 token 발급 시 호출됨
     *
     * @param s
     */
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken token= " + s);

        com.homey.cnstsample.util.SharedPreferenceManager sharedPreferenceManager = new com.homey.cnstsample.util.SharedPreferenceManager(mContext);
        sharedPreferenceManager.putString("fcmtoken", s);
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived remoteMessage: " + remoteMessage);
        Log.d(TAG, "onMessageReceived getFrom: " + remoteMessage.getFrom());
        Log.d(TAG, "onMessageReceived getData: " + remoteMessage.getData());

////////////////////////////////////////////////////
        Map<String, String> map = remoteMessage.getData();
        String iMessageSendSeqno = remoteMessage.getData().get("iMessageSendSeqno");
        String phoneMainCategory = remoteMessage.getData().get("phoneMainCategory");
        String messageType = remoteMessage.getData().get("messageType");
        String clientComSeqno = remoteMessage.getData().get("clientComSeqno");
        String ctx = remoteMessage.getData().get("ctx");
        String smartPhoneMessage = remoteMessage.getData().get("smartPhoneMessage");
        String marketPush = remoteMessage.getData().get("marketPush");
        String phoneSubCategory = remoteMessage.getData().get("phoneSubCategory");
        String callbackNo = remoteMessage.getData().get("callbackNo");

        Intent intent = new Intent();
        intent.putExtra("iMessageSendSeqno", iMessageSendSeqno);
        intent.putExtra("phoneMainCategory", phoneMainCategory);
        intent.putExtra("messageType", messageType);
        intent.putExtra("clientComSeqno", clientComSeqno);//고객사코드
        intent.putExtra("ctx", ctx);
        intent.putExtra("smartPhoneMessage", smartPhoneMessage);
        intent.putExtra("marketPush", marketPush);
        intent.putExtra("phoneSubCategory", phoneSubCategory);
        intent.putExtra("callbackNo", callbackNo);

        Bundle extras = intent.getExtras();
        String onNoti = "N";

        if (!extras.isEmpty()) { // has effect of unparcelling Bundle
            // TODO: 2018-03-28 광고성값 확인
//                String marketPush = extras.getString("marketPush");
            Log.d(TAG, "marketPush : " + marketPush);
            if (marketPush != null && marketPush.equals("Y")) {
                Log.d(TAG, "marketPush 광고성 메시지");

            }

            if (iMessageSendSeqno == null || TextUtils.isEmpty(iMessageSendSeqno)) {
                Log.d(TAG, "iMessageSendSeqno 빈 값으로 내려왔기 때문에 LGCNS 에서 보낸 메시지가 아님");

                return;
            }

            if (TextUtils.isEmpty(clientComSeqno)) {
                clientComSeqno = "";
            }

            if (TextUtils.isEmpty(smartPhoneMessage)) {
                smartPhoneMessage = "";
            }

            Log.d(TAG, "iMessageSendSeqno : " + iMessageSendSeqno);
            Log.d(TAG, "clientComSeqno : " + clientComSeqno);
            Log.d(TAG, "smartPhoneMessage : " + smartPhoneMessage);

            if (Build.VERSION.SDK_INT > 18 && Build.MODEL.contains("Nexus 5")) {
                try {
                    smartPhoneMessage = URLDecoder.decode(smartPhoneMessage, "utf-8");
                } catch (UnsupportedEncodingException e) {
                }
            } else {
                try {
                    smartPhoneMessage = URLDecoder.decode(smartPhoneMessage, "euc-kr");
                } catch (UnsupportedEncodingException e) {
                }
            }

            //GCM 수신 'message_seq' 전달 및 Notification을 띄워도 되는지 확인
            SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
            String gcm_token = SharedPreferenceManager.getString("fcmtoken", "");
            Log.d(TAG, "gcm_token : " + gcm_token);


            // 2018/01/03 광고성 메시지 수신 동의/거절 값 추가
            boolean marketing_agree_state = true;
            marketing_agree_state = sharedPreferenceManager.getBoolean(Const.TAG_MARKETING_AGREE_STATE, true);

            // 2018/01/16 추가
            // 앱 버전 가져오기
            String app_version_name = SystemUtil.getPackageVersionName(getApplicationContext());
            // 통신사명 가져오기
            String telecom_code = SystemUtil.getTelecomCode(getApplicationContext());
            // 디바이스 모델명 가져오기
            String device_model_name = SystemUtil.getDeviceModelName();
            // os버전 가져오기
            String os_version = SystemUtil.getOSVersion();


            String number = SystemUtil.getMyNumber(getApplicationContext());
            if (number == null || TextUtils.isEmpty(number)) {

                // TODO: 2020-09-25 전화번로를 가져올 수 없을때 return 처리
//                number = Const.TEST_NUMBER_;
                Toast.makeText(mContext, "전화번호를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: 2019-12-17 v3상용 카테고리 리스트 테스트
            if (Const.TEST_PN_ON) {
                mAZ = new AZ(getApplicationContext(), number, gcm_token, Const.TEST_PN, marketing_agree_state, device_model_name, telecom_code, os_version, app_version_name);
            } else {
                mAZ = new AZ(getApplicationContext(), number, gcm_token, getApplicationContext().getPackageName(), marketing_agree_state, device_model_name, telecom_code, os_version, app_version_name);
            }

            boolean result_init = mAZ.init();//라이브러리 초기화
            if (result_init) {

                if (mAZ == null) {
                    Log.d(TAG, "onMessageReceived mAZ is null");
                }
                if (intent == null) {
                    Log.d(TAG, "onMessageReceived intent is null");
                }

                // TODO: 2020-09-25 테스트 노티 이미지 추가
//                intent.putExtra("imageUrl", "https://ximpqa.lgcns.com/pamp/aitutor_1200_627.png");


                AZNotiInfoVO azNotiInfoVO = mAZ.sendMSG(intent);
                onNoti = azNotiInfoVO.getON_NOTIFICATION();//Notification을 띄워도 되는지 결과 ( "Y": 띄워도됨, "N": 안됨 )
                final String title = azNotiInfoVO.getCOMPANY_NAME();//Notification 제목
                final String company_id = azNotiInfoVO.getCOMPANY_ID();//고객사코드
                final String content = azNotiInfoVO.getCONTENT();//Notification 내용
                final int noti_count = azNotiInfoVO.getNOTI_COUNT();//Notification 개수 ex) 외 7건

                // TODO: 2020-09-10 전버전 테스트를 위한 주석처리
                // TODO: 2020-08-13 Noti에 이미지 추가 관련 테스트
                final Bitmap notiImage = azNotiInfoVO.getNOTI_IMAGE(); //
//                final Bitmap notiImage = null;

                Log.d(TAG, "onNoti : " + onNoti);
                Log.d(TAG, "title : " + title);
                Log.d(TAG, "company_id : " + company_id);
                Log.d(TAG, "content : " + content);
                Log.d(TAG, "noti_count : " + noti_count);

                boolean get_push_state2 = mAZ.getPushState();
                Log.d(TAG, "hsh_test_1 getPushState= " + get_push_state2);

                int get_noti_count2 = mAZ.getNotiCount();
                Log.d(TAG, "hsh_test_1 getNotiCount= " + get_noti_count2);
                if (onNoti.equals("Y")) {//Notification을 보내도 될때
                    Log.d(TAG, "sendNotification sendMSG result true");
                    Handler mHandler = new Handler(getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: 2020-08-13 Noti에 이미지 추가 관련 테스트
//                            sendNotification(title, content, noti_count, company_id);
                            sendNotification(title, content, noti_count, company_id, notiImage);
                        }
                    });
                } else {
                    Log.d(TAG, "sendNotification sendMSG result false");
                    /*if (mAZ != null) {
                        mAZ.release();
                        mAZ = null;
                    }*/
                }
            } else {
                Log.d(TAG, "AZ init이 필요합니다.");
            }

            Log.i(TAG, "Received: " + extras.toString());
//            }
        }
        //////////////////////////////////////////////////////
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG, "onDeletedMessages");
    }

    // TODO: 2020-08-13 Noti에 이미지 추가 관련 테스트
    //Notification 띄우기
    private void sendNotification(String title, String message, int noti_count, String company_id, Bitmap notiImage) {
        Log.d(TAG, "sendNotification");
        Intent intent;

        try {

            intent = new Intent(this, MainActivity.class);//이동할 액티비티

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(AZConst.TAG_COMPANY_ID, company_id);

            PendingIntent contentIntent = null;
            contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Log.d(TAG, "sendNotification 오레오 이상");
                noti = new NotificationHelper(this);
                sendNotification(NOTI_SECONDARY1, title, message, contentIntent, notiImage);
            } else {
                Log.d(TAG, "sendNotification 오레오 이하");
                NotificationCompat.Builder mBuilder = createNotification();

                mBuilder.setContentTitle(title)                            // required
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(notiImage)
                        .setContentText(message) // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent)
                        .setTicker(message)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(notiImage));

                if (Build.VERSION.SDK_INT < 16) {
                    mNotificationManager.notify(Const.NOTIFICATION_INDEX, mBuilder.getNotification());
                    Log.d(TAG, "1message=" + message);
                } else {
                    mNotificationManager.notify(Const.NOTIFICATION_INDEX, mBuilder.build());
                    Log.d(TAG, "2message=" + message);
                }
            }

            PushWakeLock.acquireCpuWakeLock(this);//WakeLock 연결, 화면 켜기


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mAZ != null && mAZ.onInit()) {
            mAZ.release();
            mAZ = null;
        }
    }

    //Notification 띄우기
    private void sendNotification(String title, String message, int noti_count, String company_id) {
        Log.d(TAG, "sendNotification");
        Intent intent;

        try {

            intent = new Intent(this, MainActivity.class);//이동할 액티비티

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(AZConst.TAG_COMPANY_ID, company_id);

            PendingIntent contentIntent = null;
            contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Log.d(TAG, "sendNotification 오레오 이상");
                noti = new NotificationHelper(this);
                sendNotification(NOTI_SECONDARY1, title, message, contentIntent);
            } else {
                Log.d(TAG, "sendNotification 오레오 이하");
                NotificationCompat.Builder mBuilder = createNotification();

                mBuilder.setContentTitle(title)                            // required
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(message) // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent)
                        .setTicker(message)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                        .setPriority(Notification.PRIORITY_HIGH);

                if (Build.VERSION.SDK_INT < 16) {
                    mNotificationManager.notify(Const.NOTIFICATION_INDEX, mBuilder.getNotification());
                    Log.d(TAG, "1message=" + message);
                } else {
                    mNotificationManager.notify(Const.NOTIFICATION_INDEX, mBuilder.build());
                    Log.d(TAG, "2message=" + message);
                }
            }

            PushWakeLock.acquireCpuWakeLock(this);//WakeLock 연결, 화면 켜기


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mAZ != null && mAZ.onInit()) {
            mAZ.release();
            mAZ = null;
        }
    }

    // TODO: 2020-08-13 Noti에 이미지 추가 관련 테스트

    /**
     * Send activity notifications.
     *
     * @param id    The ID of the notification to create
     * @param title The title of the notification
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification(int id, String title, String text, PendingIntent contentIntent, Bitmap notiImage) {
        Log.d(TAG, "sendNotification");
        Notification.Builder nb = null;
        nb = noti.getNotification2(title, text, contentIntent, notiImage);
        if (nb != null) {
            noti.notify(id, nb);
        }
    }

    /**
     * Send activity notifications.
     *
     * @param id    The ID of the notification to create
     * @param title The title of the notification
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification(int id, String title, String text, PendingIntent contentIntent) {
        Log.d(TAG, "sendNotification");
        Notification.Builder nb = null;
        nb = noti.getNotification2(title, text, contentIntent);
        if (nb != null) {
            noti.notify(id, nb);
        }
    }

    /**
     * 노티피케이션 빌드
     *
     * @return
     */
    private NotificationCompat.Builder createNotification() {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "8829")
                .setLargeIcon(icon)
                .setContentTitle("StatusBar Title")
                .setContentText("StatusBar subTitle")
                .setSmallIcon(R.mipmap.ic_launcher/*스와이프 전 아이콘*/)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setWhen(System.currentTimeMillis());
//                .setDefaults(Notification.DEFAULT_ALL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        return builder;
    }
}