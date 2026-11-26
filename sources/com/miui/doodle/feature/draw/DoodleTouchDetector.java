package com.miui.doodle.feature.draw;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.base.TouchGestureDetector;
import com.miui.doodle.base.core.IDoodleTouchDetector;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoodleTouchDetector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleTouchDetector;", "Lcom/miui/doodle/base/TouchGestureDetector;", "Lcom/miui/doodle/base/core/IDoodleTouchDetector;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "listener", "Lcom/miui/doodle/base/TouchGestureDetector$IOnTouchGestureListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/doodle/base/TouchGestureDetector$IOnTouchGestureListener;)V", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleTouchDetector extends TouchGestureDetector implements IDoodleTouchDetector {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleTouchDetector(Context context, TouchGestureDetector.IOnTouchGestureListener listener) {
        super(context, listener);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listener, "listener");
        setScaleSpanSlop(1);
        setScaleMinSpan(1);
        setLongpressEnabled(false);
        setScrollAfterScaled(false);
    }
}
