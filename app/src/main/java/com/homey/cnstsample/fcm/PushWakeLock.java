package com.homey.cnstsample.fcm;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;



/**
 * Created by 상훈 on 2016-03-21.
 *
 * 잠든화면 켜기
 */
public class PushWakeLock {
    private static PowerManager.WakeLock sCpuWakeLock;

    static void acquireCpuWakeLock(Context context) {//화면 켜기
        Log.d("PushWakeLock", "Acquiring cpu wake lock");
        Log.d("PushWakeLock", "wake sCpuWakeLock = " + sCpuWakeLock);

        if (sCpuWakeLock != null) {
            return;
        }
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        sCpuWakeLock = pm.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, "hello");

        sCpuWakeLock.acquire();
    }

    public static void releaseCpuLock() {//해제
        Log.d("PushWakeLock", "Releasing cpu wake lock");
        Log.d("PushWakeLock", "relase sCpuWakeLock = " + sCpuWakeLock);

        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }
}