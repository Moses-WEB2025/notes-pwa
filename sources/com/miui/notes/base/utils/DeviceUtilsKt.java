package com.miui.notes.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DeviceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"toDateString", "", "", "pattern", "common_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class DeviceUtilsKt {
    public static /* synthetic */ String toDateString$default(long j, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        return toDateString(j, str);
    }

    public static final String toDateString(long j, String pattern) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        String str = new SimpleDateFormat(pattern, Locale.CHINA).format(new Date(j));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }
}
