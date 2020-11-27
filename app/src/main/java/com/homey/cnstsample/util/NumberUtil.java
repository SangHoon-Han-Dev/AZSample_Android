package com.homey.cnstsample.util;

import android.widget.TextView;

import com.homey.cnstsample.util.StringUtil;

/**
 * Created by 상훈 on 2016-04-04.
 */
public class NumberUtil {
    /**
     * String -> int
     *
     * @param value
     * @param defaultValue 문자열이 Null이거나 비어있을경우 리턴할 디폴트값
     * @return
     */
    public static int toInt(String value, int defaultValue) {
        int i = defaultValue;

        if (value == null)
            return i;

        try {
            i = Integer.parseInt(value.replaceAll("[^\\d-.]", ""));
        } catch (NumberFormatException e) {
        }

        return i;
    }

    /**
     * String -> int
     *
     * @param d
     * @return
     */
    public static int toInt(double d) {
        return toInt(String.valueOf(d), 0);
    }

    /**
     * float -> int
     *
     * @param f
     * @return
     */
    public static int toInt(float f) {
        return toInt(floor(String.valueOf(f)), 0);
    }

    /**
     * Long -> int
     *
     * @param value
     * @return
     */
    public static int toInt(long value) {
        int i = 0;

        try {
            i = Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException e) {
        }

        return i;
    }

    /**
     * CharSequence -> int
     *
     * @param value
     * @return
     */
    public static int toInt(TextView value) {
        return toInt(value.getText().toString(), 0);
    }

    /**
     * CharSequence -> int
     *
     * @param value
     * @return
     */
    public static int toInt(CharSequence value) {
        return toInt(StringUtil.getNullToEmpty(value), 0);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device
     * density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need
     *            to convert into pixels
     * @return A float value to represent px equivalent to dp depending on
     *         device density
     */
//    public static int toPixel(float dp) {
//        Resources resources = SystemUtil.getApplicationContext().getResources();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//        int px = (int)(dp * (metrics.densityDpi / 160f));
//        return px;
//    }

    /**
     * This method converts device specific pixels to density independent
     * pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
//    public static float toDp(int px) {
//        Resources resources = SystemUtil.getApplicationContext().getResources();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//        float dp = px / (metrics.densityDpi / 160f);
//        return dp;
//    }


    /** 소수점, 공백제거 */
    public static String floor(String value) {
        value = value.trim();

        int pointIdx = value.indexOf(".");

        // 소수점이 있으면 소수점 이하 버림
        if (pointIdx != -1) {
            value = value.substring(0, pointIdx);
        }

        return value;
    }
}