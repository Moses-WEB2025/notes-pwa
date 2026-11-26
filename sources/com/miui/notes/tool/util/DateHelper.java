package com.miui.notes.tool.util;

import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes4.dex */
public class DateHelper {
    private static final char DAY = 26085;
    private static final char MONTH = 26376;
    private static final char[] NUMBER = {38646, 19968, 20108, 19977, 22235, 20116, 20845, 19971, 20843, 20061};
    private static final char[] UNIT = {21313};
    private static final char YEAR = 24180;

    private static String getYearAsChineseText(int i) {
        StringBuilder sb = new StringBuilder();
        while (i > 0) {
            sb.insert(0, NUMBER[i % 10]);
            i /= 10;
        }
        return sb.toString();
    }

    private static String getNumberAsChineseText(int i) {
        StringBuilder sb = new StringBuilder();
        if (i <= 31) {
            int i2 = -1;
            while (i > 0) {
                if (i2 >= 0) {
                    sb.insert(0, UNIT[i2]);
                }
                int i3 = i % 10;
                if ((i2 < 0 && i3 > 0) || (i2 >= 0 && i3 > 1)) {
                    sb.insert(0, NUMBER[i3]);
                }
                i /= 10;
                i2++;
            }
        }
        return sb.toString();
    }

    public static String getDateAsChineseText(long j) {
        Calendar calendar = new Calendar();
        calendar.setTimeInMillis(j);
        return getYearAsChineseText(calendar.get(1)) + "年 " + getNumberAsChineseText(calendar.get(5) + 1) + "月 " + getNumberAsChineseText(calendar.get(9)) + DAY;
    }

    public static boolean isYesterday(long j) {
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        calendar.setTimeInMillis(j);
        android.icu.util.Calendar calendar2 = android.icu.util.Calendar.getInstance();
        calendar2.add(5, -1);
        return calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5);
    }

    public static boolean isSameYear(long j, long j2) {
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        calendar.setTimeInMillis(j);
        android.icu.util.Calendar calendar2 = android.icu.util.Calendar.getInstance();
        calendar2.setTimeInMillis(j2);
        return calendar.get(1) == calendar2.get(1);
    }

    public static boolean isSameWeek(long j, long j2) {
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTimeInMillis(j);
        android.icu.util.Calendar calendar2 = android.icu.util.Calendar.getInstance();
        calendar2.setFirstDayOfWeek(2);
        calendar2.setTimeInMillis(j2);
        int i = calendar.get(1) - calendar2.get(1);
        if (i == 0) {
            return calendar.get(3) == calendar2.get(3);
        }
        if (i == 1 && calendar2.get(2) == 11) {
            return calendar.get(3) == calendar2.get(3);
        }
        return i == -1 && calendar.get(2) == 11 && calendar.get(3) == calendar2.get(3);
    }
}
