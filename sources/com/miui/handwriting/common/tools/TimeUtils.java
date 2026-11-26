package com.miui.handwriting.common.tools;

import android.icu.util.Calendar;
import android.util.Log;
import com.miui.handwriting.common.HandWritingViewApplication;
import defpackage.R2;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes3.dex */
public class TimeUtils {
    private static final String TAG = "TimeUtils";

    public static CharSequence getFormattedSortedDate(long j) {
        StringBuilder sb = new StringBuilder();
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean zIsSameYear = isSameYear(j, jCurrentTimeMillis);
        boolean zIsSameWeek = isSameWeek(j, jCurrentTimeMillis);
        boolean zIsYesterday = isYesterday(j);
        if (zIsSameWeek || zIsYesterday) {
            sb.append(DateUtils.formatRelativeTime(HandWritingViewApplication.INSTANCE.getApplication(), j, true));
            return sb;
        }
        if (!zIsSameYear) {
            sb.append(DateUtils.formatDateTime(HandWritingViewApplication.INSTANCE.getApplication(), j, 896));
            return sb;
        }
        sb.append(DateUtils.formatDateTime(HandWritingViewApplication.INSTANCE.getApplication(), j, R2.attr.alignItems));
        return sb;
    }

    public static boolean isYesterday(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(5, -1);
        return calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5);
    }

    public static boolean isSameYear(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j2);
        return calendar.get(1) == calendar2.get(1);
    }

    public static boolean isSameWeek(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
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

    public static String formatTimestamp(long j, String str) {
        try {
            return new SimpleDateFormat(str, Locale.getDefault()).format(new Date(j));
        } catch (Exception e) {
            Log.e(TAG, "formatTimestamp fail", e);
            return "";
        }
    }
}
