package com.miui.handwriting.common.tools;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DisplayCutoutHolder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\b\u001a\u00020\u0005J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/handwriting/common/tools/DisplayCutoutHolder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "safeTop", "", "TAG", "", "getSafeTop", "init", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onConfigChanged", "updateDisplayCutout", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DisplayCutoutHolder {
    public static final DisplayCutoutHolder INSTANCE = new DisplayCutoutHolder();
    private static final String TAG = "DisplayCutoutHolder";
    private static int safeTop;

    private DisplayCutoutHolder() {
    }

    public final int getSafeTop() {
        return safeTop;
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        updateDisplayCutout(context);
    }

    public final void onConfigChanged(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        updateDisplayCutout(context);
    }

    private final void updateDisplayCutout(Context context) {
        DisplayCutout cutout;
        Display display = context.getDisplay();
        if (display != null && (cutout = display.getCutout()) != null) {
            safeTop = cutout.getSafeInsetTop();
        }
        Log.d(TAG, "safeTop:" + safeTop);
    }
}
