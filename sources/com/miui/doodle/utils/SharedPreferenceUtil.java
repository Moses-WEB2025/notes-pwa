package com.miui.doodle.utils;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SharedPreferenceUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/doodle/utils/SharedPreferenceUtil;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "hasShowGuideDialog", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "setShowGuideDialog", "", "DOODLE_SHARED_PREFERENCES", "", "KEY_SHOW_QUICK_SHAPE_GUIDE_DIALOG", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SharedPreferenceUtil {
    private static final String DOODLE_SHARED_PREFERENCES = "doodle";
    public static final SharedPreferenceUtil INSTANCE = new SharedPreferenceUtil();
    private static final String KEY_SHOW_QUICK_SHAPE_GUIDE_DIALOG = "show_quick_shape_guide_dialog";

    private SharedPreferenceUtil() {
    }

    public final boolean hasShowGuideDialog(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getSharedPreferences("doodle", 0).getBoolean(KEY_SHOW_QUICK_SHAPE_GUIDE_DIALOG, false);
    }

    public final void setShowGuideDialog(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        context.getSharedPreferences("doodle", 0).edit().putBoolean(KEY_SHOW_QUICK_SHAPE_GUIDE_DIALOG, true).apply();
    }
}
