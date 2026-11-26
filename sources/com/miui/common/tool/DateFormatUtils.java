package com.miui.common.tool;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.TextView;

/* JADX INFO: loaded from: classes2.dex */
public class DateFormatUtils {
    public static CharSequence formatDateText(Context context, long j) {
        return DateUtils.formatDateTime(context, j, 16);
    }

    public static CharSequence formatDateYearText(Context context, long j) {
        return DateUtils.formatDateTime(context, j, 4);
    }

    public static CharSequence formatTimeText(Context context, long j) {
        return miuix.pickerwidget.date.DateUtils.formatDateTime(context, j, 12);
    }

    public static String getRelativeTime(Context context, long j) {
        return miuix.pickerwidget.date.DateUtils.formatRelativeTime(context, j, false);
    }

    public static void setTextWithRelativeTime(TextView textView, long j) {
        textView.setText(miuix.pickerwidget.date.DateUtils.formatRelativeTime(textView.getContext(), j, false));
    }
}
