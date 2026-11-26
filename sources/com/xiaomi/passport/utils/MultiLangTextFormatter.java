package com.xiaomi.passport.utils;

import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;

/* JADX INFO: loaded from: classes4.dex */
public class MultiLangTextFormatter {
    public static String forceLTR(String str) {
        return BidiFormatter.getInstance().unicodeWrap(str, TextDirectionHeuristics.LTR);
    }

    public static String forceRTL(String str) {
        return BidiFormatter.getInstance().unicodeWrap(str, TextDirectionHeuristics.RTL);
    }

    public static String wrapDefault(String str) {
        return BidiFormatter.getInstance().unicodeWrap(str);
    }
}
