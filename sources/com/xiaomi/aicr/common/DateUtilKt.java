package com.xiaomi.aicr.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DateUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0006\u0010\u0006\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0007"}, d2 = {"FORMAT_PATTERN", "", "format", "Ljava/text/SimpleDateFormat;", "getFormat", "()Ljava/text/SimpleDateFormat;", "currentTimeStr", "aicr_AI_sdk_release"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class DateUtilKt {
    public static final String FORMAT_PATTERN = "yyyyMMddHHmmssSSS";
    private static final SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN);

    public static final SimpleDateFormat getFormat() {
        return format;
    }

    public static final String currentTimeStr() {
        String str = format.format(Calendar.getInstance().getTime());
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }
}
