package com.homey.cnstsample.util;

import android.text.TextUtils;

import com.homey.cnstsample.util.LogUtil;

import java.util.Calendar;

/**
 * Created by 상훈 on 2016-04-04.
 * 문자열 처리 관련 유틸
 */

public class StringUtil {
    // --------------------------------------------------------------
    //
    // Constants
    //
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    //
    // Statics
    //
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    //
    // 기존 유틸
    //
    // --------------------------------------------------------------

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * Null이 들어올경우 Empty로 리턴
     *
     * @param value 체크할 값
     * @return
     */
    public static String getNullToEmpty(Object value) {
        return getNullToValue(value, "");
    }

    /**
     * Null이 들어올경우 설정한 기본값으로 리턴
     *
     * @param value    체크할 값
     * @param defValue 기본값
     * @return
     */
    public static String getNullToValue(Object value, String defValue) {
        String s = defValue;
        try {
            if (value != null && !value.toString().equals("null"))
                s = value.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * String 띄어쓰기 '\n' 개수 리턴
     * @param content
     * @return
     */
    public static int getStringLineCount(String content) {
        LogUtil.d("StringUtil", "getStringLineCount content= " + content);
        int line_count = 0;
        int point = 0;
        int endcheck = content.lastIndexOf('\n');
        while (true) {
            point = content.indexOf('\n', point);
            line_count++;
            if (endcheck == point++) break;
        }
        LogUtil.d("StringUtil", "getStringLineCount line_count= " + line_count);
        return line_count;
    }


    /**
     * 파일명 마지막'/'로 잘라받기
     *
     * @param url
     * @return
     */
    public static String subStringFileName(String url) {
        String fileName = "";

        try {
            if (url != null && !TextUtils.isEmpty(url)) {
                int conurl_size = url.length();
                int start_content_index = url.lastIndexOf("/");
                fileName = url.substring(start_content_index + 1, conurl_size);
            } else {
                LogUtil.d("StringUtil", "subStringFileName url is null");
            }
            LogUtil.d("StringUtil", "subStringFileName fileName= " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 날짜 처리
     *
     * @param date
     * @return
     */
    public static String getDateSubString_Date(String date) {
        String date_s = "";
        String year = "";
        String month = "";
        String day = "";
        String week = "";
        if (TextUtils.isEmpty(date)) {

            date_s = year + "." + month + "." + day + "." + "(" + week + ")";
        } else {
            try {
                // 2017/12/15 15:18 기준 - lg cns 데이터
                year = date.substring(0, 4);
                month = date.substring(5, 7);
                day = date.substring(8, 10);

                int month_int = 0;
                month_int = Integer.parseInt(month);
                month = String.valueOf(month_int);
                int day_int = 0;
                day_int = Integer.parseInt(day);
                day = String.valueOf(day_int);

                week = getDayOfWeek(year, month, day);


                date_s = year + "." + month + "." + day + "." + "(" + week + ")";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date_s;
    }

    /**
     * 날짜 처리2
     *
     * @param date
     * @return
     */
    public static String getDateSubString_Date2(String date) {
        String date_s = "";
        String year = "";
        String month = "";
        String day = "";
        String week = "";
        if (TextUtils.isEmpty(date)) {

            date_s = year + "." + month + "." + day + "." + "(" + week + ")";
        } else {
            try {
                // 2017/12/15 15:18 기준 - lg cns 데이터
                year = date.substring(0, 4);
                month = date.substring(5, 7);
                day = date.substring(8, 10);

                int month_int = 0;
                month_int = Integer.parseInt(month);
                month = String.valueOf(month_int);
                int day_int = 0;
                day_int = Integer.parseInt(day);
                day = String.valueOf(day_int);

                week = getDayOfWeek2(year, month, day);


                date_s = year + "년 " + month + "월 " + day + "일 " + week;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date_s;
    }

    /**
     * 요일 처리
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getDayOfWeek(String year, String month, String day) {

        String week = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(year));
            cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
            cal.set(Calendar.DATE, Integer.parseInt(day));

            switch (cal.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    week = "일";
                    break;
                case 2:
                    week = "월";
                    break;
                case 3:
                    week = "화";
                    break;
                case 4:
                    week = "수";
                    break;
                case 5:
                    week = "목";
                    break;
                case 6:
                    week = "금";
                    break;
                case 7:
                    week = "토";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return week;
    }

    /**
     * 요일 처리2
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getDayOfWeek2(String year, String month, String day) {

        String week = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(year));
            cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
            cal.set(Calendar.DATE, Integer.parseInt(day));

            switch (cal.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    week = "일요일";
                    break;
                case 2:
                    week = "월요일";
                    break;
                case 3:
                    week = "화요일";
                    break;
                case 4:
                    week = "수요일";
                    break;
                case 5:
                    week = "목요일";
                    break;
                case 6:
                    week = "금요일";
                    break;
                case 7:
                    week = "토요일";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return week;
    }

    /**
     * 시간 처리
     *
     * @param date
     * @return
     */
    public static String getDateSubString_Time(String date) {
        LogUtil.d("StringUtil", "getDateSubString_Time date= " + date);
        String time = "";

        try {
            if (TextUtils.isEmpty(date)) {
                String hour = "";
                String minute = "";

                time = hour + minute;
            } else {
                // 2017/12/15 15:18 기준 - lg cns 데이터 8, 10
                String hour = date.substring(11, 13);
                String minute = date.substring(14, 16);

                int hour_int = 0;
                hour_int = Integer.parseInt(hour);
                if (hour_int == 12) {
                    hour = "오후 12:";
                } else if (hour_int == 0) {
                    hour = "오전 12:";
                } else if (hour_int > 12) {
                    hour_int = hour_int - 12;
                    hour = "오후 " + String.valueOf(hour_int) + ":";
                } else {
                    hour = "오전 " + String.valueOf(hour_int) + ":";
                }

                time = hour + minute;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


    /*원본
     *//**
     * 시간 처리
     *
     * @param date
     * @return
     *//*
    public static String getDateSubString_Time(String date) {
        String time = "";

        try {
            if (TextUtils.isEmpty(date)) {
                String hour = "";
                String minute = "";

                time = hour + minute;
            } else {
                // 2017/12/15 15:18 기준 - lg cns 데이터 8, 10
                String hour = date.substring(11, 13);
                String minute = date.substring(14, 16);

                int hour_int = 0;
                hour_int = Integer.parseInt(hour);
                if (hour_int > 12) {
                    hour_int = hour_int - 12;
                    hour = "오후 " + String.valueOf(hour_int) + ":";
                } else if (hour_int == 12 || hour_int == 0) {
                    hour = "오전 12:";
                } else {
                    hour = "오전 " + String.valueOf(hour_int) + ":";
                }

                time = hour + minute;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }*/
}
