package com.homey.cnstsample;

import android.app.Application;
//import android.support.multidex.MultiDexApplication;

//import com.google.android.gms.analytics.GoogleAnalytics;
//import com.google.android.gms.analytics.Tracker;
import com.homey.az.AZ;

/**
 * Created by rocomo12 on 2017-11-27.
 */

public class CNSTSApplication extends Application {
    public static AZ az = null;

    // TODO: 2018-10-10 GA 테스트 
//    public static GoogleAnalytics analytics;
//    public static Tracker tracker;
    private final String trackingId = "UA-127214514-1";
//    private final String trackingId = "<your Tracking ID>";

    @Override
    public void onCreate() {
        super.onCreate();
        /*analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);
        tracker = getDefaultTracker();*/
    }

   /* synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker(trackingId);
            tracker.enableExceptionReporting(true);
            tracker.enableAdvertisingIdCollection(true);
            tracker.enableAutoActivityTracking(true);
        }
        return tracker;
    }*/


}