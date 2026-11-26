package com.miui.handwriting.common.tools;

import android.content.Context;
import android.util.TypedValue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DisplaySizeUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"dpToPx", "", "Landroid/content/Context;", "dp", "BIG_SIZE", "NORMAL_SIZE", "PARTION_SELECT_SIZE", "libHandWritingCommon_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class DisplaySizeUtilKt {
    public static final float BIG_SIZE = 400.0f;
    public static final float NORMAL_SIZE = 240.0f;
    public static final float PARTION_SELECT_SIZE = 440.0f;

    public static final float dpToPx(Context context, float f) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }
}
